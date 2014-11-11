
import java.lang.reflect.*;
import java.util.*;


public class Visualizer {
	public Class currentClass;
	public Object currentObject;
	public Stack<Object> seen = new Stack<Object>();
	
	public String[] inspectFields (Class currClass) {
		Field[] fieldList = currClass.getDeclaredFields();
		String[] fieldNames = new String[fieldList.length];
		System.out.println("The field(s) of this class: ");
		for (int i = 0; i < fieldList.length; i++) {
			Field f = fieldList[i];
			f.setAccessible(true);
			//get each field name
			int k = i + 1;
			String fieldName = f.getName();
			fieldNames[i] = fieldName;
			System.out.println(k + ") Name: " + fieldName);

			//get type of field of each field
			//get value of the field
			Class typeClass = f.getType();
			try {
				Object value = f.get(currentObject);
				//handles normal primitives
				if(typeClass.isPrimitive()) {
					System.out.println("   Type: " + typeClass.getName());
					System.out.print("   Value: " + value.toString());
				}
				else if (typeClass.equals(String.class)) {
					System.out.println("   Type:  String");
					System.out.print("   Value: " + value.toString());
				}
				//handles arrays
				else if(value.getClass().isArray()) {
					fieldArrays(value);
				}
				//handles objects
				else {
					System.out.println("   Type: " + typeClass.getName());
					System.out.print("   Pointer Value: " + value.toString());
					//System.out.print("   Pointer Value: " + System.identityHashCode(value));
					seen.push(value);
				}
				System.out.println();
			}
			catch (Exception e) {
				System.out.println("Error in printing value of field");
			}
			
			//test modifiers of each field
			int mod = fieldList[i].getModifiers();
			testModifiers(mod);
			System.out.println();
		}
		return fieldNames;
	}
	
	
	
	
	//handles printing of the array
		public void fieldArrays(Object object) {
			if (object.getClass().isArray()) {
				Class<?> componentType;
				componentType = object.getClass().getComponentType();
				if (componentType.isPrimitive()) {
					if (boolean.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: boolean[]");
						System.out.print("   Value: [");
						for (boolean anElement : (boolean[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}
					
					else if (byte.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: byte[]");
						System.out.print("   Value: [");
						for(byte anElement : (byte[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}

					else if (char.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: char[]");
						System.out.print("   Value: [");
						for(char anElement : (char[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}

					else if (double.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: double[]");
						System.out.print("   Value: [");
						for(double anElement : (double[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}

					else if (float.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: float[]");
						System.out.print("   Value: [");
						for(float anElement : (float[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}

					else if (int.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: int[]");
						System.out.print("   Value: [");
						for (int anElement : (int[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}

					else if (long.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: long[]");
						System.out.print("   Value: [");
						for (long anElement : (long[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}

					else if (short.class.isAssignableFrom(componentType)) {
						System.out.println("   Type: short[]");
						System.out.print("   Value: [");
						for (short anElement : (short[]) object) {
							System.out.print(anElement + ", ");
						}
						System.out.print("]");
					}
				}

				else {
					//handle Object[]					
					System.out.println("   Type: " + componentType.getName());
					System.out.print("   Value: [");
					for(Object anElement : (Object[]) object) {
						System.out.print(System.identityHashCode(anElement) + ", ");		
					}
					System.out.print("]");
				}
			}
		}
	
	
	
	
	
	
	//method that checks each modifier and if true, will print
	public void testModifiers(int mod) {
		System.out.print("   Modifiers: ");
		if(Modifier.isAbstract(mod))
			System.out.print("Abstract ");
		if(Modifier.isFinal(mod))
			System.out.print("Final ");
		if(Modifier.isInterface(mod))
			System.out.print("Interface ");
		if(Modifier.isNative(mod))
			System.out.print("Native ");
		if(Modifier.isPrivate(mod))
			System.out.print("Private ");
		if(Modifier.isProtected(mod))
			System.out.print("Protected ");
		if(Modifier.isPublic(mod))
			System.out.print("Public ");
		if(Modifier.isStatic(mod))
			System.out.print("Static ");
		if(Modifier.isStrict(mod))
			System.out.print("Strict ");
		if(Modifier.isSynchronized(mod))
			System.out.print("Synchronized ");
		if(Modifier.isTransient(mod))
			System.out.print("Transient ");
		if(Modifier.isVolatile(mod))
			System.out.print("Volatile ");
		System.out.println();
	}
	
	
	
	
	
	public void inspect(Object obj) {
		currentObject = obj;
		currentClass = obj.getClass();
		System.out.println("Class Name: " + currentClass.getName());
		
		inspectFields(currentClass);
		
		if(!seen.empty()) {
			inspect(seen.pop());
		}
	}
	
}