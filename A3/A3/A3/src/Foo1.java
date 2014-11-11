/*
Author: Brandon Yip
 A test class that only contains primitive fields
*/

public class Foo1 {
	public int value;
	public String punchphrase;

	public Foo1() {

	}

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
