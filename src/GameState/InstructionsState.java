/*
 * The instructions state works the same as the menu state, see the comments in the menu state
 */

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

public class InstructionsState extends GameState{
	
	private final int START_GAME = 0;
	private final int BACK_TO_MENU = 1;
	
	private int selected;
	
	static File titlePic = new File("Pictures/title.png");
	static Image title;
	
	static File leftArrowPic = new File("Pictures/arrowleft.png");
	static Image leftArrow;
	
	static File rightArrowPic = new File("Pictures/arrowright.png");
	static Image rightArrow;
	
	static File upArrowPic = new File("Pictures/arrowup.png");
	static Image upArrow;
	
	static File downArrowPic = new File("Pictures/arrowdown.png");
	static Image downArrow;
	
	Font textFont;
	Font smallTextFont;
	
	public InstructionsState(GameStateManager gsm){
		this.gsm = gsm;
		
		try {
			title = ImageIO.read(titlePic);
			leftArrow = ImageIO.read(leftArrowPic);
			rightArrow = ImageIO.read(rightArrowPic);
			upArrow = ImageIO.read(upArrowPic);
			downArrow = ImageIO.read(downArrowPic);
			textFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/BebasNeue.ttf"))).deriveFont(Font.PLAIN, 40);
			smallTextFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/BebasNeue.ttf"))).deriveFont(Font.PLAIN, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public void updateGame() {
		
	}

	public void renderGame(Graphics g) {
		
		g.setColor(new Color(51, 204, 255));
		g.fillRect(0, 0, 2000, 2000);
		
		g.setColor(Color.BLACK);
		g.drawImage(title, GamePanel.WIDTH*GamePanel.SCALE/2-400, 50, null);
		
		g.setColor(new Color(23, 28, 36));
		g.setFont(textFont);
		
		g.drawString("Try to avoid the bombs and survive as long as possible", GamePanel.WIDTH*GamePanel.SCALE/2-360, 300);
		
		g.setFont(smallTextFont);
		
		g.drawImage(leftArrow, 200, 370, null);
		g.drawImage(rightArrow, 230, 370, null);
		g.drawString(" - Move sideways", 260, 395);
		
		g.drawImage(upArrow, 480, 370, null);
		g.drawString(" - Jump", 510, 395);
		
		g.drawImage(downArrow, 640, 370, null);
		g.drawString(" - Duck", 670, 395);
		
		g.drawString("ESC   -   Pause ", 800, 395);
		
		g.setFont(textFont);
		
		g.drawString("Start Game", GamePanel.WIDTH*GamePanel.SCALE/2-80, 500);
		g.drawString("Back to Menu", GamePanel.WIDTH*GamePanel.SCALE/2-80, 550);
		
		switch(selected){
		case START_GAME: 
			g.setColor(Color.WHITE);
			g.drawString("Start Game", GamePanel.WIDTH*GamePanel.SCALE/2-80+1, 500+1);
			break;
		case BACK_TO_MENU: 
			g.setColor(Color.WHITE);
			g.drawString("Back to Menu", GamePanel.WIDTH*GamePanel.SCALE/2-80+1, 550+1);
			break;
		}
		
	}
	
	public void changeSelected(String direction){
		
		if(direction == "down"){
			if(selected == BACK_TO_MENU){
				selected = START_GAME;
			}else{
				selected++;
			}
		}
		else if(direction == "up"){
			
			if(selected == START_GAME){
				selected = BACK_TO_MENU;
			}else{
				selected--;
			}	
		}
	}
	
	public void chooseSelected(){
		
		if(selected == START_GAME){
			GameLogic.resetGameLogic();
			gsm.setCurrentState(gsm.PLAYSTATE);
		}
		else if(selected == BACK_TO_MENU){
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
