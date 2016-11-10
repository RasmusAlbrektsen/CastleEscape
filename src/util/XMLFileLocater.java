package util;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Created by Alex on 27/10/2016.
 * Finds all Xml files in a directory and parses them with the {@link XMLParser}
 * Will first parse inspectableObjects, then Items, then rooms.
 * Calls {@link XMLRoomExitBuilder#build()} when all rooms has been initialised.
 *
 */
public class XMLFileLocater implements FileVisitor<Path> {
	/**
	 * All files in items directory
	 */
	private final ArrayList<String> itemFiles = new ArrayList<>();
	/**
	 * All files in inspectableObjects directory
	 */
	private final ArrayList<String> inspectableObjectFiles = new ArrayList<>();
	/**
	 * All files in rooms directory
	 */
	private final ArrayList<String> roomFiles = new ArrayList<>();
	private String filetype;
	private final String ITEM = "item", ROOM = "room", INSPECTABLE_OBJECT = "insp";

	public XMLFileLocater(Path xmlFolder) {
		try {
			Files.walkFileTree(xmlFolder, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(new XMLParser());
			//Parse all inspectable Objects
			for (String inspectableObjectFile : inspectableObjectFiles) {
				System.out.println(inspectableObjectFile);
				xmlReader.parse(inspectableObjectFile);
			}
			//Parse all items
			for (String itemFile : itemFiles) {
				System.out.println(itemFile);
				xmlReader.parse(itemFile);
			}
			//Parse all rooms
			for (String roomFile : roomFiles) {
				System.out.println(roomFile);
				xmlReader.parse(roomFile);
			}
			//Builds exits, after all rooms has been initialised.
			XMLRoomExitBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a file to the correct list of files
	 * @param path full the path of the file
	 */
	private void addfile(String path) {
		switch (filetype) {
			case ITEM:
				itemFiles.add(path);
				break;
			case INSPECTABLE_OBJECT:
				inspectableObjectFiles.add(path);
				break;
			case ROOM:
				roomFiles.add(path);
				break;
		}
	}

	/**
	 * Called before a directory is opened.
	 * Updates the file type to be expected based on the name of the directory.
	 * @param dir Path object describing the directory.
	 * @param attrs Attributes of the directory.
	 *
	 * @see FileVisitor
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		if (dir.getFileName().toString().equalsIgnoreCase("items")) {
			filetype = ITEM;
		} else if (dir.getFileName().toString().equalsIgnoreCase("rooms")) {
			filetype = ROOM;
		} else if (dir.getFileName().toString().equalsIgnoreCase("inspectableobjects")) {
			filetype = INSPECTABLE_OBJECT;
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Called once for every file
	 * adds the file to the list, if the file is an xml file.
	 * @param file visited file.
	 * @param attrs the file attributes.
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (file.getFileName().toString().endsWith("xml")) {
			addfile(file.toAbsolutePath().toString());
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Called upon failure to visit a file. This can occur, if the application is missing permissions to view them.
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.TERMINATE;
	}

	/**
	 * Called after visiting every file and directory in a directory.
	 */
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
}
