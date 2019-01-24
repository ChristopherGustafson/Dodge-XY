/*
 * The starting point of the game, creates the window in which the game is going to be
 * displayed and make sure it's visible, able to quit and so on...
 */

package Main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Dodge XY");
		
		frame.add(new GamePanel());
		frame.pack();
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}

