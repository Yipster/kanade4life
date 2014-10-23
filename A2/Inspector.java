/*
Author: Brandon Yip
CPSC 501: Advanced Programming Technique
Assignment 2

This is the a class called Inspector that contains a method named inspect.
inspect @params
- Object: obj				an object to inspect
- boolean: recursive		if set true, it will continue up the hierarchy to recursively inspect objects

inspect specifically inspects

*/

import java.lang.reflect.*;
import java.util.*;
//import java.awt.*;

public class Inspector {

	Object currentObject;
	Class currentClass;
	Class superClass;
	boolean end;
	public static List<Class> seen = new ArrayList<Class>();
	
	/*
	inspects an object that is given and a boolean that asks if it should be recursive or not
	*/
	public void inspect(Object obj, boolean recursive) {
	
		inspector(obj);
		
		if(recursive) {
			end = false;
			while(superClass != null && end == false){
				try {
					inspector(obj);
				}
				catch (Exception e) {
					System.out.println("Cannot make object of this class");
					end = true;
				}
			}
		}
	}


	
	public void inspector(Object obj) {
		currentObject = obj;
		//1) Get name of declaring class
		System.out.println("========================================================================");
		System.out.println("Class Name: " + obj.getClass().getName());
		
		//2) Get name of immediate superclass
		currentClass = obj.getClass();
		superClass = currentClass.getSuperclass();
		if(superClass!= null) {
			String className = superClass.getName();
			System.out.println("Superclass Name: " + className);
		}
		else {
			System.out.println("Superclass Name: null");
		}
		System.out.println();
		System.out.println("--------------------------------------");
		
		//3) Name of interfaces that the class implements
		inspectInterfaces();
		System.out.println("--------------------------------------");
		
		//4) Methods that the class declares. 
		inspectMethods();
		System.out.println("--------------------------------------");
		
		//5) Constructor(s) that the class has
		inspectConstructors();
		System.out.println("--------------------------------------");
		
		//6) Fields that the class has
		inspectFields();
		System.out.println("--------------------------------------");
		System.out.println("========================================================================");
	}
	
	
	
	
	
	

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
			Method m = methodList[i];
			//get method name and print
			int k = i + 1;
			String methodName = m.getName();
			System.out.println(k + ") Name: " + methodName);
			
			//get parameter types of the specified method and print
			Class[] parameterTypes = m.getParameterTypes();
			System.out.print("   Parameter Types: ");
			printList(parameterTypes);
			System.out.println();

			//get exception types of the specified method and print			
			Class[] exceptionTypes = m.getExceptionTypes();
			System.out.print("   Exception Types: ");
			printList(exceptionTypes);
			System.out.println();

			//get the method's modifiers and print
			int mod = m.getModifiers();
			testModifiers(mod);

			//get return type and print
			String returnType = m.getReturnType().getName();
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
			Field f = fieldList[i];
			f.setAccessible(true);
			//get each field name
			int k = i + 1;
			String fieldName = f.getName();
			System.out.println(k + ") Name: " + fieldName);

			//get type of field of each field
			//get value of the field
			Class typeClass = f.getType();
			try {
				Object value = f.get(currentObject);
				//handles normal primitives
				if(typeClass.isPrimitive()) {
					System.out.println("   Type: " + typeClass.getName());
					System.out.print("   Value: " + value.toString());
				}
				//handles arrays
				else if(value.getClass().isArray()) {
					fieldArrays(value);
				}
				//handles objects
				else {
					System.out.println("   Type: " + typeClass.getName());
					System.out.print("   Pointer Value: " + System.identityHashCode(value));
				}
				System.out.println();
			}
			catch (Exception e) {
				System.out.println("Error in printing value of field");
			}
			
			//test modifiers of each field
			int mod = fieldList[i].getModifiers();
			testModifiers(mod);
			System.out.println();
		}
	}

	
	public void fieldArrays(Object object) {
		if (object.getClass().isArray()) {
			Class<?> componentType;
			componentType = object.getClass().getComponentType();
			if (componentType.isPrimitive()) {
				if (boolean.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: boolean[]");
					System.out.print("   Value: [");
					for (boolean anElement : (boolean[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}
				
				else if (byte.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: byte[]");
					System.out.print("   Value: [");
					for(byte anElement : (byte[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}

				else if (char.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: char[]");
					System.out.print("   Value: [");
					for(char anElement : (char[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}

				else if (double.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: double[]");
					System.out.print("   Value: [");
					for(double anElement : (double[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}

				else if (float.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: float[]");
					System.out.print("   Value: [");
					for(float anElement : (float[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}

				else if (int.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: int[]");
					System.out.print("   Value: [");
					for (int anElement : (int[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}

				else if (long.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: long[]");
					System.out.print("   Value: [");
					for (long anElement : (long[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}

				else if (short.class.isAssignableFrom(componentType)) {
					System.out.println("   Type: short[]");
					System.out.print("   Value: [");
					for (short anElement : (short[]) object) {
						System.out.print(anElement + ", ");
					}
					System.out.print("]");
				}
			}

			else {
				//handle Object[]
				
				System.out.println("   Type: " + componentType.getName());
				System.out.print("   Value: [");
				for(Object anElement : (Object[]) object) {
					System.out.print(System.identityHashCode(anElement) + ", ");
				}
				System.out.print("]");
				
				/*if(recursive) {
					//recursive functionality. 
				}*/
			}
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
