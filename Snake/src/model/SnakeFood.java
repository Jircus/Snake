package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for a snake food
 * @author Jiøí Škoda
 *
 */
public class SnakeFood extends Square {
	
	/**
	 * Creates new snake food at random location with specified size
	 */
	public SnakeFood(int size, List<SnakePart> parts) {
		super(size);
		setRandomPosition(parts);
	}

	/**
	 * Sets new random location for the snake food
	 */
	public void setRandomPosition(List<SnakePart> parts) {
		setX(((ThreadLocalRandom.current().nextInt(10, 500) / 10) * 10) - 5);
		setY(((ThreadLocalRandom.current().nextInt(10, 500) / 10) * 10) - 5);
    	
    	//check whether new food location does not collide with the snake
    	for (SnakePart part : parts) {
	    	if (getX() == part.getX() && getY() == part.getY()) {
	    		setRandomPosition(parts);
	    		return;
	    	}
    	}
	}
	
	/**
	 * Paints the food
	 */
	public void paintFood(Graphics g) {
    	paintSquare(g, Color.RED);
    }
	
}
