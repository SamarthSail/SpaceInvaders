import java.awt.Image;

public class PlayerBullet extends Sprite2D {
    private static double ySpeed = 10;
    private boolean isActive = true; // to determine if bullet is active

    public PlayerBullet(Image i1, Image i2) {
        super(i1, i2);
    }

    public boolean move() {
        y -= ySpeed;

        // return true when bullet needs to disappear
        if (y <= 0) {
            isActive = false; // Deactivate bullet when it goes out of screen
            return true;
        } else {
            return false;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
