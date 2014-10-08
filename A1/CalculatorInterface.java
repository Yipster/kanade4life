/*
Author: Brandon Yip

Basic calculator program

 */
import java.util.Scanner;

public class CalculatorInterface {
    private Scanner in;
    public String command;
    private ConvertLetter convertLetter;
    private SubtractNumbers subtraction;
    private MultiplyNumbers multiplication;
    private DivideNumbers division;  
    public char convertedChar;
    public int firstNum;
    public int secondNum;
    public int result;
	public int[] savedNumbers;

    public CalculatorInterface() {
		convertLetter = new ConvertLetter();
		subtraction = new SubtractNumbers();
		multiplication = new MultiplyNumbers();
		division = new DivideNumbers();
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
					System.out.println("Addition: Input two numbers.");
					try {
					firstNum = Integer.parseInt(in.nextLine());
					secondNum = Integer.parseInt(in.nextLine());
					result = firstNum + secondNum;
					System.out.println("Result is: " + result);
					}
					catch(NumberFormatException e){
					System.out.println("One or both inputs were not numbers!");
					}
					break;
				case 'S': case 's':
					System.out.println("Subtraction: Input two numbers.");
					try {
						firstNum = Integer.parseInt(in.nextLine());
						secondNum = Integer.parseInt(in.nextLine());
						result = subtraction.subtract(firstNum, secondNum);
						System.out.println("Result is: " + result);
					}
					catch(NumberFormatException e){
						System.out.println("One or both inputs were not numbers!");
					}
					break;
				case 'M': case 'm':
					System.out.println("Multiplying");
					try {
						firstNum = Integer.parseInt(in.nextLine());
						secondNum = Integer.parseInt(in.nextLine());
						result = multiplication.multiply(firstNum, secondNum);
						System.out.println("Result is: " + result);
					}
					catch(NumberFormatException e){
						System.out.println("One or both inputs were not numbers!");
					}
					break;
				case 'D': case 'd':
					System.out.println("Dividing");
					try {
						firstNum = Integer.parseInt(in.nextLine());
						secondNum = Integer.parseInt(in.nextLine());
						result = division.divide(firstNum, secondNum);
						System.out.println("Result is: " + result);
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
		} while(!(command.equalsIgnoreCase("q")));
    }
	
	public void storefromsavedNumbers () {
		//method to store answer into savedNumbers
	}

	public void getfromsavedNumbers() {
		//method to get stored answer from savedNumbers
	}
}
