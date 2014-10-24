

public class Driver {

	public static Inspector inspector;
	public static ArrayTester arraytester;
	
	public static void main(String[] args) {
		inspector = new Inspector();
		arraytester = new ArrayTester();
		
		inspector.inspect(arraytester, true);
	
	
	}


}