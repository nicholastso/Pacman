import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

public class GhostGreen {
    private final Image GHOSTGREEN = new Image("res/ghostGreen.png");
    private final Image GHOSTFRENZY = new Image("res/ghostFrenzy.png");
    private final static double FRENZYSIZE = 0.5;
    private final static double MOVESIZE = 4;

    private double newMoveSizeX = 4;
    private double newMoveSizeY = 4;
    private Point position;
    private int frenzyTimer;
    private boolean frenzy;
    private Point prevPosition;

    public GhostGreen(int startX, int startY) {
        this.position = new Point(startX, startY);
        frenzy = false;
        frenzyTimer = 0;
        
        // randomly generates 0 or 1, 0 for vertical movement, 1 for horizontal
        Random random = new Random();
        boolean moveUp = random.nextInt(2) == 0;
        
        if(moveUp) {
        	newMoveSizeX = 0;
        }
        else {
        	newMoveSizeY = 0;
        }
        
    }

    public void update() {
    	if(frenzy) {
    		GHOSTFRENZY.drawFromTopLeft(this.position.x, this.position.y);
    		
    		// check if moving up or down
    		if(newMoveSizeX == 0) {
        		newMoveSizeY = MOVESIZE - FRENZYSIZE;
    		}
    		else {
    			newMoveSizeX = MOVESIZE - FRENZYSIZE;
    		}
    		
	        frenzyTimer++;
	        
	        if(frenzyTimer == 1000) {
	        	frenzy = false;
	        	if(newMoveSizeX == 0) {
	        		newMoveSizeY = MOVESIZE + FRENZYSIZE;
	    		}
	    		else {
	    			newMoveSizeX = MOVESIZE + FRENZYSIZE;
	    		}
	        }
	        
    	}
    	else {
    		GHOSTGREEN.drawFromTopLeft(this.position.x, this.position.y);
    	}
    	
    	// horizontal or vertical movement until collision then go opposite
    	double newX = position.x + newMoveSizeX;
    	double newY = position.y - newMoveSizeY;
    	setPrevPosition();
        this.position = new Point(newX, newY);
    }

    public void changeDirection() {
    	newMoveSizeX = -newMoveSizeX;
    	newMoveSizeY = -newMoveSizeY;
    }
    
    public Rectangle getBoundingBox() {
    	if(frenzy) {
            return new Rectangle(position, GHOSTFRENZY.getWidth(), GHOSTFRENZY.getHeight());
    	}
    	else {
    		return new Rectangle(position, GHOSTGREEN.getWidth(), GHOSTGREEN.getHeight());
    	}
    }
    
    public void ghostGreenFrenzy() {
    	frenzy = true;
    }
    
    private void setPrevPosition() {
        this.prevPosition = new Point(position.x, position.y);
    }
    
    public void moveBack() {
        this.position = prevPosition;
    }

    public Point getPosition() {
        return position;
    }
    
}
