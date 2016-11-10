package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.InspectableObjectRegister;

/**
 * Created by Alex on 01/11/2016.
 * Add an event executer for changing the description of an object
 */
public class SetObjectDescriptionExecuter implements EventExecuter {
	@Override
	public void execute(Game game, Event event) {
		//get the item or object from the event.
		InspectableObject object = InspectableObjectRegister.getAsInspectableObject(event.getEventParam(Event.OBJECT));
		if (object != null) {
			object.setDescription(event.getEventParam(Event.DESCRIPTION));
		}
	}
}
