/*
 * The abstract class of all the game states. 
 */

package GameState;

import java.awt.Graphics;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public abstract void updateGame();
	public abstract void renderGame(Graphics g);
	public abstract void setKeyState(int key, boolean state);
	
}
