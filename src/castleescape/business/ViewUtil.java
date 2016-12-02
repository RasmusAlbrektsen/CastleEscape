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
 */
public class ViewUtil {

	/**
	 * An HTML element.
	 */
	private static final String LINE_BREAK_ELEMENT = "<br/>",			//Used to make a line break
			PARAGRAPH_START_ELEMENT = "<p>",							//Used around paragraphs of text
			PARAGRAPH_END_ELEMENT = "</p>",
			SHAKY_SPAN_ELEMENT_START = "<span class=\"shakyText\">",	//Used to make text shake, controlled by UI
			BOLD_SPAN_ELEMENT_START = "<span class=\"boldText\">",		//Used to make the text bold or otherwise larger, controlled by UI
			SPAN_ELEMENT_END = "</span>";								//Used to end a span element

	/**
	 * The stringbuilder for accumulating characters to print to the user
	 * interface.
	 */
	private static final StringBuilder string = new StringBuilder();

	/**
	 * Whether we are in the process of printing some text already. If this is
	 * false, then a call to {@link #print(java.lang.String)} should append an
	 * open paragraph element before the string, and {@link #getString()} should
	 * return an empty string, to indicate that nothing has been written.
	 */
	private static boolean isPrintingText = false;

	/**
	 * Print the specified string to the user interface.
	 *
	 * @param s the string to print
	 */
	public static void print(String s) {
		//If not already in a paragraph element, make one
		if (!isPrintingText) {
			string.append(PARAGRAPH_START_ELEMENT);
			isPrintingText = true;
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
		//The ternary operator reads: if (o == null) return "null"; else return o.toString();
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
		print(LINE_BREAK_ELEMENT);
	}

	/**
	 * Print i newlines to the user interface.
	 *
	 * @param i the amount of newlines t print
	 */
	public static void newLine(int i) {
		//Call print() i times using a for loop
		for (int j = 0; j < i; j++) {
			print(LINE_BREAK_ELEMENT);
		}
	}

	/**
	 * Print the specified string so that it will be shaking in the user
	 * interface. The user interface is free to define the specifics of this
	 * effect, and whether it wants to support it at all.
	 *
	 * @param s the string to print
	 */
	public static void printShaky(String s) {
		print(SHAKY_SPAN_ELEMENT_START);
		print(s);
		print(SPAN_ELEMENT_END);
	}

	/**
	 * Print the specified string so that it will be bold in the user interface.
	 * The user interface is free to define the specifics of this effect, and
	 * whether it wants to support it at all.
	 *
	 * @param s the string to print
	 */
	public static void printBold(String s) {
		print(BOLD_SPAN_ELEMENT_START);
		print(s);
		print(SPAN_ELEMENT_END);
	}

	/**
	 * Get the string constructed by this class so far. This string will be
	 * formatted in HTML. After this method returns, the currently buffered
	 * string will be reset.
	 *
	 * @return the string constructed by this class so far
	 */
	public static String getString() {
		//If we haven't written anything yet, we just return an empty string
		if (!isPrintingText) {
			return "";
		}

		//Otherwise we need to close the paragraph currently written before
		//returning it
		string.append(PARAGRAPH_END_ELEMENT);

		//Save the contents of the stringbuilder in a temporary variable so that
		//it can be reset before returning the string
		String s = string.toString();
		string.setLength(0);

		return s;
	}
}
