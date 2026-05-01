package com.juic;

import com.juic.Core.Renderer;
import com.juic.Core.VNode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        VNode app = new VNode("div")
                .setAttrib("class","container")
                .addChild(new VNode("h1").setText("Hello JUIC"))
                .addChild(new VNode("p").setText("This is my framework"));

        String html = Renderer.render(app);
        System.out.println(html);
    }
}
