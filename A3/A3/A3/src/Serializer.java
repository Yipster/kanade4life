
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
	public Class currentClass;
	public Foo1 foo1;
	public Stack<Object> seen = new Stack<Object>();
	public ArrayList<Class> visited = new ArrayList<Class>();
	
	public void objectTag() {
		int id = 0;
		do {
			if(!seen.empty()) {
				currentObject = seen.pop();
				currentClass = currentObject.getClass();
			}
			if(currentClass.isArray()) {
				Element object = new Element("object");
				object.setAttribute(new Attribute("class", currentClass.getName()));
				object.setAttribute(new Attribute("id", Integer.toString(id)));
				
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
		System.out.println("Beginning to Serialize Object.....");
		currentObject = obj;
		currentClass = currentObject.getClass();
		try {
			//set first tag to be serialize
			Element serialize = new Element("serialize");
			doc = new Document(serialize);
			doc.setRootElement(serialize);
			
			//set second tag to be object
			objectTag();

			//set third tag to be fields
			


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
