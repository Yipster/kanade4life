
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



public class InspectorTester {
	private Inspector inspector;
	private ClassA classA;
	
	@Before
	public void setUp() {
		inspector = new Inspector();
		classA = new ClassA();
	}
	
	@After
	public void tearDown() {
		inspector = null;
		classA = null;
	}
	
	
	@Test
	public void inspectorTest_className() {
		inspector.inspect(classA, false)
	}


}