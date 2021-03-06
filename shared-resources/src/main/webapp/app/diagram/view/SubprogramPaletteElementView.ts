/*
 * Copyright Vladimir Zakharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/// <reference path="HtmlView.ts" />
/// <reference path="../../utils/StringUtils.ts" />

class SubprogramPaletteElementView extends HtmlView {

    private imageWidth: number = 30;
    private imageHeight: number = 30;

    private template: string = '' +
        '<li>' +
        '   <div class="tree-element" data-type="{0}" data-name="{1}" data-id="{2}">' +
        '       <img class="element-img" src="{3}" width="{4}" height="{5}">' +
        '       {6}' +
        '   </div>' +
        '</li>';

    constructor(typeName: string, name: string, imageSrc: string, nodeLogicalId: string) {
        super();
        this.content = StringUtils.format(this.template, typeName, name, nodeLogicalId, imageSrc,
            this.imageWidth.toString(), this.imageHeight.toString(), name);
    }

}