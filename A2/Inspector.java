/*
Author: Brandon Yip
CPSC 501: Advanced Programming Technique
Assignment 2

This is the a class called Inspector that contains a method named inspect.

*/

import java.lang.reflect.*;
import java.util.*;

public class Inspector {

	/*
	inspects an object that is given and a boolean that asks if it should be recursive or not
	*/
	public void inspect(Object obj, boolean recursive) {
		
		//1) Get name of declaring class
		System.out.println("The name of this object's class is " + obj.getClass().getName());
		System.out.println();
		
		//2) Get name of immediate superclass
		Class currentClass = obj.getClass();
		Class superclass = currentClass.getSuperclass();
		String className = superclass.getName();
		System.out.println("The name of the superclass of this object's class is " + className);
		System.out.println();

		//3) Name of interfaces that the class implements
		System.out.println("The interface(s) of this object's class is(are):");
		Class[] theInterfaces = currentClass.getInterfaces();
		for (int i = 0; i < theInterfaces.length; i++ ){
			String interfaceName = theInterfaces[i].getName();
			System.out.println(interfaceName);
		}
		System.out.println();

		
		//4) Methods the class declares
		System.out.println("The method(s) the class declares:")
		Method[] methodList = currentClass.getMethods();
		for (int i = 0; i < methodList.length; i++) {
			//get method name and print
			String methodName = methodList[i].getName();
			System.out.println("Name: " + methodName);
			
			Class[] paramterTypes = methodList[i].getParameterTypes();
			System.out.println("   Parameter Types: ");
			for (int j = 0; j < parameterTypes.length; j++) {
				String parameterName = parameterTypes[j].getName();
				int k = j + 1;
				System.out.println("     " + k + ") " + parameterName);
			}
			
			//get return type and print
			String returnType = mtehodList[i].getReturnType().getName();
			System.out.println("   Return Type: " + returnType);
			
		}
	}


}