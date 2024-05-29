import java.awt.*;

public abstract class Sprite2D {
    // member data
    protected double x, y;
    protected Image myImage1, myImage2; // Separate images
    protected int framesDrawn = 0;
    protected int width, height; // Width and height of the sprite

    // static member data
    protected static int winWidth;
    protected static int winHeight;

    // constructor
    public Sprite2D(Image i1, Image i2) {
        myImage1 = i1;
        myImage2 = i2;
        width = myImage1.getWidth(null);
        height = myImage1.getHeight(null);
    }

    public void setPosition(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public void paint(Graphics g) {
        framesDrawn++;
        if (framesDrawn % 100 < 50) {
            g.drawImage(myImage1, (int) x, (int) y, null);
        } else {
            g.drawImage(myImage2, (int) x, (int) y, null);
        }
    }

    public static void setWinWidth(int w) {
        winWidth = w;
    }

    public static void setWinHeight(int h) {
        winHeight = h;
    }

    // Method to get the width of the sprite
    public int getWidth() {
        return width;
    }

    // Method to get the height of the sprite
    public int getHeight() {
        return height;
    }

    // Method to get the X coordinate of the sprite
    public double getX() {
        return x;
    }

    // Method to get the Y coordinate of the sprite
    public double getY() {
        return y;
    }
}
