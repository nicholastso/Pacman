import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Dot {
    private final Image DOT = new Image("res/dot.png");
    private final Point position;
    private boolean isActive;

    public Dot(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
    }

    public void update() {
        if(isActive) {
            DOT.drawFromTopLeft(this.position.x, this.position.y);
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(position, DOT.getWidth(), DOT.getHeight());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}