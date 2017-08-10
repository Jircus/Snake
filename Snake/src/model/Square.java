package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * General class for a square
 * @author Jiøí Škoda
 *
 */
public abstract class Square {

	private int x;
    private int y;
    private int size;
    
    /**
     * Creates a square with specific size
     */
    public Square(int size) {
    	this.size = size;
    }
    
    /**
     * Creates a square with specific size and at the specific location
     */
    public Square(int x, int y, int size) {
    	this.x = x;
    	this.y = y;
    	this.size = size;
    }

    public void setX(int x) { 
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    /**
     * Paints the square
     */
    public void paintSquare(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
        g.drawRect(x, y, size, size);
    }
    
}
