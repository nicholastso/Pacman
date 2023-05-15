import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Ghost {
    private final Image GHOSTRED = new Image("res/ghostRed.png");
    private final Point position;

    public Ghost(int startX, int startY) {
        this.position = new Point(startX, startY);
    }

    public void update() {
    	GHOSTRED.drawFromTopLeft(this.position.x, this.position.y);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(position, GHOSTRED.getWidth(), GHOSTRED.getHeight());
    }
}
