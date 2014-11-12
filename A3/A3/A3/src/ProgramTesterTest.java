/*
Author: Brandon Yip
CPSC501 Assignment3
 A Junit test class that tests certain parts of the system.

*/

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.*;

public class ProgramTesterTest {
	private ObjectCreator objectCreator;
	private Serializer serializer;
	private Deserializer deserializer;
	private Visualizer visualizer;
	
	@Before
	public void setUp(){
		objectCreator = new ObjectCreator();
		serializer = new Serializer();
		deserializer = new Deserializer();
		visualizer = new Visualizer();
	}
	
	@After
	public void tearDown() {
		objectCreator = null;
		serializer = null;
		deserializer = null;
		visualizer = null;
		
	}
	
	
	//test to see if Foo1 is created. Will require user input
	@Test
	public void test_Foo1Creation() {
		Object obj = objectCreator.object1();
		String actual = obj.getClass().getName();
		assertEquals(actual, "Foo1");
	}
	
	
	//test to see if Foo2 is created. Needs user input
	@Test
	public void test_Foo2Creation() {
		Object obj = objectCreator.object2();
		String actual = obj.getClass().getName();
		assertEquals(actual, "Foo2");
	}
	
	//test to see if Foo3 is created. Needs user input
	@Test
	public void test_Foo3Creation() {
		Object obj = objectCreator.object3();
		String actual = obj.getClass().getName();
		assertEquals(actual, "Foo3");
	}
		
	//test to see if Foo2 is created. Requires user input
	@Test
	public void test_Foo4Creation() {
		Object obj = objectCreator.object4();
		String actual = obj.getClass().getName();
		assertEquals(actual, "Foo4");
	}
	
	//test to see if Foo2 is created. Requires user input
	@Test
	public void test_Foo5Creation() {
		Object obj = objectCreator.object5();
		String actual = obj.getClass().getName();
		assertEquals(actual, "Foo5");
	}
	
	
}
