/*
Author: Brandon Yip
CPSC501 Assignment 2

Junit tester that tests Inspector.java. 
It uses ClassA as the test subject

To compile, I used junit-4.11.jar but you can replace it with whatever version you have
Make sure all classes that are involved are in the same directory.
		javac -cp junit-4.11.jar InspectorTester.java Inspector.java ClassA.java

This is how to run it on command line after you compiled it
		java org.junit.runner.JUnitCore InspectorTester

*/
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.*;


public class InspectorTester {
	private Inspector inspector;
	private ClassA classA;
	private Class a;
	
	@Before
	public void setUp() {
		inspector = new Inspector();
		classA = new ClassA();
		a = classA.getClass();
		inspector.setTesting(true);
	}
	
	@After
	public void tearDown() {
		inspector = null;
		classA = null;
		a = null;
	}
	
	
	//tests to see if the method gets the class name of the object
	@Test
	public void inspectorTest_className() {
		String className =inspector.inspectClass(a);
		assertEquals(className, "ClassA");
	}
	
	//tests to see if the method gets the superclass name
	@Test
	public void inspectorTest_superClassName() {
		String className = inspector.inspectSuperClass(a);
		assertEquals(className, "java.lang.Object");
	}
	
	//tests to see if the method gets the right methods for the class
	@Test
	public void inspectorTest_methodList() {
		String[] methodList = inspector.inspectMethods(a);
		Method[] expectedList = a.getDeclaredMethods();
		String[] methodNames = new String[expectedList.length];
		for(int i = 0; i < expectedList.length; i++) {
			methodNames[i] = expectedList[i].getName();
		}
		assertArrayEquals(methodNames, methodList);
	}
	
	//tests to see if the method gets the right interfaces for the class
	@Test
	public void inspectorTest_interfaceList() {
		String[] interfaceList = inspector.inspectInterfaces(a);
		Class[] expectedList = a.getInterfaces();
		String[] interfaceNames = new String[expectedList.length];
		for(int i = 0; i < expectedList.length; i++) {
			interfaceNames[i] = expectedList[i].getName();
		}
		assertArrayEquals(interfaceNames, interfaceList);
	}
	
	
	//tests to see if the list of fields are correct
	@Test
	public void inspectorTest_fieldList() {
		String[] fieldList = inspector.inspectFields(false, a);
		Field[] expectedList = a.getDeclaredFields();
		String[] fieldNames = new String[expectedList.length];
		for(int i = 0; i < expectedList.length; i++) {
			fieldNames[i] = expectedList[i].getName();
		}
		assertArrayEquals(fieldNames, fieldList);
	}
}