/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that will deserializes a org.jdom.Document and returns an object.
 - Requires you to pass in an org.jdom.Document
 - Handles objects created by ObjectCreator only including arrays.
 - Parses and deserializes the doc
 - Outputs an object that is created using reflection.

*/

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

public class Deserializer {
	public Object deserialize(org.jdom.Document document) {
		System.out.println("===============================================\n"
				+ "Deserializing object......");
		Object obj = new Object();
		try {
			Element rootNode = document.getRootElement();
			List<Element> objects = rootNode.getChildren();
			/*
			//prints it out to console just for now.
			for(int i = 0; i < objects.size(); i++ ){
				Element object = (Element) objects.get(i);
				String objectName = object.getAttributeValue("class");
				System.out.println("Class: " + objectName);
				
				//print out field values
				List<Element> fields = object.getChildren();
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
			}*/
			
			//createObject here
			obj = createObject(objects);
		} catch (Exception e) {
			System.out.println("Error in deserializing xml file");
		}
		
		
		return obj;
	}

	
	//create objects method
	public Object createObject(List<Element> objects){
		Object obj = new Object();
		for(int i = 0; i < objects.size(); i++) {
			Element object = (Element) objects.get(i);
			String objectName = object.getAttributeValue("class");
			List<Element> fields = object.getChildren();
			//if object is called Foo1 which only contains primitive types
			if(objectName.equals("Foo1"))
				obj = createFoo1(fields);
			
			else if(objectName.equals("Foo2")) {
				//functionality for creating a Foo2 object.
				//Note: Need to create a Foo1 object as well;
				Element object2 = (Element)objects.get(i+1);
				List<Element> fields2 = object2.getChildren();
				i++;
				obj = createFoo2(fields, fields2);
			}
			else if(objectName.equals("Foo3")) {
				//functionality for creating a Foo3 object
				Element messages = (Element)objects.get(i+1);
				Element values = (Element)objects.get(i+2);
				i++; i++;
				
				obj = createFoo3(fields, messages, values);
			}
		}
		return obj;
	}
	

	//this method creates a new Foo1 object from the information of the JDOM
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
	
	
	//this method creates a new Foo2 object from the information of the JDOM
	public Object createFoo2(List<Element> fields, List<Element> fields2) {
		Object obj = new Object();
		try {
			Constructor c = Class.forName("Foo2").getConstructor();
			int value = Integer.parseInt(fields.get(0).getChildText("value"));
			String punchphrase = fields.get(1).getChildText("value");
			obj = c.newInstance();
			Foo1 foo1 = (Foo1) createFoo1(fields2);
			Foo2 foo2 = (Foo2) obj;
			foo2.setPunchphrase2(punchphrase);
			foo2.setValue2(value);
			foo2.foo1 = foo1;
			obj = foo2;
			
		} catch(Exception e) {
			System.out.println("Problem with creating Foo2 object.");
		}
		
		return obj;
	}
	
	
	//this method creates a new Foo3 object from the information of the JDOM
	private Object createFoo3(List<Element> fields, Element field2, Element field3) {
		Object obj = new Object();
		try {
			Constructor c = Class.forName("Foo3").getConstructor();
			obj = c.newInstance();
			
			List<Element> fields2 = field2.getChildren("value");
			List<Element> fields3 = field3.getChildren("value");
			
			String[] punchphrases = new String[2];
			punchphrases[0] = fields2.get(0).getText();
			punchphrases[1] = fields2.get(1).getText();
			
			int[] values = new int[3];
			values[0] = Integer.parseInt(fields3.get(0).getText());
			values[1] = Integer.parseInt(fields3.get(1).getText());
			values[2] = Integer.parseInt(fields3.get(2).getText());
			
			Foo3 foo3 = (Foo3) obj;
			foo3.values = values;
			foo3.punchphrases = punchphrases;
			obj = foo3;
		} catch(Exception e) {
			System.out.println("Error in creating a Foo3 object.");
		}
		return obj;
	}
	
	
	public Object createFoo4() {
		Object obj = new Object();
		
		return obj;
	}
	
	
	//this method is to test when importing an XMLFile of the name awesome.xml
	//And seeing if it deserializes properly.
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