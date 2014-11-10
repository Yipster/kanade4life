import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

public class Deserializer {
	public Object deserialize(org.jdom.Document document) {
		Object obj = new Object();
		try {
			Element rootNode = document.getRootElement();
			List<Element> objects = rootNode.getChildren();
			for(int i = 0; i < objects.size(); i++ ){
				Element object = (Element) objects.get(i);
				String objectName = object.getAttributeValue("class");
				System.out.println("Class: " + objectName);
				
				//print out field values
				List<Element> fields = object.getChildren();
				ArrayList fieldValues = new ArrayList();
				for(int j = 0; j < fields.size(); j++) {
					Element field = (Element) fields.get(j);
					int k = j+1;
					System.out.print("  Field" + k + ": " + field.getAttributeValue("name"));
					if(field.getChildText("value") != null) {
						System.out.print(" = " + field.getChildText("value"));					
					}
					else if(field.getChildText("reference") != null) {
						System.out.print(" = " + field.getChildText("reference"));
					}
					System.out.println();
				}
				System.out.println();
				
				
				
				//create object here
				//if object is called Foo1 which only contains primitive types
				if(objectName.equals("Foo1"))
					obj = createFoo1(fields);
				
				else if(objectName.equals("Foo2")) {
					//functionality for 
				}
				
				
			}
		} catch (Exception e) {
			System.out.println("Error in deserializing xml file");
		}
		
		
		return obj;
	}

	
	
	public Object createFoo1(List<Element> fields) {
		Object obj = new Object();
		try {
			Constructor c = Class.forName("Foo1").getConstructor(Integer.TYPE, String.class);
			int value = Integer.parseInt(fields.get(0).getChildText("value"));
			String punchphrase = fields.get(1).getChildText("value");
			obj = c.newInstance(value, punchphrase);
		} catch(Exception e) {
			System.out.println("Problem with creating Foo1 object.");
		}
		return obj;
	}
	
	
	
	
	
	public Object findXMLFile() {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("awesome.xml");
		Object obj = new Object();
		try {
			Document doc = (Document) builder.build(xmlFile);
			obj = deserialize(doc);
		} catch (Exception e) {
			System.out.println("Problem with loading file");
		}
		return obj;
	}
}