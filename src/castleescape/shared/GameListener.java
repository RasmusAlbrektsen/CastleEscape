/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.shared;

/**
 *
 * @author Christian Schou
 */
public interface GameListener {
	
	void onGameStart();
	
	void onGameExit();
	
	void onGameIteration(String output);
	
}
