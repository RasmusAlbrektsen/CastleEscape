/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business;

/**
 * The ViewUtil is designed to transform simple strings into html markup before
 * they are sent to the user interface.
 * <p>
 * Currently PrintUtil supports printing of strings, chars, integers and
 * doubles.
 *
 * @author Kasper
 */
public class ViewUtil {

	/**
	 * The name of the line separator system property.
	 */
	private static final String LINE_SEPARATOR_PROPERTY = "line.separator";

	/**
	 * The stringbuilder for accumulating characters to print to the user
	 * interface.
	 */
	private static final StringBuilder string = new StringBuilder();
	
	/**
	 * Whether we are in the process of printing a line already. If this is
	 * false, then a call to print(String) should append an open paragraph
	 * element before the string.
	 */
	private static boolean isPrintingLine = false;

	/**
	 * Print the specified string to the user interface.
	 *
	 * @param s the string to print
	 */
	public static void print(String s) {
		if (! isPrintingLine) {
			string.append("<p>");
			isPrintingLine = true;
		}
		
		string.append(s);
	}

	/**
	 * Print the specified char to the user interface.
	 *
	 * @param c the char to print
	 */
	public static void print(char c) {
		print(String.valueOf(c));
	}

	/**
	 * Print the specified int to the user interface.
	 *
	 * @param i the int to print
	 */
	public static void print(int i) {
		print(String.valueOf(i));
	}

	/**
	 * Print the specified double to the user interface.
	 *
	 * @param d the double to print
	 */
	public static void print(double d) {
		print(String.valueOf(d));
	}

	/**
	 * Print the specified object to the user interface. If the object is null
	 * this will print the string "null", otherwise it will print the value of
	 * o.toString().
	 *
	 * @param o the object to print
	 */
	public static void print(Object o) {
		print(o == null ? "null" : o.toString());
	}

	/**
	 * Print the specified string followed by a newline to the user interface.
	 *
	 * @param s the string to print
	 */
	public static void println(String s) {
		print(s);
		newLine();
	}

	/**
	 * Print the specified char followed by a newline to the user interface.
	 *
	 * @param c the char to print
	 */
	public static void println(char c) {
		print(c);
		newLine();
	}

	/**
	 * Print the specified int followed by a newline to the user interface.
	 *
	 * @param i the int to print
	 */
	public static void println(int i) {
		print(i);
		newLine();
	}

	/**
	 * Print the specified double followed by a newline to the user interface.
	 *
	 * @param d the double to print
	 */
	public static void println(double d) {
		print(d);
		newLine();
	}

	/**
	 * Print the specified object followed by a newline to the user interface.
	 * If the object is null this will print the string "null", otherwise it
	 * will print the value of o.toString().
	 *
	 * @param o the object to print
	 */
	public static void println(Object o) {
		print(o);
		newLine();
	}

	/**
	 * Print a newline to the user interface.
	 */
	public static void newLine() {
		string.append("</p>");
		isPrintingLine = false;
	}
	
	public static String getString() {
		//Save the contents of the stringbuilder in a temporary variable so that
		//it can be reset before returning the string
		String s = string.toString();
		string.setLength(0);
		
		return s;
	}
}
