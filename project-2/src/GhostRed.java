import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class GhostRed {
    private final Image GHOSTRED = new Image("res/ghostRed.png");
    private final Image GHOSTFRENZY = new Image("res/ghostFrenzy.png");
    private final static double FRENZYSIZE = 0.5;
    private final static double MOVESIZE = 1;

    private double newMoveSize = 1;
    private Point position;
    private boolean frenzy;
    private int frenzyTimer;
    private Point prevPosition;

    public GhostRed(int startX, int startY) {
        this.position = new Point(startX, startY);
        frenzy = false;
        frenzyTimer = 0;
    }

    public void update() {    	
    	if(frenzy) {
    		GHOSTFRENZY.drawFromTopLeft(this.position.x, this.position.y);
    		newMoveSize = MOVESIZE - FRENZYSIZE;
    		
	        frenzyTimer++;
	        
	        if(frenzyTimer == 1000) {
	        	frenzy = false;
	        	newMoveSize = MOVESIZE;
	        }
	        
    	}
    	else {
    		GHOSTRED.drawFromTopLeft(this.position.x, this.position.y);
    	}
    	
    	// horizontal movement until collision then go opposite
		double newX = position.x + newMoveSize;
		setPrevPosition();
        this.position = new Point(newX, position.y);
    }

    public void changeDirection() {
    	newMoveSize = -newMoveSize;
    }
    
    public Rectangle getBoundingBox() {
    	if(frenzy) {
            return new Rectangle(position, GHOSTFRENZY.getWidth(), GHOSTFRENZY.getHeight());
    	}
    	else {
    		return new Rectangle(position, GHOSTRED.getWidth(), GHOSTRED.getHeight());
    	}
    }
    
    public void ghostRedFrenzy() {
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
