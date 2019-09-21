import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class PackageManagerTest {

    /** 
     * Tests that PackageManager can construct the Graph
     */
    @Test
    public void test1_graphConstructor() {
    	PackageManager pm = new PackageManager();
        
    	// construct the Graph from the JSON file
    	try {
    		pm.constructGraph("jsonfile.json");
    	}
    	catch (ParseException e) {
    		fail("ParseException was thrown");
    	}
    	catch (IOException e) {
    		fail("IOException was thrown");
    	}
    	
    	// check if the Graph was constructed by checking the size is 4
    	if (pm.getAllPackages().size() != 4) {
    		System.out.println(pm.getAllPackages().size());
    		fail("Number of packages should be 4");
    	}
    }
    
    /** 
     * Tests that getPackageWithMaxDependencies gets the correct package
     */
    @Test
    public void test2_getMaxDependencies() {
    	PackageManager pm = new PackageManager();
    	String maxDependencies = "";
        
    	// construct graph
    	try {
    		pm.constructGraph("jsonfile.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call method to be tested getPackageWithMaxDependencies
    	try {
    		maxDependencies = pm.getPackageWithMaxDependencies();
    	}
    	catch (CycleException e) {
    		fail("CycleException was thrown");
    	}
    	
    	if (maxDependencies.equals("A") == false) {
    		fail("maxDependencies has returned the wrong value");
    	}
    }
    
    /** 
     * Tests that getPackageWithMaxDependencies throws a CycleException when the Graph is cyclic
     */
    @Test
    public void test3_cyclicGetPackageWithMaxDependencies() {
    	PackageManager pm = new PackageManager();
    	String maxDependencies = "";
    	boolean cyclicExceptionThrown = false;
        
    	// construct graph with cyclic json
    	try {
    		pm.constructGraph("cyclic.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call method to be tested getPackageWithMaxDependencies
    	try {
    		maxDependencies = pm.getPackageWithMaxDependencies();
    	}
    	catch (CycleException e) {
    		// test passed
    		cyclicExceptionThrown = true;
    	}
    	catch (Exception e) {
    		fail("An incorrect exception was thrown");
    	}
    	
    	if (cyclicExceptionThrown == false) {
    		fail("no CycleException was thrown");
    	}
    }
    
    /** 
     * Tests that getInstallationOrderForAllPackages throws a CycleException for a cyclic Graph
     */
    @Test
    public void test4_cyclicGetInstallationOrderForAllPackages() {
    	PackageManager pm = new PackageManager();
    	boolean cyclicExceptionThrown = false;
        
    	// construct graph with cyclic json
    	try {
    		pm.constructGraph("cyclic.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call method to be tested getInstallationOrderForAllPackages
    	try {
    		List<String> list = pm.getInstallationOrderForAllPackages();
    	}
    	catch (CycleException e) {
    		// test passed
    		cyclicExceptionThrown = true;
    	}
    	catch (Exception e) {
    		fail("An incorrect exception was thrown");
    	}
    	
    	if (cyclicExceptionThrown == false) {
    		fail("no CycleException was thrown");
    	}
    }

    /** 
     * Tests that getInstallationOrderForAllPackages works as intended
     */
    @Test
    public void test5_getInstallationOrderForAllPackages() {
    	PackageManager pm = new PackageManager();
    	List<String> list = new ArrayList<String>();
        
    	// construct graph with cyclic json
    	try {
    		pm.constructGraph("jsonfile.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call method to be tested getInstallationOrderForAllPackages
    	try {
    		list = pm.getInstallationOrderForAllPackages();
    	}
    	catch (Exception e) {
    		fail("An incorrect exception was thrown");
    	}
    	
    	Object[] list2 = list.toArray();
    	
    	// check that the installation order is correct
    	if (list2[0].equals("D") == false) {
    		fail("Installation order is not correct. First node should be D.");
    	}
    	
    	if (list2[1].equals("B") == false && list2[1].equals("C") == false) {
    		fail("Installation order is not correct. Second node or third node should be B or C. Fail 1");
    	}
    	
    	if (list2[2].equals("B") == false && list2[2].equals("C") == false) {
    		fail("Installation order is not correct. Second node or third node should be B or C. Fail 2");
    	}
    	
    	if (list2[3].equals("A") == false) {
    		fail("Installation order is not correct. Last node should be A");
    	}
    }

    /** 
     * Tests the toInstall method
     */
    @Test
    public void test6_toInstall() {
    	PackageManager pm = new PackageManager();
    	List<String> list = new ArrayList<String>();
        
    	// construct the Graph from the JSON file
    	try {
    		pm.constructGraph("jsonfile.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call toInstall method to test
    	try {
    		list = pm.toInstall("A", "B");
    	}
    	catch (Exception e) {
    		fail("No exception should have been thrown");
    	}
    	
    	// check if toInstall worked
    	if (list.get(0).equals("C") == false) {
    		fail("First node should be C");
    	}
    	if (list.get(1).equals("A") == false) {
    		fail("Second node should be A");
    	}
    }
    
    /** 
     * Tests that getInstallationOrderForAllPackages works as intended
     */
    @Test
    public void test7_getInstallationOrder() {
    	PackageManager pm = new PackageManager();
    	List<String> list = new ArrayList<String>();
        
    	// construct graph with cyclic json
    	try {
    		pm.constructGraph("jsonfile.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call method to be tested getInstallationOrderForAllPackages
    	try {
    		list = pm.getInstallationOrder("A");
    	}
    	catch (Exception e) {
    		fail("An incorrect exception was thrown");
    	}
    	
    	Object[] list2 = list.toArray();
    	
    	// check that the installation order is correct
    	if (list2[0].equals("D") == false) {
    		fail("Installation order is not correct. First node should be D.");
    	}
    	
    	if (list2[1].equals("B") == false && list2[1].equals("C") == false) {
    		fail("Installation order is not correct. Second node or third node should be B or C. Fail 1");
    	}
    	
    	if (list2[2].equals("B") == false && list2[2].equals("C") == false) {
    		fail("Installation order is not correct. Second node or third node should be B or C. Fail 2");
    	}
    	
    	if (list2[3].equals("A") == false) {
    		fail("Installation order is not correct. Last node should be A");
    	}
    }
    
    /** 
     * Tests that getInstallationOrderForAllPackages throws a CycleException for a cyclic Graph
     */
    @Test
    public void test8_cyclicGetInstallationOrder() {
    	PackageManager pm = new PackageManager();
    	boolean cyclicExceptionThrown = false;
        
    	// construct graph with cyclic json
    	try {
    		pm.constructGraph("cyclic.json");
    	}
    	catch (Exception e) {
    		fail("Exception was thrown");
    	}
    	
    	// call method to be tested getInstallationOrderForAllPackages
    	try {
    		List<String> list = pm.getInstallationOrder("A");
    	}
    	catch (CycleException e) {
    		// test passed
    		cyclicExceptionThrown = true;
    	}
    	catch (Exception e) {
    		fail("An incorrect exception was thrown");
    	}
    	
    	if (cyclicExceptionThrown == false) {
    		fail("no CycleException was thrown");
    	}
    }
}
