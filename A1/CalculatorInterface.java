/*
Author: Brandon Yip

Basic calculator program

 */
import java.util.Scanner;

public class CalculatorInterface {
    private Scanner in;
    public String command;
    public char convertedChar;
    public int firstNum;
    public int secondNum;
    public int result;
	public int[] savedNumbers;

    public CalculatorInterface() {
		command = "";
		savedNumbers = new int[10];
    }

    public void display()
    {
		System.out.println("\nCALCULATOR OPTIONS:");
		System.out.println("-> ADD: type 'a'.");
		System.out.println("-> SUBTRACT: type 's'.");
		System.out.println("-> MULTIPLY: type 'm'.");
		System.out.println("-> DIVIDE: type 'd'.");
		System.out.println("-> Quit Program: type 'q'.");
    }

	
	public void addition() {
		System.out.println("Addition: Input two numbers.");
		try {
			firstNum = Integer.parseInt(in.nextLine());
			secondNum = Integer.parseInt(in.nextLine());
			result = firstNum + secondNum;
			System.out.println("Result is: " + result);
		} catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		}
	}
	
	public void subtraction() {
		System.out.println("Subtraction: Input two numbers.");
		try {
			firstNum = Integer.parseInt(in.nextLine());
			secondNum = Integer.parseInt(in.nextLine());
			result = firstNum - secondNum;
			System.out.println("Result is: " + result);
		}
		catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		}
	}
	
	public void multiplication() {
		System.out.println("Multiplying");
		try {
			firstNum = Integer.parseInt(in.nextLine());
			secondNum = Integer.parseInt(in.nextLine());
			result = firstNum * secondNum;
			System.out.println("Result is: " + result);
		}
		catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		}
	}
	
	
	public void division() {
		System.out.println("Dividing");
		try {
			firstNum = Integer.parseInt(in.nextLine());
			secondNum = Integer.parseInt(in.nextLine());
			result = firstNum / secondNum;
			System.out.println("Result is: " + result);
		}
		catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		}
	}
	
	
	
    public void start() {
		in = new Scanner(System.in);
		do{
			display();
			command = in.nextLine();
			if(command.length() != 1)
			System.out.println("Sup you didn't put one character");
			else {
				convertedChar = command.charAt(0);
				switch (convertedChar){
				case 'A': case 'a':
					addition();
					break;
				case 'S': case 's':
					subtraction();
					break;
				case 'M': case 'm':
					multiplication();
					break;
				case 'D': case 'd':
					division();
					break;
				case 'q': case 'Q':
					System.out.println("Quiting");
					break;
				default:
					System.out.println("Invalid");
					break;
				}
			}
		} while(!(command.equalsIgnoreCase("q")));
    }
	
	public void storefromsavedNumbers () {
		//method to store answer into savedNumbers
	}

	public void getfromsavedNumbers() {
		//method to get stored answer from savedNumbers
	}
}
