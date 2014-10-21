
public class Driver {
	public static void main(String[] args) {
		Bicycle test = new Bicycle(1, 1, 1);
		Inspector inspector = new Inspector();
		inspector.inspect(test, false);
	}

}