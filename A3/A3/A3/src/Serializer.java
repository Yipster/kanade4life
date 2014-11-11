
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.io.FileWriter;
import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Serializer {

	public Document doc;
	public Object currentObject;
	public Foo1 foo1;
	public Stack<Object> seen = new Stack<Object>();
	public ArrayList<Class> visited = new ArrayList<Class>();
	
	public void objectTag(Object obj) {
		Class currentClass;
		int id = 0;
		seen.push(obj);
		while(!seen.isEmpty()) {
			Object newObject = seen.pop();
			currentClass = newObject.getClass();
			if(currentClass.isArray()) {
				Element object = new Element("object");
				object.setAttribute(new Attribute("class", currentClass.getName()));
				object.setAttribute(new Attribute("id", Integer.toString(id)));
				Class<?> componentType;
				componentType = newObject.getClass().getComponentType();
				if(componentType.isPrimitive()) {
					
				}
				doc.getRootElement().addContent(object);
			}
			
			else{
				
				Element object = new Element("object");
				object.setAttribute(new Attribute("class", currentClass.getName()));
				object.setAttribute(new Attribute("id", Integer.toString(id)));
				/*
				if(currentClass.isArray())
					object.setAttribute(new Attribute("length"));
				 */
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
							field.addContent(new Element("reference").setText(value.toString()));
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
		}
	}
	
	

	public org.jdom.Document serialize(Object obj) {
		System.out.println("Beginning to Serialize Object.....");
		try {
			//set first tag to be serialize
			Element serialize = new Element("serialize");
			doc = new Document(serialize);
			doc.setRootElement(serialize);
			
			//calling function to set object tag and field tags.
			objectTag(obj);


			


			//output to file
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("awesome.xml"));

			System.out.println("File Saved!");
		
		}
		catch (Exception e) {
			System.out.println("Some error has occured when creating JDom");
		}
		return doc;
	}

}
