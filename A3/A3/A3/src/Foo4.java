/*
Author: Brandon Yip
CPSC501 Assignment3
 A class that only contains 1 object array of size 2 with setters for both indexes
 4th type of object you can make in ObjectCreator
*/
public class Foo4 {

	public Object[] fooList;
	
	public Foo4() {
		fooList = new Object[2];
	}
	
	public void setIndex0(Object object) {
		fooList[0] = object;
	}
	
	public void setIndex1(Object object) {
		fooList[1] = object;
	}
	
}
