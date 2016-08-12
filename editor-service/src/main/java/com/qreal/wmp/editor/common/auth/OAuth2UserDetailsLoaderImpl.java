package com.qreal.wmp.editor.common.auth;

import com.qreal.wmp.editor.database.users.client.UserService;
import com.qreal.wmp.editor.database.users.model.User;
import com.qreal.wmp.editor.database.users.model.UserRole;
import com.racquettrack.security.oauth.OAuth2UserDetailsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/** Handles requests for user operations from OAuth authentication points.*/
@Component
public class OAuth2UserDetailsLoaderImpl implements OAuth2UserDetailsLoader<UserDetails, String> {

    public static final String ROLE_USER = "ROLE_USER";

    private static final Logger logger = LoggerFactory.getLogger(OAuth2UserDetailsLoaderImpl.class);

    @Autowired
    private UserService userService;

    /**
     * Finds user using userService for OAuth authentication point.
     * If user was found point will authenticate session of this user.
     * If user was not found point will create new user.
     */
    @Override
    public UserDetails getUserByUserId(String id) {
        logger.trace("OAuth passed user {} for authentication", id);
        if (!userService.isUserExist(id)) {
            logger.trace("User {} was not found", id);
            return null;
        } else {
            User user = userService.findByUserName(id);
            UserDetails userDetails = convert(user);
            logger.trace("User {} was found", id);
            return userDetails;
        }
    }

    /** Updates user retrieved from OAuth authentication point. Not used right now.*/
    @Override
    public UserDetails updateUser(UserDetails userDetails, Map<String, Object> userInfo) {
        logger.trace("OAuth tried to update user {}", userDetails.getUsername());
        logger.trace("No actual updating was performed for user {}.", userDetails.getUsername());
        return userDetails;
    }

    /**
     * Creates user retrieved from OAuth authentication point. User will be created if no user with this id was
     * found previously.
     */
    @Override
    public UserDetails createUser(String id, Map<String, Object> userInfo) {
        logger.trace("OAuth passed user {} for creation.", id);
        User user = new User(id, id, true);
        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole(user, ROLE_USER);
        roles.add(userRole);
        user.setRoles(roles);
        userService.save(user);
        UserDetails userDetails = convert(user);
        logger.trace("User {} was created.", id);
        return userDetails;
    }

    /** Tells if user with such info allowed to register.*/
    @Override
    public boolean isCreatable(Map<String, Object> userInfo) {
        return true;
    }

    private org.springframework.security.core.userdetails.User convert(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, buildUserAuthority(user.getRoles()));
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuths = userRoles.stream().map(userRole ->
                new SimpleGrantedAuthority(userRole.getRole())).collect(Collectors.toSet());
        return new ArrayList<>(setAuths);
    }
}