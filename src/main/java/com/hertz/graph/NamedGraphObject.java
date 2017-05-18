
package com.hertz.graph;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

/**
 * A base interface for all graph objects that have a name. All of them by default
 * have an ID which is used to locate the object in the graph. 
 * 
 * @author Feroz
 */
public interface NamedGraphObject extends VertexFrame {

    @Property("name")
    public String getName();

    @Property("name")
    public void setName(String name);
}
