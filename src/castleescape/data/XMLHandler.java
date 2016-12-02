/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class used to handle .xml files and store their raw data in {@link IBuilder}
 * objects. This class is used in conjunction with a SAXParser to read xml
 * files.
 */
public class XMLHandler extends DefaultHandler {

	/**
	 * String describing a root element of the files that the XMLParser can
	 * read.
	 */
	private static final String ROOM = "room",
			INSPECTABLEOBJECT = "inspectableobject",
			ITEM = "item",
			CONFIGURATIONS = "configurations";

	/**
	 * Whether the root element has been read yet. The root element is special,
	 * as it is used to choose the type of IBuilder to process the data.
	 */
	private boolean rootIdentified;

	/**
	 * StringBuilder for accumulating characters from xml elements, as these
	 * characters are not always read all at once.
	 */
	private final StringBuilder contentAccumulator;

	/**
	 * Builder for processing the data read from the xml document.
	 */
	private IBuilder builder;

	/**
	 * Constructs a new parser for xml files.
	 */
	public XMLHandler() {
		contentAccumulator = new StringBuilder();
	}

	/*
	 * When beginning to parse a new document all parsing variables should be
	 * reset to prevent information carrying over from other parsed files.
	 */
	@Override
	public void startDocument() {
		//The root has not been identified at the start of a new xml document
		rootIdentified = false;

		//Clear all data in the stringbuilder
		contentAccumulator.setLength(0);

		//At the beginning of a new document no builder is known
		builder = null;
	}

	/*
	 * 
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//If the root element has not been read yet
		if (!rootIdentified) {
			rootIdentified = true;

			//Create a builder from the type of root element
			switch (qName) {
				case ROOM:
					//We are reading a room file
					builder = new RoomBuilder();
					break;
				case INSPECTABLEOBJECT:
					//We are reading an inspectable object file
					builder = new InspectableObjectBuilder();
					break;
				case ITEM:
					//We are reading an item file
					builder = new ItemBuilder();
					break;
				case CONFIGURATIONS:
					//We are reading a configuration file
					builder = new ConfigurationBuilder();
					break;
				default:
					//The root element was not recognized, so we throw an exception
					throw new SAXException("Unexpected root element <" + qName + ">");
			}
		} else {
			//The root has been identified, so we notify the builder of a new
			//element
			builder.notifyOfElement(qName);
		}

		//Clear content accumulator at every start element to prevent characters
		//from other elements to carry over
		contentAccumulator.setLength(0);
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		//Append characters onto the content accumulator
		contentAccumulator.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//Notify the builder that some data has been read. The builder is
		//responsible for validating the data
		builder.processElement(qName, contentAccumulator.toString());

		//Clear content accumulator at every end element to prevent characters
		//from other elements to carry over
		contentAccumulator.setLength(0);
	}

	@Override
	public void endDocument() throws SAXException {

	}

	/**
	 * Get the builder that was created while parsing the previous xml file.
	 *
	 * @return the builder that was created while parsing the previous xml file
	 */
	public IBuilder getBuilderResult() {
		return builder;
	}
}
