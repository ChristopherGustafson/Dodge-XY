package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import Main.GameLogic;
import Main.GamePanel;

public class MenuState extends GameState{
	
	private final int START_GAME = 0;
	private final int INSTRUCTIONS = 1;
	private final int EXIT_GAME = 2;
	private int selected;
	
	static File titlePic = new File("Pictures/title.png");
	static Image title;
	
	Font textFont;
	
	//Upon creation the MenuState will load the neccessary font and image for the menu
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		try {
			title = ImageIO.read(titlePic);
			textFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/BebasNeue.ttf"))).deriveFont(Font.PLAIN, 40);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//Since the menu state isn't calculating anything or really keeping track of anything this method is not used

	public void updateGame() {
		
	}
	
	//The render method draws out the menu and the logo to the screen.

	public void renderGame(Graphics g) {
		
		g.setColor(new Color(51, 204, 255));
		g.fillRect(0, 0, 2000, 2000);
		
		g.setColor(Color.BLACK);
		g.drawImage(title, GamePanel.WIDTH*GamePanel.SCALE/2-400, 150, null);
		
		g.setColor(new Color(23, 28, 36));
		g.setFont(textFont);
		
		g.drawString("Start Game", GamePanel.WIDTH*GamePanel.SCALE/2-80, 400);
		g.drawString("Instructions", GamePanel.WIDTH*GamePanel.SCALE/2-80, 450);
		g.drawString("Exit Game", GamePanel.WIDTH*GamePanel.SCALE/2-80, 500);
		
		switch(selected){
		case START_GAME: 
			g.setColor(Color.WHITE);
			g.drawString("Start Game", GamePanel.WIDTH*GamePanel.SCALE/2-80+1, 400+1);
			break;
		case INSTRUCTIONS: 
			g.setColor(Color.WHITE);
			g.drawString("Instructions", GamePanel.WIDTH*GamePanel.SCALE/2-80+1, 450+1);
			break;
		case EXIT_GAME: 
			g.setColor(Color.WHITE);
			g.drawString("Exit Game", GamePanel.WIDTH*GamePanel.SCALE/2-80+1, 500+1);
			break;
		}
		
	}
	
	//Changes which alternative in the menu which is selected by checking which button has been pressed
	
	public void changeSelected(String direction){
		
		if(direction == "down"){
			if(selected == EXIT_GAME){
				selected = START_GAME;
			}else{
				selected++;
			}
		}
		else if(direction == "up"){
			
			if(selected == START_GAME){
				selected = EXIT_GAME;
			}else{
				selected--;
			}	
		}
	}
	
	//Enters the alternative which is currently selected if the player pressed enter
	
	public void chooseSelected(){
		
		if(selected == START_GAME){
			GameLogic.resetGameLogic();
			gsm.setCurrentState(gsm.PLAYSTATE);
		}
		else if(selected == INSTRUCTIONS){
			gsm.setCurrentState(gsm.INSTRUCTIONSSTATE);
		}
		else if(selected == EXIT_GAME){
			System.exit(0);
		}
	}

	//Calls the change selected method if the up or down key is pressed or choose selected if enter is pressed.
	
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
