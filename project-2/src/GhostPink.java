import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

public class GhostPink {
    private final Image GHOSTPINK = new Image("res/ghostPink.png");
    private final Image GHOSTFRENZY = new Image("res/ghostFrenzy.png");
    private final static double FRENZYSIZE = 0.5;
    private final static double MOVESIZE = 3;

    private static final int NUM_DIRECTION = 4;
    private double newMoveSizeX = 3;
    private double newMoveSizeY = 3;
    private Point position;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean moveUp;
    private boolean moveDown;
    private Point prevPosition;
    private int frenzyTimer;
    private boolean frenzy;

    public GhostPink(int startX, int startY) {
        this.position = new Point(startX, startY);
        frenzy = false;
        frenzyTimer = 0;
        
        Random random = new Random();
        int direction = random.nextInt(NUM_DIRECTION);
        
        switch(direction) {
        	case 0:
        		moveRight = true;
        		break;
        	case 1:
        		moveLeft = true;
        		break;
        	case 2:
        		moveUp = true;
        		break;
        	case 3:
        		moveDown = true;
        		break;
        }
        
    }

    public void update() {
    	if(frenzy) {
    		GHOSTFRENZY.drawFromTopLeft(this.position.x, this.position.y);
    		
    		// check direction its moving
    		if(moveRight || moveLeft) {
        		newMoveSizeX = MOVESIZE - FRENZYSIZE;
    		}
    		else {
    			newMoveSizeY = MOVESIZE - FRENZYSIZE;
    		}
    		
	        frenzyTimer++;
	        
	        if(frenzyTimer == 1000) {
	        	frenzy = false;
	        	if(moveRight || moveLeft) {
	        		newMoveSizeX = MOVESIZE - FRENZYSIZE;
	    		}
	    		else {
	    			newMoveSizeY = MOVESIZE - FRENZYSIZE;
	    		}
	        }
	        
    	}
    	else {
    		GHOSTPINK.drawFromTopLeft(this.position.x, this.position.y);
    	}
    	
    	// horizontal or vertical movement until collision then go opposite
    	double right = position.x + newMoveSizeX;
    	double down = position.y - newMoveSizeY;
    	double left = position.x - newMoveSizeX;
    	double up = position.y + newMoveSizeY;

    	if(moveRight) {
            setPrevPosition();
    		this.position = new Point(right, position.y);
    	}
    	else if(moveLeft) {
    		setPrevPosition();
    		this.position = new Point(left, position.y);
    	}
    	else if(moveDown) {
    		setPrevPosition();
    		this.position = new Point(position.x, down);
    	}
    	else if(moveUp) {
    		setPrevPosition();
    		this.position = new Point(position.x, up);
    	}
    	
    }

    public void changeDirection() {
    	// randomly generates 0 to 3
        Random random = new Random();
        int direction = random.nextInt(NUM_DIRECTION);
        switch(direction) {
        	case 0:
        		if(moveRight) {
        			changeDirection();
        		}
        		else {
        			moveRight = true;
        			moveLeft = false;
        			moveUp = false;
        			moveDown = false;
        		}
        		break;
        	case 1:
        		if(moveLeft) {
        			changeDirection();
        		}
        		else {
        			moveRight = false;
        			moveLeft = true;
        			moveUp = false;
        			moveDown = false;
        		}
        		break;
        	case 2:
        		if(moveUp) {
        			changeDirection();
        		}
        		else {
        			moveRight = false;
        			moveLeft = false;
        			moveUp = true;
        			moveDown = false;
        		}
        		break;
        	case 3:
        		if(moveDown) {
        			changeDirection();
        		}
        		else {
        			moveRight = false;
        			moveLeft = false;
        			moveUp = false;
        			moveDown = true;
        		}
        		break;
        }
    
    }
    
    public Rectangle getBoundingBox() {
    	if(frenzy) {
            return new Rectangle(position, GHOSTFRENZY.getWidth(), GHOSTFRENZY.getHeight());
    	}
    	else {
    		return new Rectangle(position, GHOSTPINK.getWidth(), GHOSTPINK.getHeight());
    	}
    }
    
    public void ghostPinkFrenzy() {
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
