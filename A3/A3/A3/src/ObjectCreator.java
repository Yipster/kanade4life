/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that will prompt the user to create an object. There are 5 types of objects
 	1) Contain only primitive fields
	2) Contain references to other objects
	3) Contain an array of primitives
	4) Contain an array of object references
	5) Contains a java collection.
	
Then it will call serializer to serialize the object created. This will return a doc as well 
as outport an xml file into the directory called output.xml.

*/

import java.lang.reflect.*;
import java.util.*;
import org.jdom.Document;

public class ObjectCreator {

	private Scanner in;
	public Object myObj;
	public Foo1 foo1;
	public Foo2 foo2;
	public Foo3 foo3;
	public Serializer serializer = new Serializer();
	public Deserializer deserializer = new Deserializer();
	public Visualizer visualizer = new Visualizer();
	

	public ObjectCreator() {
		in = new Scanner(System.in);
	}


	public void display() {
		System.out.println("Hi, welcome to the object creator. You can create an object:");
		System.out.println(" 1) Contain only primitive fields");
		System.out.println(" 2) Contain references to other objects");
		System.out.println(" 3) Contain an array of primitives");
		System.out.println(" 4) Contain an array of object references");
		System.out.println(" 5) Contains a java collection.");
		System.out.println(" Enter 'Q' to quit");
		System.out.println("Input the corresponding number to your desire.");
	}


	// This method makes an instance of an object that only has primitive fields
	// This method makes an instance of an object that only has primitive fields
	public Object object1() {
		//Simple object with only primitives for instance variables
		int value = 0;
		String message = null;
		try{
			System.out.println("Object with primitive fields.");
			System.out.println("Info: This object has two primitive fields:\n"
				+ "  1) Integer field called 'value' \n"
				+ "  2) String field called 'punchphrase' \n"
				+ " Enter them one after the other:");
			System.out.print("First input (Integer): ");
			value = Integer.parseInt(in.nextLine());
			System.out.print("Second input (String): ");
			message = in.nextLine();
		} catch(Exception e) {
			System.out.println("You have entered the wrong inputs."); }
		foo1 = new Foo1();
		foo1.setPunchphrase(message);
		foo1.setValue(value);
		return foo1;
	}

	// This method makes an instance of an object that has references to other objects
	public Object object2() {
		//Object that contains references to other objects.
		int value = 0;	int value2 = 0;	String message = null;	String message2 = null;
		try {
			System.out.println("Object with object references.");
			System.out.println("Info: This object has three fields:\n"
				+ "  1) Integer field called 'value2'\n"
				+ "  2) String field called 'punchphrase2'\n"
				+ "  3) Object Foo1. Foo1 has two primitives, the first an integer and the second a string.\n"
				+ " Enter them one after the other:");
			System.out.print("First input (Integer): ");
			value = Integer.parseInt(in.nextLine());
			System.out.print("Second input (String): ");
			message = in.nextLine();
			System.out.print("Third input (Integer): ");
			value2 = Integer.parseInt(in.nextLine());
			System.out.print("Fourth input (String): ");
			message2 = in.nextLine();
		} catch (Exception e) { 
			System.out.println("You have entered the wrong inputs.");}
		
		foo2 = new Foo2();
		foo2.setPunchphrase2(message);
		foo2.setValue2(value);
		foo2.foo1.setPunchphrase(message2);
		foo2.foo1.setValue(value2);
		return foo2;
	}

	//This method makes an instance of an object that has an array of primitives
	public Object object3() {
		//An object that contains array of primitives
		int value1 = 0;  int value2 = 0;  int value3 = 0; String message = null;  String message2 = null;
		try {
			System.out.println("Object with an array of primitives.");
			System.out.println("Info: This object has two fields:\n"
					+ "  1) Integer array called 'values'. Length = 3\n"
					+ "  2) String array called 'punchphrases. Length = 2'\n"
					+ " Enter the inputs one after the other:");
			System.out.print("First input (values, index 0): ");
			value1 = Integer.parseInt(in.nextLine());
			System.out.print("Second input (values, index 1): ");
			value2 = Integer.parseInt(in.nextLine());
			System.out.print("Third input (values, index 2): ");
			value3 = Integer.parseInt(in.nextLine());
			System.out.print("Fourth input (punchphrases, index 0): ");
			message = in.nextLine();
			System.out.print("Fifth input (punchphrases, index 1): ");
			message2 = in.nextLine();
		} catch(Exception e) {
			System.out.println("You have entered the wrong inputs.");	}
		foo3 = new Foo3();
		foo3.fillValues(value1, value2, value3);
		foo3.fillPunches(message, message2);
		return foo3;
	
	}

	
	//This method makes an instance of an object that has an array of objects
	public Object object4() {
		//An object that contains an array of object references
		System.out.println("Object with an array of objects as the field.");
		System.out.println("Info: This object has an array of size two that can be any of the following objects\n"
				+ " 1) Foo1 (Object with 2 primitive fields)\n"
				+ " 2) Foo2 (Object with 2 primitive fields and a Foo1 field)\n"
				+ " 3) Foo3 (Object with 2 primitive arrays).");
		Foo4 foo4 = new Foo4();
		int i = 0;
		do {
			System.out.print("Input: ");
			String input = in.nextLine();
			if(input.equals("1")){
				Object object = object1();
				foo4.fooList[i] = object;
				i++;
				System.out.println("Object created successfully.");
			}
			else if(input.equals("2")) {
				Object object = object2();
				foo4.fooList[i] = object;
				i++;
				System.out.println("Object created successfully.");
			}
			else if(input.equals("3")) {
				Object object = object3();
				foo4.fooList[i] = object;
				i++;
				System.out.println("Object created successfully.");
			}
			else {
				System.out.println("You did not input 1, 2, or 3.");
			}
		} while (i < 2);
		return foo4;
	}


	public void start() {
		String input;
		do {
			display();
			input = in.nextLine();
			if(input.equals("1")){
				myObj = object1();		
				Document doc = serializer.serialize(myObj);
				Object myobj = deserializer.deserialize(doc);
				visualizer.inspect(myobj);
			}
			else if(input.equals("2")) {
				myObj = object2();
				Document doc = serializer.serialize(myObj);
				Object myobj = deserializer.deserialize(doc);
				visualizer.inspect(myobj);
			}
			else if(input.equals("3")) {
				myObj = object3();
				Document doc = serializer.serialize(myObj);
				Object myobj = deserializer.deserialize(doc);
				visualizer.inspect(myobj);
			}
			else if(input.equals("4")) {
				myObj = object4();
				Document doc = serializer.serialize(myObj);
				Object myobj = deserializer.deserialize(doc);
				visualizer.inspect(myobj);
				
			}
			else if(input.equalsIgnoreCase("q")) {
				System.out.println("Quitting program....");
			}
				
			else {
				System.out.println("You did not enter a number that is listed here.");
			}
		} while(!input.equalsIgnoreCase("q"));
	}
	
}
