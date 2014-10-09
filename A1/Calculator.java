
//Author: Brandon Yip
// Does actual calculation and returns it to CalculatorInterface.java
public class Calculator {
    public int add (int num1, int num2) {
		return num1 + num2;
    }

    public int subtract (int num1, int num2) {
		return num1 - num2;
    }

    public int multiply (int num1, int num2) {
		return num1 * num2;
    }

    public int divide (int num1, int num2) {
		int num3 = 0;
		try {
			num3 = num1 / num2;
		} catch (ArithmeticException e){
			System.out.println("Division by zero is invalid.");
			return 0;
		}
		return num3;
    }
}
