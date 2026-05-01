# Creating a Maven Project:

- As my end goal is to create a library, not an app.
- My goal is:

```
//XML
<dependency>
  <groupId>com.juic</groupId>
  <artifactId>juic-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

- That is possible with Maven (or Gradle).
- _Without Maven:_
  - You just have .java files
  - Not reusable
  - Not installable

- _Creating is Maven Project:_
- New Project -> Generators -> Maven Archtype.
- Name: Juic
- Archetype: maven-archetype-quickstart
- Advanced Settings:
- GroupId: com.juic
- ArtifactId: juic-core
- Version: 1.0.0

# public class VNode (Tree node):

- We need to create tree structure for our UI framework.
- As the Html Dom represents a tree structure.
- In DS terms we need a Tree structure.

```
//basic implementation of tree Node in java
class Node {
    int data;
    List<Node> childern;

    public Node(int data) {
        this.data = data;
        this.childern = new ArrayList()<>;
    }
}
```

- Creating a VNode class for the framework:

```
//VNode.java
class VNode {
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
```

- _Note:_
- _Each method returns this (the same node), so the next method call continues on it._
- _You return the node to enable method chaining and cleaner object building._

```
//Without returning:
VNode v = new VNode("div");
v.setText("Hello");
v.setAttrib("class","box");
```

```
//With returning:
VNode v = new VNode("div")
    .setText("Hello")
    .setAttrib("class","box");
```

# public class Renderer:
