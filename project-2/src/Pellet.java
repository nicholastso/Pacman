import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Pellet {
    private final Image PELLET = new Image("res/pellet.png");
    private final Point position;
    private boolean isActive;
    private GhostRed ghostRed;

    public Pellet(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
    }

    public void update() {
        if(isActive) {
            PELLET.drawFromTopLeft(this.position.x, this.position.y);
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(position, PELLET.getWidth(), PELLET.getHeight());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    
}