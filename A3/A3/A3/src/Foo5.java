/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that uses an instance of one of Java's collection classes to refer to several other objects.
 5th type of object you can make in ObjectCreator
*/

import java.util.LinkedList;

public class Foo5 {
	public LinkedList<Object> objectList;
	
	public Foo5() {
		objectList = new LinkedList<Object>();
	}
	
}
