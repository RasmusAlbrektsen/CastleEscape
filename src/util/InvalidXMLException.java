package util;

/**
 * Created by Alex on 31/10/2016.
 * Exception thrown when an error can lead to ambiguity or improper instantiated content.
 */
public class InvalidXMLException extends RuntimeException {
	public InvalidXMLException() {
		super();
	}

	public InvalidXMLException(String s) {
		super(s);
	}
}
