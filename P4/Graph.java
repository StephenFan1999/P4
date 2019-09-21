import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Stephen Fan
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {
	// declare class variables
	HashMap<String, ArrayList<String>> map;
	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		map = new HashMap();
	}

	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vertex) {
		// check if the vertex is null
		if (vertex == null) {
			return;
		}
		
		// check if hashmap contains the key and add it if not
		if (map.containsKey(vertex) == false) {
			map.put(vertex, new ArrayList<String>());
		}
	}

	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removeVertex(String vertex) {
		// check if the vertex is null or if the map does not contain the vertex
		if (vertex == null || map.containsKey(vertex) == false) {
			return;
		}
		
		// check if map contains the vertex and remove it if it does
		if (map.containsKey(vertex) == true) {
			map.remove(vertex);
			
			// iterate through hashmap to remove the edges that other vertices may have with
			// the removed vertex
			Iterator<Map.Entry<String, ArrayList<String>>> it = map.entrySet().iterator();
		    while (it.hasNext()) {
		    	// pair is a key-value pair
		        Map.Entry<String, ArrayList<String>> pair = (Map.Entry)it.next();
		        ArrayList<String> value = (ArrayList<String>) pair.getValue();
		        
		        // remove the edges corresponding to this vertex from the graph
		        value.remove(vertex);
		        
		        // change the value of pair to the ArrayList in which the vertex was removed
		        pair.setValue(value);
		        
		        // replace the vertex with the updated key-value pair
		        map.put(pair.getKey(), pair.getValue());
		    }

		}
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * no edge is added and no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		// check that vertices are not null
		if (vertex1 == null || vertex2 == null) {
			return;
		}
		
		// add a vertex if it does not exist yet
		if (map.containsKey(vertex1) == false) {
			map.put(vertex1, new ArrayList<String>());
		}
		
		// add a vertex if it does not exist yet
		if (map.containsKey(vertex2) == false) {
			map.put(vertex2, new ArrayList<String>());
		}
		
		// add edge between the vertexes if they both exist
		if (map.containsKey(vertex1) == true) {
			ArrayList<String> value = map.get(vertex1);
			value.add(vertex2);
			map.put(vertex1, value);
		}
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vertex1, String vertex2) {
		// check that vertices are not null
		if (vertex1 == null || vertex2 == null) {
			return;
		}
		
		// remove the edge between two vertices
		if (map.containsKey(vertex1) == true) {
			ArrayList<String> value = map.get(vertex1);
			value.remove(vertex2);
			map.put(vertex1, value);
		}
	}	

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() {
		// simple getter method
		Set<String> keySet = map.keySet();
		return keySet;
	}

	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		// returns value of a vertex
		List<String> value = null;
		
		if (map.containsKey(vertex) == true) {
			value = map.get(vertex);
		}
		
		return value;
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() {
		// iterate through hashmap to remove the edges that other vertices may have with
		// the removed vertex
    	int edgeCount = 0;
    	
		Iterator<Map.Entry<String, ArrayList<String>>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	    	// pair is a key-value pair
	        Map.Entry<String, ArrayList<String>> pair = (Map.Entry)it.next();
	        ArrayList<String> value = (ArrayList<String>) pair.getValue();
	        
	        // increment edgeCount for each edge on the current vertex
	        edgeCount += value.size();
	    }
    	
        return edgeCount;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() {
		// simple getter method
        return map.size();
    }
}
