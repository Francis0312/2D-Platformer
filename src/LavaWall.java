import java.awt.Color;
import java.awt.Rectangle;

/**
 * 
 * @author Francisco Reyna
 * University of Texas at Austin - Computer Science
 */

public class LavaWall extends Wall{

    //Instance Variables
    private static Color LAVA_COLOR;
    
    public LavaWall(int x, int y, int width, int height) {
        super(x, y, width, height);
        LAVA_COLOR = Color.RED;
    }
}