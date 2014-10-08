

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



public class CalculatorTester {
	private Calculator testCalc;
	public int num1;
	public int num2;
	
	
	@Before
	public void setUp() {
		testCalc = new Calculator();
		num1 = 0;
		num2 = 0;
	}
	
	
	@After
	public void tearDown() {
		testCalc = null;
	}


	//tests 0 + 0 = 0
	@Test
	public void testCalc_AdditionCase1() {
		int result = testCalc.add(num1, num2);
		int expResult = 0;
		assertEquals(expResult, result);
	}
	
	//tests 5 + -5 = 0
	@Test
	public void testCalc_AdditionCase2() {
		num1 = 5;
		num2 = -5;
		int result = testCalc.add(num1, num2);
		int expResult = 0;
		assertEquals(expResult, result);	
	}

	//tests 0 - 0 = 0
	@Test
	public void testCalc_SubtractionCase1() {
		int result = testCalc.subtract(num1, num2);
		int expResult = 0;
		assertEquals(expResult, result);
	}
	
	//tests -3 - -20 = 17
	@Test
	public void testCalc_SubtractionCase2() {
		num1 = -3;
		num2 = -20;
		int result = testCalc.subtract(num1, num2);
		int expResult = 17;
		assertEquals(expResult, result);
	}
	
	//tests 0 * 0 = 0
	@Test
	public void testCalc_MultiplicationCase1() {
		int result = testCalc.multiply(num1, num2);
		int expResult = 0;
		assertEquals(expResult, result);
	}
	
	//tests -6 * -3
	@Test
	public void testCalc_MultiplicationCase2() {
		num1 = -6;
		num2 = -3;
		int result = testCalc.multiply(num1, num2);
		int expResult = 18;
		assertEquals(expResult, result);
	}	
	
	
	//tests 0 / 0
	@Test (expected=ArithmeticException.class)
	public void testCalc_DivisionCase1() {
		int result = testCalc.divide(num1, num2);
	}	
	
	//tests 6 / -3
	@Test
	public void testCalc_DivisionCase2() {
		num1 = 6;
		num2 = -3;
		int result = testCalc.divide(num1, num2);
		int expResult = -2;
		assertEquals(expResult, result);
	}

}