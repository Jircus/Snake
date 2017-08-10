package game;
import javax.swing.JFrame; 

/**
 * Main class for starting the application
 * @author Jiøí Škoda
 *
 */
public class Main {

	public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.add(new Game());
        frame.setSize(516, 537);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
}