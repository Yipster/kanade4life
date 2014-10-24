public class ArrayTester {

	int global;
	Object[] array;
	ClassA classA;
	ClassB classB;
	ClassC classC;
	
	public ArrayTester() {
		try{
			classA = new ClassA();
			classB = new ClassB();
			array = new Object[3];
			array[0] = classA;
			array[1] = classB;
		}
		catch(Exception e) {
			System.out.println("Cannot create classes");
		}
	}

	public void havefun(int abc) {
		global = abc;
	}

}