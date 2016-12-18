/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.ViewUtil;

/**
 * An event executer for making noise and attracting the monster.
 */
public class MakeNoiseEventExecuter implements EventExecuter {

	/**
	 * Make noise as specified by the event. The player character's clumsiness
	 * is also taken into account.
	 */
	@Override
	public void execute(Game game, Event event) {
		//Print the description, if one is present
		String description = event.getEventParam(Event.DESCRIPTION);
		if (description != null) {
			ViewUtil.println(description);
		}

		//Get the string version of the chance of generating noise on this event
		String weightString = event.getEventParam(Event.WEIGHT);

		//Parse this string representation to a double value. We assume no
		//errors as events are written by ourselves, and so we do not have to
		//account for illegal usage
		double weight = Double.parseDouble(weightString);

		//Get the player character's clumsiness
		double playerWeight = game.getPlayer().getClumsiness();

		//Calculate the chance of generating noise
		double noiseChance = weight * playerWeight;

		//Roll a random number in the range [0;1[ to determine, if noise should
		//be generated
		double random = Math.random();

		if (random < noiseChance) {
			//Noise was generated, make the monster hunt the player
			game.getMonster().setHunting(game.getCurrentRoom());
			ViewUtil.println("You fool! You make too much noise.");
		}
	}

}
