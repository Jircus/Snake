package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class for a part of a snake
 * @author Jiøí Škoda
 *
 */
public class SnakePart extends Square {
    
    /**
     * Creates a snake part at specified location with specified size
     */
    public SnakePart(int x, int y, int size) {
    	super(x, y, size);
    }

    /**
     * Paints the part of the snake
     * @param g
     */
    public void paintPart(Graphics g) {
        paintSquare(g, Color.GREEN);
    }
    
}
