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

- The Renderer class Renders the User inputed VNode tree structure.
- And converts it to a Simple html String.

````
//Renderer.java
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
````

## Question to Chatgpt:

- _what is this As React has index.html with div id = root, how can I make a similar page for my juic framework in documentation terms?_

- Basically trying to re-create the React mounting mechanism in your own framework.
