package com.qreal.robots.components.editor.model.diagram;

import java.io.Serializable;

public class DiagramRequest implements Serializable {

    private Diagram diagram;

    private Long folderId;

    public Diagram getDiagram() {
        return this.diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public Long getFolderId() {
        return this.folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }
}