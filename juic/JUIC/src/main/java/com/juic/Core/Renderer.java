package com.juic.Core;

import java.util.Map;

public class Renderer {
    public static String render(VNode node) {
        if (node == null) {
            return "";
        }
        StringBuilder html = new StringBuilder();

        //opening tag
        html.append("\n<").append(node.tag);

        //props
        for (Map.Entry<String, String> entry: node.props.entrySet()) {
            html.append(" ")
                    .append(entry.getKey())
                    .append("=\"")
                    .append(entry.getValue())
                    .append("\"");
        }

        html.append(">");

        //Text
        if(node.text != null) {
            html.append("\n"+node.text);
        }

        //Childern (DFS)
        for(VNode child: node.children) {
            html.append(render(child));
        }

        //Closing tag
        html.append("\n</").append(node.tag).append(">");

        return html.toString();
    }
}
