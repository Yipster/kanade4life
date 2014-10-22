/*
Author: Brandon Yip
CPSC 501: Advanced Programming Technique
Assignment 2

This is the a class called Inspector that contains a method named inspect.

*/

import java.lang.reflect.*;
import java.util.*;
//import java.awt.*;

public class Inspector {

	Class currentClass;
	public static List<Class> seen = new ArrayList<Class>();

	/*
	inspects an object that is given and a boolean that asks if it should be recursive or not
	*/
	public void inspect(Object obj, boolean recursive) {
	
		inspector(obj);
		/*
		if(!recursive)
			inspector(obj);
		else {
			//do something
			recursiveInspector(obj);
		}*/
	}


	
	public void inspector(Object obj) {
		//1) Get name of declaring class
		System.out.println("Class Name: " + obj.getClass().getName());
		
		//2) Get name of immediate superclass
		currentClass = obj.getClass();
		Class superclass = currentClass.getSuperclass();
		String className = superclass.getName();
		System.out.println("Direct Superclass Name: " + className);
		System.out.println();

		//3) Name of interfaces that the class implements
		inspectInterfaces();

		//4) Methods that the class declares. 
		inspectMethods();
		
		//5) Constructor(s) that the class has
		inspectConstructors();
		
		//6) Fields that the class has
		inspectFields();
	}
	
	
	/*
	
	public void recursiveInspector(Object obj) {
		List<Class> neighbors = new ArrayList<Class>();
		seen.add(obj.getClass());
		
		
		
	}*/
	
	
	
	

	//method that inspects the interfaces of the object 
	public void inspectInterfaces() {
		System.out.println("The interface(s) of this object's class is(are):");
		Class[] interfaceList = currentClass.getInterfaces();
		printList(interfaceList);
		System.out.println();
		System.out.println();
	}




	//method that inspects the methods of the object
	public void inspectMethods() {
		//4) Methods the class declares
		System.out.println("The method(s) the class declares:");
		Method[] methodList = currentClass.getDeclaredMethods();
		for (int i = 0; i < methodList.length; i++) {
			//get method name and print
			int k = i + 1;
			String methodName = methodList[i].getName();
			System.out.println(k + ") Name: " + methodName);
			
			//get parameter types of the specified method and print
			Class[] parameterTypes = methodList[i].getParameterTypes();
			System.out.print("   Parameter Types: ");
			printList(parameterTypes);
			System.out.println();

			//get exception types of the specified method and print			
			Class[] exceptionTypes = methodList[i].getExceptionTypes();
			System.out.print("   Exception Types: ");
			printList(exceptionTypes);
			System.out.println();

			//get the method's modifiers and print
			int mod = methodList[i].getModifiers();
			testModifiers(mod);

			//get return type and print
			String returnType = methodList[i].getReturnType().getName();
			System.out.println("   Return Type: " + returnType);
			System.out.println();
		}

	}




	//method that inspects the constructor of the object
	public void inspectConstructors () {
		System.out.println("The constructor(s) in " + currentClass.getName() + ":");
		Constructor[] constructorList = currentClass.getDeclaredConstructors();
		for (int i = 0; i < constructorList.length; i++) {
			//get the constructor i's parameter types
			Class[] parameterTypes = constructorList[i].getParameterTypes();
			int k = i + 1;
			System.out.print(" " + k + ") " + "Parameters: ");
			printList(parameterTypes);
			System.out.println();
			System.out.print("  ");
			int mod = constructorList[i].getModifiers();
			testModifiers(mod);
			System.out.println();
		}
	}

	
	
	//method that inspects the class fields
	public void inspectFields () {
		Field[] fieldList = currentClass.getDeclaredFields();
		System.out.println("The field(s) of this class: ");
		for (int i = 0; i < fieldList.length; i++) {
			//get each field name
			int k = i + 1;
			String fieldName = fieldList[i].getName();
			System.out.println(k + ") Name: " + fieldName);

			//get type of field of each field
			Class typeClass = fieldList[i].getType();
			String fieldType = typeClass.getName();
			System.out.print("   Type: " + fieldType);
			System.out.println();
			
			//test modifiers of each field
			int mod = fieldList[i].getModifiers();
			testModifiers(mod);
			System.out.println();
		}
	}

	
	public void printList(Class[] c) {
		for(int j = 0; j < c.length; j++) {
			String name = c[j].getName();
			System.out.print(name + "  ");
		}
	}

	//method that checks each modifier and if true, will print
	public void testModifiers(int mod) {
		System.out.print("   Modifiers: ");
		if(Modifier.isAbstract(mod))
			System.out.print("Abstract ");
		if(Modifier.isFinal(mod))
			System.out.print("Final ");
		if(Modifier.isInterface(mod))
			System.out.print("Interface ");
		if(Modifier.isNative(mod))
			System.out.print("Native ");
		if(Modifier.isPrivate(mod))
			System.out.print("Private ");
		if(Modifier.isProtected(mod))
			System.out.print("Protected ");
		if(Modifier.isPublic(mod))
			System.out.print("Public ");
		if(Modifier.isStatic(mod))
			System.out.print("Static ");
		if(Modifier.isStrict(mod))
			System.out.print("Strict ");
		if(Modifier.isSynchronized(mod))
			System.out.print("Synchronized ");
		if(Modifier.isTransient(mod))
			System.out.print("Transient ");
		if(Modifier.isVolatile(mod))
			System.out.print("Volatile ");
		System.out.println();
	}

}
