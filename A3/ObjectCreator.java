
import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public class ObjectCreator {

	private Scanner in;
	public Object myObj;
	public Foo1 foo1;
	

	public ObjectCreator() {
		in = new Scanner(System.in);
	}


	public void display() {
		System.out.println("Hi, welcome to the object creator. You can create an object:");
		System.out.println(" 1) Contain only primitive fields");
		System.out.println(" 2) Contain references to other objects");
		System.out.println(" 3) Contain an array of primitives");
		System.out.println(" 4) Contain an array of object references");
		System.out.println(" Enter 'Q' to quit");
		System.out.println("Input the corresponding number to your desire.");
	}


	// This method makes an instance of an object that only has primitive fields
	public void object1() {
		//Simple object with only primitives for instance variables
		System.out.println("You have selected: Object with primitive fields.");
		System.out.println("This object has two primitive fields, an integer and a string. Enter them one after the other");
		int value = Integer.parseInt(in.nextLine());
		String message = in.nextLine();
		foo1 = new Foo1(value, message);
	}

	// This method makes an instance of an object that has references to other objects
	public void object2() {
		//Object that contains references to other objects.
		System.out.println("You have selected: Object with object references.");
		
	}

	//This method makes an instance of an object that has an array of primitives
	public void object3() {
		//An object that contains array of primitives
		System.out.println("You have selected: Object with an array of primitives.");
	}

	
	//This method makes an instance of an object that has an array of objects
	public void object4() {
		//An object that contians an array of object references
		System.out.println("You have selected: Object with an array of objects.");

	}


	public void start() {
		do {
			display();
			String input = in.nextLine();
			if(input.equals("1")){
				object1();
			}
			else if(input.equals("2")) {

			}
			else if(input.equals("3")) {

			}
			else if(input.equals("4")) {

			}
			else {
				System.out.println("You did not enter a number that is listed here.");
			}
		} while(!input.equalsIgnoreCase("q"));
	}
	
}
