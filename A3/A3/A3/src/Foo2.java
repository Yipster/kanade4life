/*
Author: Brandon Yip
 A test class that contains primitive fields and a Foo1 field (object field)
*/

public class Foo2 {

	public int value2;
	public String punchphrase2;
	public Foo1 foo1;

	public Foo2() {
		foo1 = new Foo1();
	}
	
	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	public String getPunchphrase2() {
		return punchphrase2;
	}

	public void setPunchphrase2(String punchphrase2) {
		this.punchphrase2 = punchphrase2;
	}
}
