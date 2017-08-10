package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.SnakeFood;
import model.SnakePart;

/**
 * Game GUI and logic
 * @author Jiøí Škoda
 *
 */
@SuppressWarnings("serial")
class Game extends JPanel {
	
	private Timer timer;
	int left, right, up, down, direction, controlDirection, foodX, foodY, size;
	private List<SnakePart> snakeParts;
	private boolean ran;
	private SnakeFood snakeFood;
	
	/**
	 * Constructor for creating new instance of a game
	 */
    public Game() {
    	left = KeyEvent.VK_LEFT;
    	right = KeyEvent.VK_RIGHT;
    	up = KeyEvent.VK_UP;
    	down = KeyEvent.VK_DOWN;
    	size = 8;
    	direction = 0;
    	ran = false;
    	setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));       
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
            	changeDirection(e);
            }
        });       
        setFocusable(true);
        requestFocusInWindow();
    }
    
    /**
     * This method is triggered every time player presses a key
     * It changes the direction of a snake based on user's input
     */
    private void changeDirection(KeyEvent e) {
    	//change of direction to the opposite of current one is not allowed
    	if((e.getKeyCode() == down && controlDirection != up)
    			|| (e.getKeyCode() == right && controlDirection != left)
    			|| (e.getKeyCode() == up && controlDirection != down)
    			|| (e.getKeyCode() == left && controlDirection != right)) {
    		
    		//runs the game if it's not already running
    		if(ran == false) {
        		runGame();
        	}
    		
    		direction = e.getKeyCode();
    	}
    }
    
    /**
     * This method creates food and a snake and makes the snake move
     */
    public void runGame() {
		ran = true;
		snakeParts = new ArrayList<>();
    	snakeParts.add(new SnakePart(245, 245, size));
    	for(int i = 0; i <= 3; i++) {
    		snakeParts.add(new SnakePart(-10, -10, size));
    	}
		snakeFood = new SnakeFood(size, snakeParts);
		foodX = snakeFood.getX();
		foodY = snakeFood.getY();
		repaint();
    	timer = new Timer(80, moveSnake);
    	timer.setInitialDelay(200);
    	timer.start();
    	System.out.println("Game has started");
    }
    
    /**
     * This method stops the timer and thus also the snake
     * It raises confirm dialog which results either in a new game or termination of the app
     */
    public void stopGame() {
    	timer.stop();
    	controlDirection = 0;
    	direction = 0;
    	System.out.println("Game over (score: " + (snakeParts.size() - 5) + ")");
    	String message = "<html><div width='190px' align='center'>Your score: " + (snakeParts.size() - 5)
    			+ "<br><br>Do you want to play again?<br><br></div></html>";
    	JLabel label = new JLabel(message);    	
    	int confirm = JOptionPane.showConfirmDialog(this, label, "Game over", JOptionPane.YES_NO_OPTION, -1);
    	
    	//If player responded yes repaint GUI, otherwise exit the app
    	if (confirm == 0) {
    		ran = false;
    		repaint();
    	} else {
    		System.exit(0);
    	}
    }
    
    /**
     * This action is fired in regular intervals by the timer
     * It keeps the snake moving and checks whether it crashed or ate a food
     */
    private ActionListener moveSnake = new ActionListener() {   
        @Override
        public void actionPerformed(ActionEvent event) {
        	SnakePart head = snakeParts.get(0);
        	int x = head.getX();
        	int y = head.getY();
        	
        	//check whether the snake hit a wall
    		if (x < 5 || x > 495 || y < 5 || y > 500) {
    			stopGame();
    			return;
    		} 
			int lastIndex = snakeParts.size() - 1;
			
			//check every part whether it was hit and if not reposition it
			for (int i = lastIndex; i > 0; i--) {
				SnakePart actualPart = snakeParts.get(i);
				if (head.getX() == actualPart.getX() && head.getY() == actualPart.getY()) {
					stopGame();
					return;
				} else {
    				actualPart.setX(snakeParts.get(i - 1).getX());
    				actualPart.setY(snakeParts.get(i - 1).getY());
				}
			}
			
			//change the snake's head position based on user's input
			if (direction == left) {
				head.setX(x - 10);
				controlDirection = left;
			} else if (direction == right) {
				head.setX(x + 10);
				controlDirection = right;
			} else if (direction == up) {
				head.setY(y - 10);
				controlDirection = up;
			} else if (direction == down) {
				head.setY(y + 10);
				controlDirection = down;
			}
			
			//check if snake ate the food and if so, reposition the food and add new snake part
			if (head.getX() == foodX && head.getY() == foodY) {
				snakeParts.add(new SnakePart(-10, -10, size));
				snakeFood.setRandomPosition(snakeParts);
				foodX = snakeFood.getX();
				foodY = snakeFood.getY();
				System.out.println("Score: " + (snakeParts.size() - 5));
				
				//speed up the snake when it reaches a specified length
				if (lastIndex == 28 || lastIndex == 53 || lastIndex == 103 || lastIndex == 203) {
        			timer.setDelay(timer.getDelay() - 10);
        			System.out.println("Snake has sped up");
        		}
			}  			
			repaint();
        }            
    };
    
    /**
     * Overridden method that paints whole GUI
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //before start of the game paint a label then paint a snake and a food
        if (ran) {
	        for (SnakePart snake : snakeParts) {
	        	snake.paintPart(g);
	        }
	        snakeFood.paintFood(g);
        } else {
        	g.setFont(new Font(g.getFont().toString(), Font.BOLD, 18));
        	g.setColor(Color.GREEN);
        	g.drawString("Press an arrow key to start the game", 90, 240);
        }
    }
    
}
