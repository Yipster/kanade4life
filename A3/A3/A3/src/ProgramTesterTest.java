import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class ProgramTesterTest {
	private ObjectCreator objectCreator;
	private Serializer serializer;
	private Deserializer deserializer;
	private Visualizer visualizer;
	
	@Before
	public void setUp(){
		objectCreator = new ObjectCreator();
		serializer = new Serializer();
		deserializer = new Deserializer();
		visualizer = new Visualizer();
	}
	
	@After
	public void tearDown() {
		objectCreator = null;
		serializer = null;
		deserializer = null;
		visualizer = null;
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
