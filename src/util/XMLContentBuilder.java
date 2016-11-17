package util;

import castleescape.business.event.EventWord;

/**
 * Created by Alex on 27/10/2016.
 * Class handling the data received from {@link XMLParser}
 * Differentiates between the content builders extending the interface {@link IBuilder}
 */
class XMLContentBuilder {

	/**
	 * Enumeration of the builder types
	 */
	enum Type {
		ROOM, INSPECTABLEOBJECT, ITEM, CONFIGURATIONS
	}

	/**
	 * The type of object to be build
	 */
	private final Type type;

	/**
	 * The builder class, extending {@link IBuilder}
	 */
	private IBuilder build;

	XMLContentBuilder(Type type) {
		this.type = type;
	}

	/**
	 * Adds an element to the builder. This is a consumer of the {@link XMLParser.MetadataElement#DEFAULT} data type.
	 * @param element The name of the element.
	 * @param data The data within the element.
	 */
	void addElement(String element, String data) {
		switch (element) {
			case "name":
				if (type.equals(Type.ITEM)) {
					build = new XMLItemBuilder(data);
				} else if (type.equals(Type.INSPECTABLEOBJECT)) {
					build = new XMLInspectableObjectBulder(data);
				} else if (type.equals(Type.ROOM)) {
					build = new XMLRoomBuilder(data);
				}
				break;
			case "description":
				build.setDescription(data);
				break;
			case "":
				break;
			default:
				if (build == null&&type.equals(Type.CONFIGURATIONS)) {
					build=new XMLConfugurationsBuilder();
				}
				if (build instanceof XMLConfugurationsBuilder){
					((XMLConfugurationsBuilder) build).addConfiguration(element,data);
				}
		}
	}

	/**
	 * Adds an inventorry element to the builder. This is a consumer of the {@link XMLParser.MetadataElement#INVENTORY} data type.
	 * @param element The name of the element.
	 * @param data The data within the element.
	 */
	void addInventorryElement(String element, String data) {
		if (build instanceof XMLRoomBuilder) {
			if (element.equals("item")) {
				((XMLRoomBuilder) build).addItem(data);
			} else {
				((XMLRoomBuilder) build).addInspectableObject(data);
			}
		}
	}

	/**
	 * Adds an exit element to the builder. This is a consumer of the {@link XMLParser.MetadataElement#EXITS} data type.
	 * @param element The name of the element, which is the direction of the exit.
	 * @param data The data within the element.
	 */
	void addExitElement(String element, String data) {
		if (build instanceof XMLRoomBuilder) {
			((XMLRoomBuilder) build).addExit(element, data);
		}
	}

	/**
	 * Adds an event element to the builder. This is a consumer of the {@link XMLParser.MetadataElement#EVENTS} data type.
	 * @param element The name of the element.
	 * @param data The data within the element.
	 */
	void addEventElement(String element, String data) {
		if (build instanceof XMLItemBuilder) {
			switch (element) {
				case "trigger":
					((XMLItemBuilder) build).addEventTrigger(data);
					break;
				case "type":
					((XMLItemBuilder) build).addEventType(EventWord.getEventWord(data));
					break;
				default:
					((XMLItemBuilder) build).addEventParameter(element, data);
			}

		} else if (build instanceof XMLInspectableObjectBulder) {
			switch (element) {
				case "type":
					((XMLInspectableObjectBulder) build).addEventType(EventWord.getEventWord(data));
					break;
				default:
					((XMLInspectableObjectBulder) build).addEventParameter(element, data);
			}
		}
	}

	/**
	 * Builds an event. This method is called at the element end tag, to build the event, and make ready for a new event to be added.
	 */
	void buildEvent() { // calls buildEvent on the builder
		if (build instanceof XMLItemBuilder) {
			((XMLItemBuilder) build).buildEvent();
		} else if (build instanceof XMLInspectableObjectBulder) {
			((XMLInspectableObjectBulder) build).buildEvent();
		}
	}

	/**
	 * Builds the InspectableObject, Item or room.
	 * Called when no more data will be added.
	 */
	public void build() {
		build.build();
	}
}
