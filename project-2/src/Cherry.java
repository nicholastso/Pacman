import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Cherry {
    private final Image CHERRY = new Image("res/cherry.png");
    private final Point position;
    private boolean isActive;

    public Cherry(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
    }

    public void update() {
        if(isActive) {
            CHERRY.drawFromTopLeft(this.position.x, this.position.y);
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(position, CHERRY.getWidth(), CHERRY.getHeight());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}