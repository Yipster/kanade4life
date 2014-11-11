/* Author: Brandon Yip
 * CPSC501 Assignment3
 * 
 * Driver class that will start the objectCreator.
 */

import java.util.*;

public class Driver {
	public static ObjectCreator objectCreator;
	
	public static void main(String[] args) {
		objectCreator = new ObjectCreator();
		objectCreator.start();
	
	}


}