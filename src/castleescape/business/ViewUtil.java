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
	 * An HTML element.
	 */
	private static final String LINE_BREAK_ELEMENT = "<br/>",
			PARAGRAPH_START_ELEMENT = "<p>",
			PARAGRAPH_END_ELEMENT = "</p>",
			SHAKY_SPAN_ELEMENT_START = "<span class=\"shakyText\">",
			SPAN_ELEMENT_END = "</span>";

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
		if (!isPrintingLine) {
			string.append(PARAGRAPH_START_ELEMENT);
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
		if (! isPrintingLine) {
			string.append(PARAGRAPH_START_ELEMENT);
		}
		
		string.append(PARAGRAPH_END_ELEMENT);
		isPrintingLine = false;
	}

	/**
	 * Add a line break to the currently buffered text. A line break will be
	 * smaller than a newline.
	 */
	public static void lineBreak() {
		string.append(LINE_BREAK_ELEMENT);
	}

	/**
	 * Print the specified string so that it will be shaking in the user
	 * interface. The user interface is free to define the specifics of this
	 * effect, at whether it wants to support it at all.
	 *
	 * @param s the string to print
	 */
	public static void printShaky(String s) {
		string.append(SHAKY_SPAN_ELEMENT_START)
				.append(s)
				.append(SPAN_ELEMENT_END);
	}

	/**
	 * Get the string constructed by this class so far. This string will be
	 * formatted in HTML. After this method returns the currently buffered
	 * string will be reset.
	 *
	 * @return the string constructed by this class so far
	 */
	public static String getString() {
		//Save the contents of the stringbuilder in a temporary variable so that
		//it can be reset before returning the string
		String s = string.toString();
		string.setLength(0);

		return s;
	}
}
