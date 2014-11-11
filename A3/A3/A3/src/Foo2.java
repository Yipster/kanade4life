/*
Author: Brandon Yip
 A test class that contains primitive fields and a Foo1 field (object field)
*/

public class Foo2 {
	public int value2;
	public String punchphrase2;
	public Foo1 foo1;

	public Foo2() {

	}

	public Foo2(int value2, String punchphrase2, int value, String punchphrase) {
		this.value2 = value2;
		this.punchphrase2 = punchphrase2;
		foo1 = new Foo1(value, punchphrase);
	}
	

}
