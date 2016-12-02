package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.object.InspectableObject;

/**
 * Event executer for changing the description of an object.
 */
public class SetObjectDescriptionEventExecuter implements EventExecuter {

	@Override
	public void execute(Game game, Event event) {
		//Get the item or object from the event
		InspectableObject object = game.getInspectableObjectRegister().getAsInspectableObject(event.getEventParam(Event.OBJECT));

		//if the object exists, set its description, otherwise do nothing
		if (object != null) {
			object.setDescription(event.getEventParam(Event.DESCRIPTION));
		}
	}
}
