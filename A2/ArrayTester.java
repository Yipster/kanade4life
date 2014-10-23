public class ArrayTester {

	public ClassA[] arrayInt;
	public int global;
	
	public ArrayTester() {
		arrayInt = new ClassA[10];
		for(int i = 0; i < arrayInt.length; i++) {
			arrayInt[i] = new ClassA();
		}
	}

	public void havefun(int abc) {
		global = abc;
	}

}