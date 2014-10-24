/*
Author: Brandon Yip
CPSC 501: Advanced Programming Technique
Assignment 2

This is the a class called Inspector that contains a method named inspect.
inspect @params
- Object: obj				an object to inspect
- boolean: recursive		if set true, it will continue up the hierarchy to recursively inspect objects

Output will be towards a text file called output.txt

*/

import java.lang.reflect.*;
import java.util.*;
import java.io.*;
//import java.awt.*;

public class Inspector {

	Object currentObject;
	Scanner sc = new Scanner(System.in);
	public static Stack<Class> queue = new Stack<Class>();
	public static ArrayList<String> seen;
	PrintWriter writer;
	
	/*
	inspects an object that is given and a boolean that asks if it should be recursive or not
	*/
	public void inspect(Object obj, boolean recursive) {
		try {
			//creates a new printwriter to output into the file
			System.out.println("Type out a name for output file. Do not add .txt at the end.");
			String output = sc.nextLine();
			output = output + ".txt";
			writer = new PrintWriter(output, "UTF-8");
			seen = new ArrayList<String>();
			
			Class c = obj.getClass();
			seen.add(c.getName());
			inspector(obj,c, recursive);
			/*
			if(recursive) {
				end = false;
				while(superClass != null && end == false){
					try {
						inspector(obj);
					}
					catch (Exception e) {
						writer.println("Cannot make object of this class");
						end = true;
					}
				}
			}*/
			writer.close();
		}
		catch(Exception e) {
			System.out.println("File failed to create");
		}
	}


	
	public void inspector(Object obj, Class classObject, boolean recursive) {
		Class currClass = classObject;
		currentObject = obj;
		
		//1) Get name of declaring class
		writer.println("========================================================================");
		inspectClass(currClass);
		
		//2) Get name of immediate superclass
		inspectSuperClass(currClass);
		writer.println();
		writer.println("--------------------------------------");
		
		//3) Name of interfaces that the class implements
		inspectInterfaces(currClass);
		writer.println("--------------------------------------");
		
		//4) Methods that the class declares. 
		inspectMethods(currClass);
		writer.println("--------------------------------------");
		
		//5) Constructor(s) that the class has
		inspectConstructors(currClass);
		writer.println("--------------------------------------");
		
		//6) Fields that the class has
		inspectFields(recursive, currClass);
		writer.println("--------------------------------------");
		writer.println("========================================================================");
		
		if(!queue.empty()) {
			seen.add(queue.peek().getName());
			inspector(obj, queue.pop(), recursive);
		}
	}
	public String inspectClass(Class currClass) {
		writer.println("Class Name: " + currClass.getName());
		return currClass.getName();
	}
	
	//method that inspects the super class of the current object.
	public String inspectSuperClass(Class currClass) {
		Class superClass = currClass.getSuperclass();
		String className = "";
		if(superClass!= null) {
			className = superClass.getName();
			if(!seen.contains(className))
				queue.push(superClass);
			writer.println("Superclass Name: " + className);
		}
		else {
			writer.println("Superclass Name: null");
		}
		return className;
	}
	
	

	//method that inspects the interfaces of the object 
	public String[] inspectInterfaces(Class currClass) {
		writer.println("The interface(s) of this object's class is(are):");
		Class[] interfaceList = currClass.getInterfaces();
		String[] interfaceNames = new String[interfaceList.length];
		for(int i = 0; i < interfaceList.length; i++) {
			if(!seen.contains(interfaceList[i].getName()))
				queue.push(interfaceList[i]);
			interfaceNames[i] = interfaceList[i].getName();
		}
		printList(interfaceList);
		writer.println();
		writer.println();
		return interfaceNames;
	}




	//method that inspects the methods of the object
	public String[] inspectMethods(Class currClass) {
		//4) Methods the class declares
		writer.println("The method(s) the class declares:");
		Method[] methodList = currClass.getDeclaredMethods();
		String[] methodNames = new String[methodList.length];
		for (int i = 0; i < methodList.length; i++) {
			Method m = methodList[i];
			//get method name and print
			int k = i + 1;
			String methodName = m.getName();
			methodNames[i] = methodName;
			writer.println(k + ") Name: " + methodName);
			
			//get parameter types of the specified method and print
			Class[] parameterTypes = m.getParameterTypes();
			writer.print("   Parameter Types: ");
			printList(parameterTypes);
			writer.println();

			//get exception types of the specified method and print			
			Class[] exceptionTypes = m.getExceptionTypes();
			writer.print("   Exception Types: ");
			printList(exceptionTypes);
			writer.println();

			//get the method's modifiers and print
			int mod = m.getModifiers();
			testModifiers(mod);

			//get return type and print
			String returnType = m.getReturnType().getName();
			writer.println("   Return Type: " + returnType);
			writer.println();
		}
		return methodNames;
	}




	//method that inspects the constructor(s) of the object
	public void inspectConstructors (Class currClass) {
		writer.println("The constructor(s) in " + currClass.getName() + ":");
		Constructor[] constructorList = currClass.getDeclaredConstructors();
		for (int i = 0; i < constructorList.length; i++) {
			//get the constructor i's parameter types
			Class[] parameterTypes = constructorList[i].getParameterTypes();
			int k = i + 1;
			writer.print(" " + k + ") " + "Parameters: ");
			printList(parameterTypes);
			writer.println();
			writer.print("  ");
			int mod = constructorList[i].getModifiers();
			testModifiers(mod);
			writer.println();
		}
		
	}

	
	
