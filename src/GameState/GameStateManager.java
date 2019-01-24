/*
 * The Game State Manager controls all the different states in the game. Every tick the which is being made trough the GamePanel class
 * calls the three last methods in the class which is then calls these methods in the current game state. The class uses an index for each
 * of the different states to easily be able to switch between the states. When creating a Game State Manager object all the different
 * gamestates will be created and added to an list of gamestates.
 */


package GameState;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameStateManager {
	
	public ArrayList<GameState> gameStates;
	
	protected final int MENUSTATE = 0;
	protected final int PLAYSTATE = 1;
	protected final int GAMEOVERSTATE = 2;
	protected final int PAUSSTATE = 3;
	protected final int INSTRUCTIONSSTATE = 4;
	
	protected int currentState;
	
	public GameStateManager(){
		
		gameStates = new ArrayList<GameState>();
		
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new PausState(this));
		gameStates.add(new InstructionsState(this));
		
		currentState = MENUSTATE;
	}
	
	public void setCurrentState(int GameState){
		currentState = GameState;
	}
	
	public void updateGame(){
		gameStates.get(currentState).updateGame();
	}
	
	public void renderGame(Graphics g){
		gameStates.get(currentState).renderGame(g);
	}
	
	public void setKeyState(int key, boolean state){
		gameStates.get(currentState).setKeyState(key, state);
	}

}
