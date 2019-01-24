package GameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Main.GameLogic;
import Main.GameRender;

public class PlayState extends GameState{
	
	static boolean keyUp = false;
	static boolean keyDown = false;
	static boolean keyRight = false;
	static boolean keyLeft = false;
	
	boolean restart = false;
	
	//Upon creating the playstate will reset the variables of the gamelogic and load all the resources for the game
	
	public PlayState(GameStateManager gsm){
		this.gsm = gsm;
		
		GameLogic.resetGameLogic();
		GameRender.loadResources();
	}
	
	//Every tick the following methods is being called which makes the game work. If the player loses the gameover variable will be true
	//and the game enters the gameover state.
	
	public void updateGame() {
		GameLogic.moveCharacter();
		GameLogic.bombCounter();
		GameLogic.moveBombs();
		GameLogic.checkCollision();
		GameLogic.updateScore();
		
		if(GameLogic.gameOver == true){
			gsm.setCurrentState(gsm.GAMEOVERSTATE);
		}
	}
	
	//Draws the necessary components of the game to the screen

	public void renderGame(Graphics g) {
		GameRender.drawGameBoard(g);
		GameRender.drawPlayer(g);
		GameRender.drawBombs(g);
		GameRender.drawScore(g);
	}
	
	//If the player presses the escape button the paus state will be entered

	public void setKeyState(int key, boolean state) {
		GameLogic.setKeyState(key, state);
		if(key == KeyEvent.VK_ESCAPE && state){
			gsm.setCurrentState(gsm.PAUSSTATE);
		}
	}

}
