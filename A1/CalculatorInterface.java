/*
Author: Brandon Yip

Basic calculator program

 */
import java.util.Scanner;

public class CalculatorInterface {
    private Scanner in;
    public String var1;
    private ConvertLetter prog1;
    private Calculator prog2;
    private SubtractNumbers prog3;
    private MultiplyNumbers prog4;
    private DivideNumbers prog5;  
    public char var2;
    public int num1;
    public int num2;
    public int num3;
	public int[] array;

    public CalculatorInterface() {
	prog2 = new Calculator();
	prog1 = new ConvertLetter();
	prog3 = new SubtractNumbers();
	prog4 = new MultiplyNumbers();
	prog5 = new DivideNumbers();
	var1 = "";
	array = new int[10];
    }

    public void display()
    {
	System.out.println("\nCALCULATOR OPTIONS:");
	System.out.println("-> ADD: type 'a'.");
	System.out.println("-> SUBTRACT: type 's'.");
	System.out.println("-> MULTIPLY: type 'm'.");
	System.out.println("-> DIVIDE: type 'd'.");
    }

    public void start() {
	in = new Scanner(System.in);
	do{
	    display();
	    var1 = in.nextLine();
	    if(var1.length() != 1)
		System.out.println("Sup you didn't put one character");
	    else {
		var2 = prog1.convertToAscii(var1);
		switch (var2){
		case 'A': case 'a':
		    System.out.println("Addition: Input two numbers.");
		    try {
			num1 = Integer.parseInt(in.nextLine());
			num2 = Integer.parseInt(in.nextLine());
			num3 = num1 + num2;
			System.out.println("Result is: " + num3);
		    }
		    catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		    }
		    break;
		case 'S': case 's':
		    System.out.println("Subtraction: Input two numbers.");
		    try {
			num1 = Integer.parseInt(in.nextLine());
			num2 = Integer.parseInt(in.nextLine());
			num3 = prog3.subtract(num1, num2);
			System.out.println("Result is: " + num3);
		    }
		    catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		    }
		    break;
		case 'M': case 'm':
		    System.out.println("Multiplying");
		    try {
			num1 = Integer.parseInt(in.nextLine());
			num2 = Integer.parseInt(in.nextLine());
			num3 = prog4.multiply(num1, num2);
			System.out.println("Result is: " + num3);
		    }
		    catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		    }
		    break;
		case 'D': case 'd':
		    System.out.println("Dividing");
		    try {
			num1 = Integer.parseInt(in.nextLine());
			num2 = Integer.parseInt(in.nextLine());
			num3 = prog5.divide(num1, num2);
			System.out.println("Result is: " + num3);
		    }
		    catch(NumberFormatException e){
			System.out.println("One or both inputs were not numbers!");
		    }
		    break;
		case 'q': case 'Q':
		    System.out.println("Quiting");
		    break;
		default:
		    System.out.println("Invalid");
		    break;
		}
	    }
	} while(!(var1.equalsIgnoreCase("q")));
    }
	
	public void storefromArray () {
		//method to store answer into array
	}

	public void getfromArray() {
		//method to get stored answer from array
	}
}
