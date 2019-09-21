import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;

/**
 * Filename:   PackageManager.java
 * Project:    p4
 * Authors:    Stephen Fan
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

/**
 * Package Manager class used to process JSON files and provide functions that make that 
 * information available to other users
 * @author Stephen Fan
 *
 */
public class PackageManager {
    
    private Graph graph;
    
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
        graph = new Graph();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
        // create JSON object
    	Object object = new JSONParser().parse(new FileReader(jsonFilepath));
        JSONObject jsonobject = (JSONObject) object;
        
        // create JSON array
        JSONArray jsonarray = (JSONArray) jsonobject.get("packages");
        
        // loop through JSON array and add key/value pairs to graph
        for (int i = 0; i < jsonarray.size(); i++) {
        	JSONObject obj = (JSONObject) jsonarray.get(i);
        	
        	graph.addVertex((String)obj.get("name"));
        	JSONArray dependencyArray =  (JSONArray)obj.get("dependencies");
        	
        	for (int j = 0; j < dependencyArray.size(); j++) {
        		graph.addEdge((String)obj.get("name"), (String)dependencyArray.get(j));
        	}
        }
    }
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
    	// simple getter method
        return graph.getAllVertices();
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
    	// set of all vertices
    	Set<String> verticesList = graph.getAllVertices();
    	
    	// check if the package is contained in the set of all vertices and throw exception if it is not
    	if (verticesList.contains(pkg) == false) {
    		throw new PackageNotFoundException();
    	}
    	
    	// create hashmap and arraylist
    	HashMap<String, Integer> map = new HashMap<String, Integer>();
    	List<String> list = new ArrayList<String>();
    	
    	// -1 will represent a node that is unvisited
    	// make every node unvisited
    	for (String node : graph.getAllVertices()) {
    		map.put(node, -1);
    	}
    	
    	// call recursive helper method
    	return getInstallationOrderHelper(list, pkg, map);
    }
    
    private List<String> getInstallationOrderHelper(List<String> orderList, String vertex, HashMap<String, 
    		Integer> hashMap) throws CycleException {
    	
    	// 0 represents the current vertex to keep track of cycling errors
    	hashMap.put(vertex,0);
    	
    	// loops through dependency list to visit each vertex that is dependent upon the current vertex
    	for(String node : graph.getAdjacentVerticesOf(vertex)) {
    		// checks that node is not null and the map contains the node
    		if (node != null && hashMap.containsKey(node) == true) {
    			// checks for a cycle exception if the node has been visited before
    			if (hashMap.get(node) == 0) {
    				throw new CycleException();
    			}
    			// recursive call
    			else if (hashMap.get(node) != 1) {
    				getInstallationOrderHelper(orderList, node, hashMap);
    			}
    		}
    	}
    	
    	orderList.add(vertex);
    	
    	// mark vertex as visited
    	hashMap.put(vertex, 1);
    	
    	return orderList;
    }
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
        // create lists of installation order for each package
    	List<String> installOrder = getInstallationOrder(newPkg);
        List<String> installedPkgList = getInstallationOrder(installedPkg);
        
        // remove duplicates
        for (String node : installedPkgList) {
        	if (installOrder.contains(node)) {
        		installOrder.remove(node);
        	}
        }
    	
    	return installOrder;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
    	// create variables
    	String vertex = "";
    	List<String> orderList = new ArrayList<>();
    	Set<String> packageSet = getAllPackages();
    	
    	// loop through all packages
    	while (orderList.containsAll(getAllPackages()) == false) {
    		packageSet = getAllPackages();
    		
    		// remove packages that have been accounted for
    		packageSet.removeAll(orderList);
    		
    		vertex = getPackageWithMaxDependencies();
    		
    		try {
    			for (String n : getInstallationOrder(vertex)) {
    				// checks if vertices are in order and adds them if they are not
    				if (orderList.contains(n) == false) {
    					orderList.add(n);
    				}
    			}
    		}
    		catch (PackageNotFoundException e) {
    			//do nothing
    		}
    	}
    	
    	return orderList;
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {
    	// create variables to represent max and current dependencies
    	int maxDependencies = 0;
    	int currentDependencies = 0;
    	String md = ""; // String version of the package with the max dependencies to return
    	
    	// loop through all vertices
    	for (String vertex : graph.getAllVertices()) {
    		try {
                currentDependencies = getInstallationOrder(vertex).size();
    		} 
    		catch (PackageNotFoundException e) {
                e.printStackTrace();
    		}

    		// set max dependencies to current dependencies if current is larger
            if (currentDependencies > maxDependencies) {
                maxDependencies = currentDependencies;
                
                // change the String variable to be returned to the new largest vertex
                md = vertex;
            }
    	}

    	return md;
    }

    public static void main (String [] args) {
        System.out.println("PackageManager.main()");
    }
    
}
