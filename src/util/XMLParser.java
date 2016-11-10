package util;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Arrays;


/**
 * Created by Alex on 26/10/2016.
 * Class concerned with parsing the XML files.
 * Passes on data to an {@link XMLContentBuilder XMLContntBuilder}
 */
class XMLParser extends DefaultHandler {

	private boolean rootIdentified = false;
	private MetadataElement metadataElement = MetadataElement.DEFAULT;
	private String currentElement;
	private XMLContentBuilder builder;
	private String content = "";

	/**
	 * Enumeration of data type. Only name and description should be of the default data type.
	 */
	enum MetadataElement {
		EVENTS, INVENTORY, EXITS, DEFAULT
	}

	/**
	 * Receive notification of the beginning of an element.
	 * Initialises the builder from the root element.
	 * Updates the value of currentElement.
	 * Updates the value of metadataElement, when a metadataElement is reached.
	 *
	 *
	 * @exception InvalidXMLException Thrown, when an incorrectly written XML files would lead to ambiguity or an incorrectly initialized object.
	 * @see org.xml.sax.ContentHandler#startElement
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!rootIdentified) {
			//If root hasn't been identified:
			try {
				builder = new XMLContentBuilder(XMLContentBuilder.Type.valueOf(qName.toUpperCase()));//Initialize the builder from the root element.
			} catch (IllegalArgumentException e) {
				//In case the root element wasn't item, room or inspectableobject:
				System.out.println("Parser error: \"" + qName + "\" is an incorrect root element.\n (inspectableobject, room or item)");
				throw new InvalidXMLException("Incorrect root element");
			}
			rootIdentified = true;
			return;
		}
		currentElement = qName;
		try {
			metadataElement = MetadataElement.valueOf(qName.toUpperCase());//try to cast the name of this element to a metadata element.
		} catch (IllegalArgumentException ignored) {
		}//The name of the element was not a metadata element.

		//Cases of incorrectly written XML:

		//Event tag outside an events block:
		if (qName.equalsIgnoreCase("event") && metadataElement != MetadataElement.EVENTS) {
			System.out.println("Parser error: \"<events>\" tag expected");
			throw new InvalidXMLException("Events tag missing");

			//An exit outside an exits block:
		} else if (metadataElement != MetadataElement.EXITS && (qName.equalsIgnoreCase("north") || qName.equalsIgnoreCase("south") || qName.equalsIgnoreCase("east") || qName.equalsIgnoreCase("west"))) {
			System.out.println("parse error: \"<exits>\" tag expected");
			throw new InvalidXMLException("Exits tag missing");
		}
	}

	/**
	 * Notification of characters. Assuming the characters are not all whitespaces, creates a string and adds it to the end of content
	 * @param ch the Characters received
	 * @param start the start index in the array.
	 * @param length the length of the character string.
	 *
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (hasContent(Arrays.copyOfRange(ch, start, start + length))) {
			content += new String(Arrays.copyOfRange(ch, start, start + length));
		}
	}

	/**
	 * Adds data to the content builder.
	 * The content added is dependent on the value of the value of metadataElement.
	 */
	private void addContentToBuilder() {
		if (content.isEmpty()) return;
		switch (metadataElement) {
			case DEFAULT:
				builder.addElement(currentElement, content);
				break;
			case EXITS:
				builder.addExitElement(currentElement, content);
				break;
			case INVENTORY:
				builder.addInventorryElement(currentElement, content);
				break;
			case EVENTS:
				builder.addEventElement(currentElement, content);
		}
		content = "";
	}

	/**
	 * Returns false if the character array contains only whitespaces.
	 * @param ch the character array to perform the check on.
	 * @return false if the array only contains whitespaces.
	 */
	private boolean hasContent(char[] ch) {
		for (char c : ch) {
			if (c != ' ' && c != '\n' && c != '\t') return true;
		}
		return false;
	}

	/**
	 * Notification of the end of an element.
	 * Updates the value of metadataElement.
	 * Sends a notification to the builder, that an event block has ended if this is the case.
	 *
	 * @see ContentHandler#endElement
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		addContentToBuilder();
		if (qName.equals("event")) {
			builder.buildEvent();
			return;
		}
		if (metadataElement.name().equals(qName)) {
			metadataElement = MetadataElement.DEFAULT;
		}
	}

	/**
	 * Notification of the end of the file.
	 * Builds the element and resets the parser for the next file.
	 *
	 * @see ContentHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		rootIdentified = false;
		metadataElement = MetadataElement.DEFAULT;
		builder.build();
		super.endDocument();
	}
}
