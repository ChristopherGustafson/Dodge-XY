package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private static boolean running = false;
	
	public final static int WIDTH = 160;
	public final static int HEIGHT = 90;
	public final static int SCALE = 7;
	private Thread thread;
	
	//Calculates the target time to wait between each tick
	private final long FPS = 60;
	private final long TARGET_WAIT_TIME = 1000/FPS; 
	
	Graphics g = getGraphics();
	
	protected GameStateManager gsm;

	public GamePanel(){
		
		//Adds the gamepanel to the window, starts the game loop and initiates the keyListener
	
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		initGame();
	}
	
	public void initGame(){
		running = true;
		gsm = new GameStateManager();
		
		thread = new Thread(this);
		thread.start();
	}

	/* 
	 * The core of the game loop, tells the GameStateManager to update the game, and then render it.
	 * Before it then loops trough this again it waits a certain amount of time, depending on how long
	 * the previous updates and renders took.
	 */
	
	@Override
	public void run() {
		
		while(running){
			
			long startTime = System.nanoTime();
			
			
			//Updates the game, then renders it
			gsm.updateGame();
			repaint();
			
			Toolkit.getDefaultToolkit().sync();
			
			//The time updating and rendering took, (ms)
			long elapsedTime = (System.nanoTime() - startTime) / 1000000;
			
			long waitTime = TARGET_WAIT_TIME - elapsedTime;
			
			try {
				if(waitTime < 0){
					waitTime = 0;
				}
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	/*
	 * Everytime a button is either pressed or released the the method setKeyState in the GameStateManager is called to tell
	 * the current gamestate that a button has been pressed/released
	 */
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		gsm.renderGame(g);
	}

	public void keyPressed(KeyEvent e) {
		gsm.setKeyState(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		gsm.setKeyState(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {	
	}

}
