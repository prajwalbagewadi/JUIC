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

- _VNode:_ represents a single node in the virtual UI tree used by 'Juic framework'.
- Models the structure of the UI as a tree of nodes.
- Mirrors the hierarchical nature of the HTML DOM.
- Stores element type, attributes(props), text content and child nodes.
- Serves as the core data structure for building and rendering UI.

- _Description:_
- In Juic, the UI is represented as a tree of VNode objects, similar to how the browser represents HTML as a DOM tree.
- Each VNode corresponds to an element and can contain child nodes, forming a recursive structure.

- _Example:_

```
//Java
VNode app = new VNode("div")
    .addChild(new VNode("h1").setText("hello"))
    .addChild(new VNode("p").setText("Welcome"));
```

- This represents:

```
//HTML
<div>
    <h1>hello</h1>
    <p>Welcome</p>
</div>
```

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

- Renderer is responsible for transforming a Virtual node tree into HTML String.
- Converts a user-defined VNode tree into a valid HTML String.
- Traverses the node Structure and generates corresponding markup.
- Acts as the bridge between the Virtual representation (VNode) and the actual output.

- _Description:_
- The Renderer processes the hierarchical structure of VNode objects and produces a serialized HTML representation that can be injected into the DOM.

- _Example:_

```
//Java
import com.juic.Core.VNode;

class Main {
    public static void main(String[] args) {

        VNode app = new VNode("div")
            .addChild(new VNode("h1").setText("hello world!"));

        String html = Renderer.render(app);
    }
}
```

```
//Html
<div>
    <h1>hello</h1>
</div>
```

- Renderer class.

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

# React mounting mechanism:

- Think of React mounting like putting your app inside an empty box on the page.
- Take my app and attach it to this HTML element.

1. Empty container in HTML

```
<div id="root"></div>
```

- This is just an empty placeholder.

2. React targets that container

```
//js
const root = document.getElementById("root");
```

- React finds the box.

3. React renders your app into it

```
//js
ReactDOM.createRoot(root).render(<App/>);
```

- React says: Put <App/> inside this root div.

- _What React Actually Does Internally_

1. Builds a virtual version of your UI (Virtual DOM).
2. Converts it into real DOM elements
3. Inserts those elements into #root.

```
<App/> -> Virtual DOM -> Real Html elements -> Inserted into #root.
```

- _Implementating the Mounting Mechanism:_

- goal:

```
//java
Juic.mount(app, "root");
```

- We need GWT to build the mechanism:
- What is GWT?
- GWT is a development toolkit that lets you write frontend web apps in java, and then compiles that java code into javascript so it can run in the browser.
- In simple Terms:
- You write Java.
- GWT converts it to JavaScript.
- Browser runs it like a normal web app.
- How it Works:

```
Java Code(your app) -> GWT Compiler -> JavaScript -> Runs in Browser (index.html)
```

- _Example:_

```
//java
public class App implements EntryPoint {
    public void onModuleLoad() {
        RootPanel.get("root").add(new Label("Hello from GWT"));
    }
}
```

- What happens:
- GWT compiles this -> Javascript
- Browser loads it.
- Content appears inside <div id="root">
