import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Graph class that has vertices and edges connecting them to each other
 * @author Stephen Fan
 *
 */
class GraphTest {

    /** 
     * Tests that Graph can add a vertex
     */
    @Test
    public void test1_addVertex() {
    	// create Graph and add two vertices
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyTwo");
    	
    	// graph order should be 2
    	if (graph.order() != 2) {
    	    fail("vertices in graph should be 1");
    	}
    }
    
    /** 
     * Tests that Graph cannot add a duplicate vertex
     */
    @Test
    public void test2_addVertexDuplicate() {
    	// create Graph and add 2 of the same vertex to it
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyOne");
    	
    	// graph order should still be one
    	if (graph.order() != 1) {
    	    fail("vertices in graph should be 1");
    	}
    }
    
    /** 
     * Tests that Graph can add edges
     */
    @Test
    public void test3_addEdge() {
    	// create Graph and add 3 vertices
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyTwo");
    	graph.addVertex("KeyThree");
    	
    	// create edge between vertices (5 created)
    	graph.addEdge("KeyOne", "KeyTwo");
    	graph.addEdge("KeyOne", "KeyThree");
    	graph.addEdge("KeyTwo", "KeyOne");
    	graph.addEdge("KeyThree", "KeyTwo");
    	graph.addEdge("KeyThree", "KeyOne");
    	
    	if (graph.size() != 5) {
    		fail("number of edges should be 5");
    	}
    }
    
    /** 
     * Tests that Graph can remove edges
     */
    @Test
    public void test4_removeEdge() {
    	// create Graph and add 3 vertices
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyTwo");
    	graph.addVertex("KeyThree");
    	
    	// add edges between vertices (5)
    	graph.addEdge("KeyOne", "KeyTwo");
    	graph.addEdge("KeyOne", "KeyThree");
    	graph.addEdge("KeyTwo", "KeyOne");
    	graph.addEdge("KeyThree", "KeyTwo");
    	graph.addEdge("KeyThree", "KeyOne");
    	
    	// remove edges between vertices (2)
    	graph.removeEdge("KeyOne", "KeyTwo");
    	graph.removeEdge("KeyOne", "KeyThree");
    	
    	// 5-2 = 3
    	if (graph.size() != 3) {
    		fail("number of edges should be 3");
    	}
    }
    
    /** 
     * Tests that Graph can remove a vertex
     */
    @Test
    public void test5_removeVertex() {
    	// create Graph and add 4 vertices
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyTwo");
    	graph.addVertex("KeyThree");
    	graph.addVertex("KeyFour");
    	
    	// remove one vertex
    	graph.removeVertex("KeyOne");
    	
    	// 4-1 = 3
    	if (graph.order() != 3) {
    		fail("number of edges should be 3");
    	}
    }
    
    /** 
     * Tests that Graph will not add a null vertex
     */
    @Test
    public void test6_addNullVertex() {
    	// create Graph
    	Graph graph = new Graph();
    	
    	// add null vertex
    	try {
    		graph.addVertex(null);
    	}
    	// no exception should be thrown
    	catch (Exception e) {
    		fail("no exception was supposed to be thrown");
    	}
    	
    	if (graph.order() != 0) {
    	    fail("vertices in graph should be 0");
    	}
    }
    
    /** 
     * Tests that adding an edge between a vertex that exists and a vertex that does not exist
     * will create the nonexistent vertex
     */
    @Test
    public void test7_addExistingEdgeAndNonExistingEdge() {
    	// create Graph and add 1 vertex
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	
    	// add edge between vertex and nonexistent vertex
    	graph.addEdge("KeyOne", "KeyTwo");
    	
    	// Graph order should be 2 after creating KeyTwo
    	if (graph.order() != 2) {
    		fail("number of edges should be 2");
    	}
    }
    
    /** 
     * Tests that Graph will not remove a null vertex
     */
    @Test
    public void test8_removeNullVertex() {
    	// create Graph
    	Graph graph = new Graph();
    	
    	// remove null vertex
    	try {
    		graph.removeVertex(null);
    	}
    	// no exception should be thrown
    	catch (Exception e) {
    		fail("no exception was supposed to be thrown");
    	}
    	
    	if (graph.order() != 0) {
    	    fail("vertices in graph should be 0");
    	}
    }
    
    /** 
     * Tests that getAdjacentVertices() gets all neighbors
     */
    @Test
    public void test9_getAdjacentVertices() {
    	// create Graph and add 4 vertices
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyTwo");
    	graph.addVertex("KeyThree");
    	graph.addVertex("KeyFour");
    	
    	// create edge between vertices (3 created)
    	graph.addEdge("KeyOne", "KeyTwo");
    	graph.addEdge("KeyOne", "KeyThree");
    	graph.addEdge("KeyOne", "KeyFour");
    	
    	
    	
    	if (graph.getAdjacentVerticesOf("KeyOne").size() != 3) {
    		fail("number of adjacent vertices should be 3");
    	}
    }
    
    /** 
     * Tests that getAllVertices() returns the correct number of vertices
     */
    @Test
    public void test10_getAllVertices() {
    	// create Graph and add 4 vertices
    	Graph graph = new Graph();
    	graph.addVertex("KeyOne");
    	graph.addVertex("KeyTwo");
    	graph.addVertex("KeyThree");
    	graph.addVertex("KeyFour");
    	
    	if (graph.getAllVertices().size() != 4) {
    		fail("number of adjacent vertices should be 4");
    	}
    }
}
