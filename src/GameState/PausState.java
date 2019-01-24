/*
 * The paus state works the same as the menu state, see the comments in the menu state
 */

package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;

import Main.GameLogic;
import Main.GamePanel;
import Main.GameRender;

public class PausState extends GameState{
	
	private final int RESUME_GAME = 0;
	private final int RESTART_GAME = 1;
	private final int EXIT_TO_MENU = 2;
	private int selected;
	
	private Font textFont;
	
	public PausState(GameStateManager gsm){
		this.gsm = gsm;
		
		try {
			textFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/BebasNeue.ttf"))).deriveFont(Font.PLAIN, 40);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void updateGame() {
		
	}

	public void renderGame(Graphics g) {
		GameRender.drawGameBoard(g);
		GameRender.drawPlayer(g);
		GameRender.drawBombs(g);
		GameRender.drawScore(g);
		
		g.setColor(new Color(0, 0, 0, 80));
		g.fillRect(0, 0, 2000, 2000);
		
		g.setColor(new Color(23, 28, 36));
		g.setFont(textFont);
		g.drawString("Paused", GamePanel.WIDTH*GamePanel.SCALE/2-45, 300);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Resume Game", GamePanel.WIDTH*GamePanel.SCALE/2-60, 340);
		
		g.drawString("Restart Game", GamePanel.WIDTH*GamePanel.SCALE/2-60, 370);
		
		g.drawString("Exit to Menu", GamePanel.WIDTH*GamePanel.SCALE/2-60, 400);
		
		switch(selected){
		case RESUME_GAME: 
			g.setColor(Color.WHITE);
			g.drawString("Resume Game", GamePanel.WIDTH*GamePanel.SCALE/2-60, 340);
			break;
		case RESTART_GAME: 
			g.setColor(Color.WHITE);
			g.drawString("Restart Game", GamePanel.WIDTH*GamePanel.SCALE/2-60, 370);
			break;
		case EXIT_TO_MENU: 
			g.setColor(Color.WHITE);
			g.drawString("Exit to Menu", GamePanel.WIDTH*GamePanel.SCALE/2-60, 400);
			break;
		}
	}
	
	public void changeSelected(String direction){
		
		if(direction == "down"){
			if(selected == EXIT_TO_MENU){
				selected = RESUME_GAME;
			}else{
				selected++;
			}
		}
		else if(direction == "up"){
			
			if(selected == RESUME_GAME){
				selected = EXIT_TO_MENU;
			}else{
				selected--;
			}
			
		}
	}
	
	public void chooseSelected(){
		
		if(selected == RESUME_GAME){
			gsm.setCurrentState(gsm.PLAYSTATE);
		}
		else if(selected == RESTART_GAME){
			GameLogic.resetGameLogic();
			gsm.setCurrentState(gsm.PLAYSTATE);
		}
		else if(selected == EXIT_TO_MENU){
			gsm.setCurrentState(gsm.MENUSTATE);
		}
	}

	public void setKeyState(int key, boolean state) {
		
		if(key == KeyEvent.VK_DOWN && state == true){
			changeSelected("down");
		}
		else if(key == KeyEvent.VK_UP && state == true){
			changeSelected("up");
		}
		else if(key == KeyEvent.VK_ENTER && state == true){
			chooseSelected();
		}
	}

}
