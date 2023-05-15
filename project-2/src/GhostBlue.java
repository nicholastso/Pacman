import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class GhostBlue {
    private final Image GHOSTBLUE = new Image("res/ghostBlue.png");
    private final Image GHOSTFRENZY = new Image("res/ghostFrenzy.png");
    private final static double FRENZYSIZE = 0.5;
    private final static double MOVESIZE = 2;

    private double newMoveSize = 2;
    private Point position;
    private int frenzyTimer;
    private boolean frenzy;
    private Point prevPosition;

    public GhostBlue(int startX, int startY) {
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
    		GHOSTBLUE.drawFromTopLeft(this.position.x, this.position.y);
    	}
    	
    	// vertical movement until collision then go opposite
    	double newY = position.y - newMoveSize;
    	setPrevPosition();
        this.position = new Point(position.x, newY);
    }

    public void changeDirection() {
    	newMoveSize = -newMoveSize;
    }
    
    public Rectangle getBoundingBox() {
    	if(frenzy) {
            return new Rectangle(position, GHOSTFRENZY.getWidth(), GHOSTFRENZY.getHeight());
    	}
    	else {
    		return new Rectangle(position, GHOSTBLUE.getWidth(), GHOSTBLUE.getHeight());
    	}
    }
    
    public void ghostBlueFrenzy() {
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
