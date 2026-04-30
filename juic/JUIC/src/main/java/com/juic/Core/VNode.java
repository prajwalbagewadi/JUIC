package com.juic.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNode {
    String tag; //html tag
    String text; //Content
    Map<String,String> props = new HashMap<>(); //properties
    List<VNode> children = new ArrayList<>(); //child nodes

    public VNode(String tag) {
        this.tag = tag;
    }

    public VNode setText(String text) {
        this.text = text;
        return this;
    }

    public VNode setProps(String key, String value) {
        this.props.put(key, value);
        return this;
    }

    public VNode addChild(VNode child) {
        this.children.add(child);
        return this;
    }
}
