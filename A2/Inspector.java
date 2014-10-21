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


	/*
	inspects an object that is given and a boolean that asks if it should be recursive or not
	*/
	public void inspect(Object obj, boolean recursive) {
		
		//1) Get name of declaring class
		System.out.println("The name of this object's class is " + obj.getClass().getName());
		System.out.println();
		
		//2) Get name of immediate superclass
		currentClass = obj.getClass();
		Class superclass = currentClass.getSuperclass();
		String className = superclass.getName();
		System.out.println("The name of the superclass of this object's class is " + className);
		System.out.println();

		//3) Name of interfaces that the class implements
		//inspectInterfaces();

		//4) Methods that the class declares. 
		//inspectMethods();
	}

/*

	//method that inspects the interfaces of the object 
	public void inspectInterfaces() {
		System.out.println("The interface(s) of this object's class is(are):");
		Class[] theInterfaces = currentClass.getInterfaces();
		for (int i = 0; i < theInterfaces.length; i++ ){
			String interfaceName = theInterfaces[i].getName();
			System.out.println(interfaceName);
		}
		System.out.println();
	}
	*/


/*
	//method that inspects the methods of the object
	public void inspectMethods() {
		//4) Methods the class declares
		System.out.println("The method(s) the class declares:")
		Method[] methodList = currentClass.getDeclaredMethods();
		for (int i = 0; i < methodList.length; i++) {
			//get method name and print
			String methodName = methodList[i].getName();
			System.out.println("Name: " + methodName);
			
			//get parameter types of the specified method and print
			Class[] paramterTypes = methodList[i].getParameterTypes();
			System.out.println("   Parameter Types: ");
			for (int j = 0; j < parameterTypes.length; j++) {
				String parameterName = parameterTypes[j].getName();
				int k = j + 1;
				System.out.println("     " + k + ") " + parameterName);
			}
			System.out.println();

			//get exception types of the specified method and print			
			Class[] exceptionTypes = methodList[i].getExceptionTypes();
			System.out.println("   Exception Types: ");
			for (int j = 0; j < exceptionTypes.length; j++) {
				String exceptionName = exceptionTypes[j].getName();
				int k = j + 1;
				System.out.println("     " + k ") " + exceptionName);
			}
			System.out.println();

			//get the method's modifiers and print
			System.out.println("   Modifiers: ");
			int mod = methodList[i].getModifiers();
			testModifiers(mod);
			System.out.println();

			//get return type and print
			String returnType = mtehodList[i].getReturnType().getName();
			System.out.println("   Return Type: " + returnType);
			
		}

	}
*/

/*

	//method that inspects the constructor of the object
	public void inspectConstructors () {
		Constructor myCons;
		//myCons = currentClass.getConstructor(
		
	}
*/
	/*
	
	//method that inspects the class fields
	public void inspectFields () {
		Field[] fieldList = currentClass.getDeclaredFields();
		for (int i = 0; i < fieldList.length; i++) {
			String fieldName = fieldList[i].getName();
			
		}
	}

	//method that checks each modifier and if true, will print
	public void testModifiers(int mod) {
		if(Modifier.isAbstact(mod))
			System.out.print("Abstract ");
		if(Modifier.isFinal(mod))
			System.out.print("Final ");
		if(Modifier.isInterface(mod))
			System.out.print("Interface ");
		if(Modifier.isNative(mod))
			System.out.println("Native ");
		if(Modifier.isPrivate(mod))
			System.out.println("Private ");
		if(Modifier.isProtected(mod))
			System.out.println("Protected ");
		if(Modifier.isPublic(mod))
			System.out.println("Public ");
		if(Modifier.isStatic(mod))
			System.out.println("Static ");
		if(Modifier.isStrict(mod))
			System.out.println("Strict ");
		if(Modifier.isSynchronized(mod))
			System.out.println("Synchronized ");
		if(Modifier.isTransient(mod))
			System.out.println("Transient ");
		if(Modifier.isVolatile(mod))
			System.out.println("Volatile ");
	}
*/

}
