package com.juic.Core;

import java.util.Map;

public class Renderer {
    //Static method, can be called directly by class.
    public static String render(VNode node) {

        //check if node is null.
        if(node == null) {
            return "";
        }

        //create a mutable string object.
        StringBuilder html = new StringBuilder();

        //Appending '<' opening bracket before the opening tag.
        html.append("\n<").append(node.tag);

        //Appending attributes
        /*
            - for-each loop over hashmap, and each temp extracted element is stored in (entry).

            - Your Map (node.attrib)
                ```
                    attrib = {
                        "class" = "container",
                        "id" = "main"
                    }
                ```
            - (node.attrib.entrySet()) -> Converts Map -> Set of Key-Val pairs.
                ```
                    [
                        ("class", "container"),
                        ("id", "main")
                    ]
                ```
        */
       //Map.Entry<k,v>: static interface nested interface within Map, interface that represents a single key-value pair.
        for(Map.Entry<String, String> entry: node.attrib.entrySet()) {
            html.append(" ")
                    .append(entry.getKey())
                    .append("=\"")
                    .append(entry.getValue())
                    .append("\"");
        }

        //appending '>' closing bracket
        html.append(">");

        //Adding Text
        if(node.text != null) {
            //check if text is not null to add text.
            html.append(node.text);
        }

        //Adding Childern
        for(VNode child: node.childern) {
            //Recursive call to render function to render child nodes/tags and add it in the tree.
            html.append(render(child));
        }

        //Adding closing tag
        html.append("</").append(node.tag).append(">");

        //returing html String
        return html.toString();
    }
}