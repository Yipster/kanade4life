
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Serializer {

	public Scanner in;
	
	public Serializer() {
		in = new Scanner(System.in);
	}
	
	public 
	
	

	public org.jdom.Document serialize(Object obj) {
		System.out.println("Beginning to Serialize Object.....");
		try {
			//set first tag to be serialize
			Element serialize = new Element("serialize");
			Document doc = new Document(serialize);
			doc.setRootElement(serialize);
			
			//set second tag to be object
			Element
			
		
		}
		catch (Exception e) {
			System.out.println("Some error has occured when creating JDom
		}
		
	}



}