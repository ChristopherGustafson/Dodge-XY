/*
 * The GameLogic class is the class where all the logic behind the gameplay is located, which is which is controlling what
 * is happening on the screen and how for example the character is supposed to react to certain presses on the keyboard.
 * 
 */

package Main;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameLogic extends GamePanel{
	
	private static final long serialVersionUID = 1L;
	
	public static boolean gameOver = false;
	public static int score;
	
	static final Rectangle topPlatform = new Rectangle(44*SCALE, 25*SCALE, 72*SCALE, 3*SCALE);
	public static final Rectangle rightPlatform = new Rectangle(98*SCALE, 50*SCALE, 32*SCALE, 3*SCALE);
	public static final Rectangle leftPlatform = new Rectangle(30*SCALE, 50*SCALE, 32*SCALE, 3*SCALE);
	static final Rectangle groundPlatform = new Rectangle(16*SCALE, 75*SCALE, 128*SCALE, 40*SCALE);
	
	static Rectangle player = new Rectangle(17*SCALE, 60*SCALE, 4*SCALE, 12*SCALE);

	static double velocityX;
	static double velocityY;
	static double accelerationY;
	
	static int jumps;
	static boolean standing;
	static boolean ducking;
	static int duckingVel = 3;
	
	static int direction = 0;
	static final int front = 0;
	static final int right = 1;
	static final int left = 2;
	
	static int lastPlayerX;
	static int lastPlayerY;
	
	static int bombVelocity = 5;
	static double bombCounter = 0;
	static double bombLimit = 40;
	static double bombLimitCounter = 0;
	
	static List<Rectangle> leftBombs;
	static List<Rectangle> rightBombs;
	
	static int duckPlayerHeight = player.height - player.height/3+2;
	
	public static boolean keyUp;
	public static boolean keyDown;
	public static boolean keyRight;
	public static boolean keyLeft;
	
	//Resets all the variables, the method is called when the player restarts the game and score, bombspawnrates for example are reset.
	
	public static void resetGameLogic(){
		gameOver = false;
		score = 0;
		
		keyUp = false;
		keyDown = false;
		keyRight = false;
		keyLeft = false;
		
		player.x = WIDTH*SCALE/2-player.width/2;
		player.y = 60*SCALE;
		velocityX = 0;
		velocityY = 1;
		accelerationY = 1;
		
		jumps = 0;
		standing = false;
		ducking = false;
		direction = 0;
		
		leftBombs = new ArrayList<Rectangle>();
		rightBombs = new ArrayList<Rectangle>();
		
		bombCounter = 0;
		bombLimit = 40;
		bombLimitCounter = 0;
	}
	
	//Moves the character by either adding or subtracting from the players x and y-coordinate.
	
	public static void moveCharacter(){
		setVelocityX();
		setVelocityY();
		
		lastPlayerX = player.x;
		lastPlayerY = player.y;
		
		player.x += velocityX;
		player.y += velocityY;
	}
	
	//Makes the character jump by increasing its velocity upwards
	
	public static void jump(){
		jumps++;
		if(jumps <= 2){
			velocityY = -15;
		}
	}
	
	//Checks which keys are currently pressed and sets the players velocity and direcion based on it.
	
	public static void setVelocityX(){
		
		if(keyRight){
			if(keyLeft){
				velocityX = 0;
				direction = front;
			}else{
				velocityX = 6;
				direction = right;
			}
		}
		
		else if(keyLeft){
			velocityX = -6;
			direction = left;
		}else{
			velocityX = 0;
			direction = front;
		}
		
		if(keyDown){
			 if(velocityX != 0){
				 if(velocityX > 0){
					 velocityX = duckingVel;
					 direction = right;
				 }else{
					 velocityX = -duckingVel;
					 direction = left;
				 }
			 }
			 
			 if(!ducking){
				 player.height -= 4*SCALE;
				 player.y += 4*SCALE;
				 ducking = true;
			 }
		}else{
			ducking = false;
			if(player.height < 10*SCALE){
				player.height += 4*SCALE;
				player.y -= 4*SCALE;
			}
		}		
	}
	
	//Decreases the Y-velocity every tick, while the player is in the air the amount it decreases increases each tick which creates gravity. 
	
	public static void setVelocityY(){
		velocityY += accelerationY;
		accelerationY += 0.001;	
	}
	
	/*
	 * The spawn timer for the bombs. Every 40 ticks a new bomb is being spawned, this amount decreases every 20 seconds making the bombs
	 * spawn faster. The bombs are spawned at one side at a time.
	 * 
	 */
	
	public static void bombCounter(){
		
		if(bombCounter == bombLimit){
			int rand = (int)(Math.random()*9+1);
			spawnLeftBomb(rand);
		}
		
		else if(bombCounter == bombLimit*2){
			int rand = (int)(Math.random()*9+1);
			spawnRightBomb(rand);
			bombCounter = 0;
			bombLimitCounter++;
		}
		
		bombCounter++;
		bombLimitCounter++;
		
		if(bombLimitCounter == 20*60){
			bombLimit -= 2;
			bombLimitCounter = 0;
		}
		
		System.out.println(bombLimit);
	}
	
	/*
	 * Spawns a bomb from the left side, randomizes one of nine spawn positions and creates a bomb at that position, every fourth bomb spawn
	 * will also spawn bombs covering the whole space between two platforms
	 */
	
	public static void spawnLeftBomb(int spawnpoint){
		Rectangle r;
		int x = 4;
		
		int rand = (int)(Math.random()*4+1);
		
		if(rand == 4){
			
			if(spawnpoint == 1 || spawnpoint == 2 || spawnpoint == 3){
				r = new Rectangle(x, 7*SCALE-10, 20, 20);
				leftBombs.add(r);
				r = new Rectangle(x, 14*SCALE-10, 20, 20);
				leftBombs.add(r);
				r = new Rectangle(x, 21*SCALE-10, 20, 20);
				leftBombs.add(r);
			}
			
			else if(spawnpoint == 4 || spawnpoint == 5 || spawnpoint == 6){
				r = new Rectangle(x, 32*SCALE-10, 20, 20);
				leftBombs.add(r);
				r = new Rectangle(x, 39*SCALE-10, 20, 20);
				leftBombs.add(r);
				r = new Rectangle(x, 46*SCALE-10, 20, 20);
				leftBombs.add(r);
			}
			
			else if(spawnpoint == 7 || spawnpoint == 8 || spawnpoint == 9){
				r = new Rectangle(x, 57*SCALE-10, 20, 20);
				leftBombs.add(r);
				r = new Rectangle(x, 64*SCALE-10, 20, 20);
				leftBombs.add(r);
				r = new Rectangle(x, 71*SCALE-10, 20, 20);
				leftBombs.add(r);
			}
			
		}
		
		else{
		
			switch (spawnpoint) {
				case 1:
					r = new Rectangle(x, 7*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 2:
					r = new Rectangle(x, 14*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 3:
					r = new Rectangle(x, 21*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 4:
					r = new Rectangle(x, 32*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 5:
					r = new Rectangle(x, 39*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 6:
					r = new Rectangle(x, 46*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 7:
					r = new Rectangle(x, 57*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 8:
					r = new Rectangle(x, 64*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
				case 9:
					r = new Rectangle(x, 71*SCALE-10, 20, 20);
					leftBombs.add(r);
					break;
			}
		}
		
	}
	
	//Does the same thing as "spawnLeftBomb" but spawns it from the right
	
	public static void spawnRightBomb(int spawnpoint){
		Rectangle r;
		int x = WIDTH*SCALE-4;
		
		int rand = (int)(Math.random()*4+1);
		
		if(rand == 4){
			
			if(spawnpoint == 1 || spawnpoint == 2 || spawnpoint == 3){
				r = new Rectangle(x, 7*SCALE-10, 20, 20);
				rightBombs.add(r);
				r = new Rectangle(x, 14*SCALE-10, 20, 20);
				rightBombs.add(r);
				r = new Rectangle(x, 21*SCALE-10, 20, 20);
				rightBombs.add(r);
			}
			
			else if(spawnpoint == 4 || spawnpoint == 5 || spawnpoint == 6){
				r = new Rectangle(x, 32*SCALE-10, 20, 20);
				rightBombs.add(r);
				r = new Rectangle(x, 39*SCALE-10, 20, 20);
				rightBombs.add(r);
				r = new Rectangle(x, 46*SCALE-10, 20, 20);
				rightBombs.add(r);
			}
			
			else if(spawnpoint == 7 || spawnpoint == 8 || spawnpoint == 9){
				r = new Rectangle(x, 57*SCALE-10, 20, 20);
				rightBombs.add(r);
				r = new Rectangle(x, 64*SCALE-10, 20, 20);
				rightBombs.add(r);
				r = new Rectangle(x, 71*SCALE-10, 20, 20);
				rightBombs.add(r);
			}
		}
		
		else{
		
			switch (spawnpoint) {
				case 1:
					r = new Rectangle(x, 7*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 2:
					r = new Rectangle(x, 14*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 3:
					r = new Rectangle(x, 21*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 4:
					r = new Rectangle(x, 32*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 5:
					r = new Rectangle(x, 39*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 6:
					r = new Rectangle(x, 46*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 7:
					r = new Rectangle(x, 57*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 8:
					r = new Rectangle(x, 64*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
				case 9:
					r = new Rectangle(x, 71*SCALE-10, 20, 20);
					rightBombs.add(r);
					break;
			}
		}
		
	}
	
	//Moves every bomb which is currently being displayed, removes a bomb if it leaves the window
	
	public static void moveBombs(){
		for(int i = 0; i < leftBombs.size(); i++){
			if(leftBombs.get(i).x < SCALE*WIDTH){
				Rectangle r = new Rectangle(leftBombs.get(i).x+bombVelocity, leftBombs.get(i).y, leftBombs.get(i).width, leftBombs.get(i).height);
				leftBombs.set(i, r);
			}
			else{
				leftBombs.remove(i);
			}
		}
		
		for(int i = 0; i < rightBombs.size(); i++){
			if(rightBombs.get(i).x < SCALE*WIDTH){
				Rectangle r = new Rectangle(rightBombs.get(i).x-bombVelocity, rightBombs.get(i).y, rightBombs.get(i).width, rightBombs.get(i).height);
				rightBombs.set(i, r);
			}
			else{
				rightBombs.remove(i);
			}
		}
	}
	
	/*
	 * Checks if the player has collided with either of the fourth platforms or any of the bombs, if the player has collided with a bomb
	 * the game is over and the game enters the GameOverState
	 */
	
	public static void checkCollision(){	
		checkPlatformCollision(groundPlatform);
		checkPlatformCollision(topPlatform);
		checkPlatformCollision(rightPlatform);
		checkPlatformCollision(leftPlatform);
		
		//Check collision with bombs approaching from left by looping trough the 
		//array of bombs from left and checking if any of them is intersecting the player
		
		for(int i = 0; i < leftBombs.size(); i++){
			if(player.intersects(leftBombs.get(i))){
				System.out.println("death");
				gameOver = true;
			}
		}
		
		//Check collision with bombs approaching from right by looping trough the 
		//array of bombs from right and checking if any of them is intersecting the player

		for(int i = 0; i < rightBombs.size(); i++){
			if(player.intersects(rightBombs.get(i))){
				gameOver = true;
			}
		}
		
		if(player.y > HEIGHT*SCALE+player.height*2){
			gameOver = true;
		}
		
		
	}
	
	/*
	 * Checks if the player have collided with a certain platform, if the player for example have collided from the top the players
	 * y-coordinate will be set to the y-coordinate of the platform, making it look like the character is standing on the platform
	 */
	
	public static void checkPlatformCollision(Rectangle platform){
		if(platform.intersects(player)){
			if(lastPlayerY+player.height <= platform.y){
				player.y = platform.y-player.height;
				velocityY = 1;
				accelerationY = 1;
				jumps = 0;
			}
			else if(lastPlayerY >= platform.y+platform.height){
				player.y = platform.y+platform.height;
			}
			else{
				if(player.x < platform.x+platform.width/2){
					player.x = platform.x-player.width;
				}
				else if(player.x > platform.x+platform.width/2){
					player.x = platform.x+platform.width;
				}
			}
		}
	}
	
	//Increases the score, gets called every tick making the score increase with time
	
	public static void updateScore(){
		score += 10;
	}
	
	//Registers if a key is being pressed or released, also calls the jump method if space is pressed
	
	public static void setKeyState(int key, boolean state){
		if(key == KeyEvent.VK_UP){
			keyUp = state;
			if(state){
				jump();
			}
		}
		else if(key == KeyEvent.VK_DOWN){
			keyDown = state;
		}
		else if(key == KeyEvent.VK_RIGHT){
			keyRight = state;
		}
		else if(key == KeyEvent.VK_LEFT){
			keyLeft = state;
		}
	}

	

	
}	
