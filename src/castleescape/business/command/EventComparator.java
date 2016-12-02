/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.event.Event;
import java.util.Comparator;

/**
 * A comparator for sorting Event objects. This class is located in the command
 * package as it is only used by classes in this package.
 *
 * @author Kasper
 */
public class EventComparator implements Comparator<Event> {

	/**
	 * Compare the two events and return an integer depending on the order in
	 * which the two events should be sorted.
	 *
	 * @param e1 the first event to compare
	 * @param e2 the second event to compare
	 */
	@Override
	public int compare(Event e1, Event e2) {
		//This will return -1 if e1 is greater than e2, 0 if they are equal and
		//1 if e1 is smaller than e2. This results in the events being ordered
		//in descending order.
		//
		//Example:
		//Returning -1 means that e1 should come before e2. In this case, e1 is
		//greater than e2 (see above), and thus the order is descending.
		return (int) Math.signum(e2.getEventWord().getWeight() - e1.getEventWord().getWeight());
	}
}
