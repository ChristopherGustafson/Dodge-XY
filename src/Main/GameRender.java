package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameRender extends GamePanel{
	
	private static final long serialVersionUID = 1L;
	
	static File bombPic = new File("Pictures/bomb2.png");
	static Image bomb;
	
	static File groundPlatformPic = new File("Pictures/groundPlatform.jpg");
	static Image groundPlatform;
	
	static File topPlatformPic = new File("Pictures/topPlatform.jpg");
	static Image topPlatform;
	
	static File leftPlatformPic = new File("Pictures/leftPlatform.jpg");
	static Image leftPlatform;
	
	static File rightPlatformPic = new File("Pictures/rightPlatform.jpg");
	static Image rightPlatform;
	
	static File characterFrontPic = new File("Pictures/characterFront.png");
	static Image characterFront;
	
	static File characterRightPic = new File("Pictures/characterRight.png");
	static Image characterRight;
	
	static File characterLeftPic = new File("Pictures/characterLeft.png");
	static Image characterLeft;
	
	static Font textFont;
	
	//Loads all the images and the font used in the program
	
	public static void loadResources(){
		try {
			bomb = ImageIO.read(bombPic);
			groundPlatform = ImageIO.read(groundPlatformPic);
			topPlatform = ImageIO.read(topPlatformPic);
			leftPlatform = ImageIO.read(leftPlatformPic);
			rightPlatform = ImageIO.read(rightPlatformPic);
			characterFront = ImageIO.read(characterFrontPic);
			characterRight = ImageIO.read(characterRightPic);
			characterLeft = ImageIO.read(characterLeftPic);
			textFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/BebasNeue.ttf"))).deriveFont(Font.PLAIN, 20);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	//Draws the background and the platforms on the screen

	public static void drawGameBoard(Graphics g){
		g.setColor(new Color(51, 204, 255));
		g.fillRect(0, 0, 2000, 2000);
		g.setColor(Color.GREEN);
		
		g.drawImage(groundPlatform, GameLogic.groundPlatform.x, GameLogic.groundPlatform.y, GameLogic.groundPlatform.width, GameLogic.groundPlatform.height, null);
		g.drawImage(leftPlatform, GameLogic.leftPlatform.x, GameLogic.leftPlatform.y, GameLogic.leftPlatform.width, GameLogic.leftPlatform.height, null);	
		g.drawImage(rightPlatform, GameLogic.rightPlatform.x, GameLogic.rightPlatform.y, GameLogic.rightPlatform.width, GameLogic.rightPlatform.height, null);
		g.drawImage(topPlatform, GameLogic.topPlatform.x, GameLogic.topPlatform.y, GameLogic.topPlatform.width, GameLogic.topPlatform.height, null);
	
	}
	
	//Draws the player to the screen
	
	public static void drawPlayer(Graphics g){
		g.setColor(Color.RED);
		if(GameLogic.direction == GameLogic.front){
			g.drawImage(characterFront, GameLogic.player.x, GameLogic.player.y, GameLogic.player.width, GameLogic.player.height, null);
		}
		else if(GameLogic.direction == GameLogic.right){
			g.drawImage(characterRight, GameLogic.player.x, GameLogic.player.y, GameLogic.player.width, GameLogic.player.height, null);
		}
		else if(GameLogic.direction == GameLogic.left){
			g.drawImage(characterLeft, GameLogic.player.x, GameLogic.player.y, GameLogic.player.width, GameLogic.player.height, null);
		}
	}
	
	//Draws the bombs to the screen
	
	public static void drawBombs(Graphics g){
		
		for(int i = 0; i < GameLogic.leftBombs.size(); i++){
			//g.fillRect(GameLogic.leftBombs.get(i).x, GameLogic.leftBombs.get(i).y, GameLogic.leftBombs.get(i).width, GameLogic.leftBombs.get(i).height);
			g.drawImage(bomb, GameLogic.leftBombs.get(i).x, GameLogic.leftBombs.get(i).y, GameLogic.leftBombs.get(i).width, GameLogic.leftBombs.get(i).height, null);
		}
		
		for(int i = 0; i < GameLogic.rightBombs.size(); i++){
			//g.fillRect(GameLogic.leftBombs.get(i).x, GameLogic.leftBombs.get(i).y, GameLogic.leftBombs.get(i).width, GameLogic.leftBombs.get(i).height);
			g.drawImage(bomb, GameLogic.rightBombs.get(i).x, GameLogic.rightBombs.get(i).y, GameLogic.rightBombs.get(i).width, GameLogic.rightBombs.get(i).height, null);
		}
		
	}
	
	//Draws the player's score to the screen
	
	public static void drawScore(Graphics g){
		g.setColor(new Color(23, 28, 36));
		g.setFont(textFont);
		g.drawString("Score: "+GameLogic.score, 30, 40);
	}
}
