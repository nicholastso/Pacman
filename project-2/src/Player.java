import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player {
	
	// pac-man defs
    private final Image PAC_CLOSE = new Image("res/pac.png");
    private final Image PAC_OPEN = new Image("res/pacOpen.png");
    private final static double MOVE_SIZE = 3;
    private final static int MOUTH_SPEED = 15;
    private int mouthTimer;
    private boolean mouthOpen;
    private Image imageToDraw;
    private DrawOptions rotation = new DrawOptions();
    private final static double FACE_UP = Math.PI*1.5;
    private final static double FACE_DOWN = Math.PI*0.5;
    private final static double FACE_LEFT = Math.PI;
    private final static double FACE_RIGHT = 0;
    
    // hearts defs
    private final Image HEART = new Image("res/heart.png");
    private final static int HEART_X = 900;
    private final static int HEART_Y = 10;
    private final static int NEXT_HEART_X = 30;
    
    private Point position;
    private Point prevPosition;
    
    public Player(int startX, int startY) {
    	this.position = new Point(startX, startY);
    	mouthTimer = 0;
    	mouthOpen = false;
    }
    
    public void update(Input input, ShadowPac gameObject) {
    	
    	// player movements, set rotation of pac-man in the direction it is facing, default right
    	if(input.isDown(Keys.UP)){
            setPrevPosition();
            move(0, -MOVE_SIZE);
            rotation.setRotation(FACE_UP);
        }
    	else if(input.isDown(Keys.DOWN)){
            setPrevPosition();
            move(0, MOVE_SIZE);
            rotation.setRotation(FACE_DOWN);
        }
    	else if(input.isDown(Keys.LEFT)){
            setPrevPosition();
            move(-MOVE_SIZE,0);
            rotation.setRotation(FACE_LEFT);
        }
    	else if(input.isDown(Keys.RIGHT)){
            setPrevPosition();
            move(MOVE_SIZE,0);
            rotation.setRotation(FACE_RIGHT);
        }

    	// timer for when to open and close pac-man mouth
    	if(mouthTimer == 0) {
    		if(!mouthOpen) {
    			mouthOpen = true;
    		}
    		else {
    			mouthOpen = false;
    		}
    		mouthTimer = MOUTH_SPEED;
    	}
    	else {
    		mouthTimer--;
    	}
    	
    	// if mouthOpen = true, draw pac-man opened, otherwise draw pac-man closed
        imageToDraw = mouthOpen ? PAC_OPEN : PAC_CLOSE;
    	imageToDraw.drawFromTopLeft(position.x, position.y, rotation);    	
    	
    }
    
    private void setPrevPosition() {
        this.prevPosition = new Point(position.x, position.y);
    }
    
    public void moveBack() {
        this.position = prevPosition;
    }
    
    private void move(double xMove, double yMove){
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }
    
    public void moveToStart(double xStart, double yStart ) {
    	this.position = new Point(xStart, yStart);
    }
    
    public Rectangle getBoundingBox() {
        return new Rectangle(position, imageToDraw.getWidth(), imageToDraw.getHeight());
    }
    
    public Point getPosition() {
        return position;
    }

    public void drawHeart(int hearts) {
    	for(int i = 0; i < hearts; i++) {
    		HEART.draw(HEART_X + i * NEXT_HEART_X, HEART_Y);
    	}
    }
}