	//method that inspects the class field(s)
	public String[] inspectFields (boolean recursive, Class currClass) {
		Field[] fieldList = currClass.getDeclaredFields();
		String[] fieldNames = new String[fieldList.length];
		writer.println("The field(s) of this class: ");
		for (int i = 0; i < fieldList.length; i++) {
			Field f = fieldList[i];
			f.setAccessible(true);
			//get each field name
			int k = i + 1;
			String fieldName = f.getName();
			fieldNames[i] = fieldName;
			writer.println(k + ") Name: " + fieldName);

			//get type of field of each field
			//get value of the field
			Class typeClass = f.getType();
			try {
				Object value = f.get(currentObject);
				//handles normal primitives
				if(typeClass.isPrimitive()) {
					writer.println("   Type: " + typeClass.getName());
					writer.print("   Value: " + value.toString());
				}
				//handles arrays
				else if(value.getClass().isArray()) {
					fieldArrays(value, recursive);
				}
				//handles objects
				else {
					writer.println("   Type: " + typeClass.getName());
					writer.print("   Pointer Value: " + System.identityHashCode(value));
					if(recursive) {
						Class c = value.getClass();
						if(!seen.contains(c.getName()));
							queue.push(c);
					}
				}
				writer.println();
			}
			catch (Exception e) {
				writer.println("Error in printing value of field");
			}
			
			//test modifiers of each field
			int mod = fieldList[i].getModifiers();
			testModifiers(mod);
			writer.println();
		}
		return fieldNames;
	}

	
	//handles printing of the array
	public void fieldArrays(Object object, boolean recursive) {
		if (object.getClass().isArray()) {
			Class<?> componentType;
			componentType = object.getClass().getComponentType();
			if (componentType.isPrimitive()) {
				if (boolean.class.isAssignableFrom(componentType)) {
					writer.println("   Type: boolean[]");
					writer.print("   Value: [");
					for (boolean anElement : (boolean[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}
				
				else if (byte.class.isAssignableFrom(componentType)) {
					writer.println("   Type: byte[]");
					writer.print("   Value: [");
					for(byte anElement : (byte[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}

				else if (char.class.isAssignableFrom(componentType)) {
					writer.println("   Type: char[]");
					writer.print("   Value: [");
					for(char anElement : (char[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}

				else if (double.class.isAssignableFrom(componentType)) {
					writer.println("   Type: double[]");
					writer.print("   Value: [");
					for(double anElement : (double[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}

				else if (float.class.isAssignableFrom(componentType)) {
					writer.println("   Type: float[]");
					writer.print("   Value: [");
					for(float anElement : (float[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}

				else if (int.class.isAssignableFrom(componentType)) {
					writer.println("   Type: int[]");
					writer.print("   Value: [");
					for (int anElement : (int[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}

				else if (long.class.isAssignableFrom(componentType)) {
					writer.println("   Type: long[]");
					writer.print("   Value: [");
					for (long anElement : (long[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}

				else if (short.class.isAssignableFrom(componentType)) {
					writer.println("   Type: short[]");
					writer.print("   Value: [");
					for (short anElement : (short[]) object) {
						writer.print(anElement + ", ");
					}
					writer.print("]");
				}
			}

			else {
				//handle Object[]
				
				writer.println("   Type: " + componentType.getName());
				writer.print("   Value: [");
				for(Object anElement : (Object[]) object) {
					writer.print(System.identityHashCode(anElement) + ", ");
					if(recursive) {
						Class c = anElement.getClass();
						if(!seen.contains(c.getName()));
							queue.push(c);
					}					
				}
				writer.print("]");
			}
		}
	}
	
	
	
	
	//prints any list that is of type Class
	public void printList(Class[] c) {
		for(int j = 0; j < c.length; j++) {
			String name = c[j].getName();
			writer.print(name + "  ");
		}
	}

	
	
	//method that checks each modifier and if true, will print
	public void testModifiers(int mod) {
		writer.print("   Modifiers: ");
		if(Modifier.isAbstract(mod))
			writer.print("Abstract ");
		if(Modifier.isFinal(mod))
			writer.print("Final ");
		if(Modifier.isInterface(mod))
			writer.print("Interface ");
		if(Modifier.isNative(mod))
			writer.print("Native ");
		if(Modifier.isPrivate(mod))
			writer.print("Private ");
		if(Modifier.isProtected(mod))
			writer.print("Protected ");
		if(Modifier.isPublic(mod))
			writer.print("Public ");
		if(Modifier.isStatic(mod))
			writer.print("Static ");
		if(Modifier.isStrict(mod))
			writer.print("Strict ");
		if(Modifier.isSynchronized(mod))
			writer.print("Synchronized ");
		if(Modifier.isTransient(mod))
			writer.print("Transient ");
		if(Modifier.isVolatile(mod))
			writer.print("Volatile ");
		writer.println();
	}

}
