/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that only contains 2 primitive arrays with setters
 3rd type of object you can make in ObjectCreator
*/

public class Foo3 {
	public int[] values;
	public String[] punchphrases;
	
	public Foo3() {
		values = new int[3];
		punchphrases = new String[2];
	}

	public void fillValues(int a, int b, int c) {
		values[0] = a;
		values[1] = b;
		values[2] = c;
	}
	
	public void fillPunches(String messageA, String messageB) {
		punchphrases[0] = messageA;
		punchphrases[1] = messageB;
	}
	
}