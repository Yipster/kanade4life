/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that only contains 2 primitive fields with setters and getters
 1st type of object you can make in ObjectCreator
*/

public class Foo1 {
	public int value;
	public String punchphrase;

	public Foo1(int value, String punchphrase) {
		this.value = value;
		this.punchphrase = punchphrase;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getPunchphrase() {
		return punchphrase;
	}

	public void setPunchphrase(String punchphrase) {
		this.punchphrase = punchphrase;
	}
}
