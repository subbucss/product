
package com.hertz.graph;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.frames.FramedGraph;

/**
 * Base class for all relationships. Provides common implementation of useful methods
 * for child classes.
 * 
 * @author Subba
 */
public class BaseRelationship {
    
    private FramedGraph<Graph> framedGraph;
    
    /**
     * Instantiates the relationship to work with the given graph
     * @param framedGraph Graph containing the entities
     */
    public BaseRelationship(FramedGraph<Graph> framedGraph) {
        this.framedGraph = framedGraph;
    }
    
    /**
     * Returns the node with the ID from the graph. 
     * @param <T> Type of the node
     * @param id ID of the node
     * @param clz Class of the node
     * @return Node object from graph
     */
    public<T> T get(Integer id, Class<T> clz) {
        if (id == null) throw new IllegalArgumentException("Node ID can't be null");
        
        // Check if it exists in graph, if not, create it
        Vertex v = framedGraph.getBaseGraph().getVertex(id);
        T node = null;
        
        if (v == null) throw new IllegalArgumentException("Node not found");
        
        node = framedGraph.frame(v, clz);
        return node;        
    }
    
    /**
     * Adds a node to the graph. If the node exists, it is updated, otherwise
     * a new vertex is added to the graph.
     * @param <T> Type of the node
     * @param id Node ID
     * @param clz Class of the framed object
     * @return Node framed object
     */
    public<T> T add(Integer id, Class<T> clz) {
        
        // Check if it exists in graph, if not, create it
        Vertex v = framedGraph.getBaseGraph().getVertex(id);
        T node = null;
        
        if (v == null) {
            v = framedGraph.getBaseGraph().addVertex(new Integer(id));
        }
        
        node = framedGraph.frame(v, clz);
        return node;
    }
    
    public<T> void removeVertex(Integer id, Class<T> clz) {
    	Vertex vertex = framedGraph.getBaseGraph().getVertex(id);
    	
    	if (vertex != null) {
    		framedGraph.getBaseGraph().removeVertex(vertex);
    	}
    }
}
