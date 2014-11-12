/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that will serialize an object, returning a doc and also outporting an XML file called output.xml
 - Handles all objects created by objectCreator only.
 - Requires you to pass in an object into serialize() method.
 - Outputs an XMLFile called output.xml
 - returns an org.jdom.Document.
 - Follows the layout of the assignment.

*/
import java.util.*;
import java.lang.reflect.*;
import java.io.FileWriter;

import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Serializer {

	public Document doc;
	public Object currentObject;
	public Class currentClass;
	public Foo1 foo1;
	public Stack<Object> seen = new Stack<Object>();
	public ArrayList<Class> visited = new ArrayList<Class>();
	
	
	
	
	//handles Serialization of elements of arrays
	public Element fieldArrays(Object object, int id) {
		Element objectTagged = new Element("object");
		objectTagged.setAttribute(new Attribute("class", currentClass.getName()));
		objectTagged.setAttribute(new Attribute("id", Integer.toString(id)));
		int length = 0;
		if (object.getClass().isArray()) {
			Class<?> componentType;
			componentType = object.getClass().getComponentType();
			if (componentType.isPrimitive()) {
				//for boolean arrays
				if (boolean.class.isAssignableFrom(componentType)) {
					for (boolean anElement : (boolean[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}
				else if (byte.class.isAssignableFrom(componentType)) {
					for(byte anElement : (byte[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}

				else if (char.class.isAssignableFrom(componentType)) {
					for(char anElement : (char[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}

				else if (double.class.isAssignableFrom(componentType)) {
					for(double anElement : (double[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}

				else if (float.class.isAssignableFrom(componentType)) {
					for(float anElement : (float[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}

				else if (int.class.isAssignableFrom(componentType)) {
					for (int anElement : (int[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}

				else if (long.class.isAssignableFrom(componentType)) {
					for (long anElement : (long[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}

				else if (short.class.isAssignableFrom(componentType)) {
					for (short anElement : (short[]) object) {
						objectTagged.addContent(new Element("value").setText(String.valueOf(anElement)));
						length++;
					}
				}
			}
			else if(String.class.isAssignableFrom(componentType)) {
				for(String anElement : (String[]) object) {
					objectTagged.addContent(new Element("value").setText(anElement));
					length++;
				}
			}

			else {
				//handle Object[]
				for(Object anElement : (Object[]) object) {
					objectTagged.addContent(new Element("reference").setText(anElement.toString()));
					length++;
					seen.push(anElement);
				}
			}
		}
		objectTagged.setAttribute(new Attribute("length", String.valueOf(length)));
		return objectTagged;
	}

	
	public Element createLLElement(Object object, int id) {
		Element linkedList = new Element("object");
		linkedList.setAttribute(new Attribute("class", object.getClass().getName()));
		linkedList.setAttribute(new Attribute("id", Integer.toString(id)));
		LinkedList objectList = (LinkedList) object;
		int i = 0;
		while(objectList.peek() != null) {
			Object newObject = objectList.remove();
			seen.push(newObject);
			Element field = new Element("field" + Integer.toString(i));
			field.setAttribute(new Attribute("name", newObject.getClass().getName()));
			field.setAttribute(new Attribute("declaringclass","java.util.LinkedList"));
			field.addContent(new Element("reference").setText(newObject.toString()));
			linkedList.addContent(field);
			i++;
		}
		return linkedList;
	}
	
	
	
	
	
	public void objectTag() {
		int id = 0;
		do {
			if(!seen.empty()) {
				currentObject = seen.pop();
				currentClass = currentObject.getClass();
			}
			if(currentClass.isArray()) {
				Element object = fieldArrays(currentObject, id);
				doc.getRootElement().addContent(object);
			}
			else if(currentClass.getName().equals("java.util.LinkedList")) {
				/*LinkedList<Object> objectList = (LinkedList<Object>) currentObject;
				Object object1 = objectList.get(1);
				Object object2 = objectList.get(2);
				System.out.println(object1.getClass().getName());
				System.out.println(object2.getClass().getName());*/
				Element object = createLLElement(currentObject, id);
				doc.getRootElement().addContent(object);
			}
			else{
				Element object = new Element("object");
				object.setAttribute(new Attribute("class", currentClass.getName()));
				object.setAttribute(new Attribute("id", Integer.toString(id)));
		
				Field[] fields = currentClass.getDeclaredFields();
				for(int i = 0; i < fields.length; i++) {
					Element field = new Element("field" + Integer.toString(i));
					Field f = fields[i];
					f.setAccessible(true);
					field.setAttribute(new Attribute("name", f.getName()));
					field.setAttribute(new Attribute("declaringclass", f.getDeclaringClass().getName()));
					
					Class typeClass = f.getType();
					try {
						//handles primitives
						Object value = f.get(currentObject);
						if(typeClass.isPrimitive() || typeClass.equals(String.class)) {
							field.addContent(new Element("value").setText(value.toString()));
						}
						//handles arrays
						else if(value.getClass().isArray()) {
							seen.push(value);
						}
						//handles objects
						else {
							field.addContent(new Element("reference").setText(value.toString()));
							seen.push(value);
						}
						object.addContent(field);
					}
					catch (Exception e) {
						System.out.println("Error in printing value of field");
					}
					
				}
				
				doc.getRootElement().addContent(object);
			}
			id++;
		}while(!seen.isEmpty());
	}
	
	

	public org.jdom.Document serialize(Object obj) {
		System.out.println("===============================================\n"
				+ "Beginning to Serialize Object.....");
		currentObject = obj;
		currentClass = currentObject.getClass();
		try {
			//set first tag to be serialize
			Element serialize = new Element("serialize");
			doc = new Document(serialize);
			doc.setRootElement(serialize);
			
			//set second tag to be object and it's fields
			objectTag();		

			//output to file
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("output.xml"));

			System.out.println("File Saved!");
		
		}
		catch (Exception e) {
			System.out.println("Some error has occured when creating JDom");
		}
		return doc;
	}

}
