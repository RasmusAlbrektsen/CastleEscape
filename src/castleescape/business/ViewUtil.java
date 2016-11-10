package castleescape.business;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Currently PrintUtil supports printing of strings, chars, integers and
 * doubles.
 * <p>
 * This class is designed to make use of the mediator pattern to communicate
 * between the game implementation and the user interface. Instead of adding a
 * deep dependency on the user interface inside the game logic, all user
 * interface related actions go through this class, which will forward these
 * actions to the UI in a way defined by the implementation. This means that
 * changing the user interface of this game is merely a matter of adapting this
 * single class.
 *
 * @author Kasper
 */
public class ViewUtil {

	/**
	 * The name of the line separator system property.
	 */
	private static final String LINE_SEPARATOR_PROPERTY = "line.separator";

	/**
	 * Print the specified string to the user interface.
	 *
	 * @param s the string to print
	 */
	public static void print(String s) {
		//TODO: Forward to user interface during phase 2
		System.out.print(s);
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
		print(System.getProperty(LINE_SEPARATOR_PROPERTY));
	}
}
