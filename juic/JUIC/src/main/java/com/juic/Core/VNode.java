package com.juic.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNode {
    //Access specifier Default(None)
    String tag; //html tag
    String text; //tag content

    Map<String, String> attrib = new HashMap<String, String>(); //tag attributes

    List<VNode> childern = new ArrayList<VNode>(); //List for storing child nodes.

    //Constructor
    public VNode(String tag) {
        this.tag = tag;
    }

    /**
     Set text/context of node
     @return this node
     */
    public VNode setText(String text) {
        this.text = text;
        return this; //return the current calling node.
    }

    /**
     Add or Update attributes
     @return this node
     */
    public VNode setAttrib(String key, String val) {
        this.attrib.put(key,val);
        return this; //return the current calling node.
    }

    /**
     Add child node
     @return this node
     */
    public VNode addChild(VNode child) {
        this.childern.add(child);
        return this;
    }
}

/*
- Note:
- Each method returns this (the same node), so the next method call continues on it.
- You return the node to enable method chaining and cleaner object building.
*/