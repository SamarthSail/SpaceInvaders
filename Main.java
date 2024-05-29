/**
 * id (22361553)
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends JFrame implements Runnable, KeyListener {
    
    // member data
    private static String workingDirectory;
    private static boolean isInitialised = false;
    private static final Dimension WindowSize = new Dimension(800,600);
    private BufferStrategy strategy;
    private Graphics offscreenGraphics;
    private static final int NUMALIENS = 30;
    private Alien[] AliensArray = new Alien[NUMALIENS];
    private Spaceship PlayerShip;
    private Image bulletImage;
    private ArrayList bulletsList = new ArrayList();
    private int score;
    private int fleetSpeed = 4;
    private boolean gameRunning = false;
    
    // constructor
    public Main() {
        //Display the window, centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - WindowSize.width/2;
        int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);
        this.setTitle("Space Invaders! .. (getting there)");
        
        // load image from disk
        ImageIcon icon = new ImageIcon("C:\\alien_ship_1.png");
        Image alienImage = icon.getImage();
        icon = new ImageIcon("C:\\alien_ship_2.png");
        Image alienImage2 = icon.getImage();
        // create and initialise some aliens, passing them each the image we have loaded
        for (int i=0; i<NUMALIENS; i++) {
            AliensArray[i] = new Alien(alienImage,alienImage2);
            double xx = (i%5)*80 + 70;
            double yy = (i/5)*40 + 50; // integer division!
            AliensArray[i].setPosition(xx, yy);
        }
        Alien.setFleetXSpeed(fleetSpeed);
        
        // create and initialise the player's spaceship
        icon = new ImageIcon("C:\\player_ship.png");
        Image shipImage = icon.getImage();
        PlayerShip = new Spaceship(shipImage,shipImage);
        PlayerShip.setPosition(300,530);
        
        
        // tell all sprites the window width
        Sprite2D.setWinWidth(WindowSize.width);
        Sprite2D.setWinHeight(WindowSize.height);
        
        // create and start our animation thread
        Thread t = new Thread(this);
        t.start();
        
        // send keyboard events arriving into this JFrame back to its own event handlers
        addKeyListener(this);
        
        // initialise double-buffering
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        offscreenGraphics = strategy.getDrawGraphics();

        isInitialised = true;
    }
    
    // thread's entry point
    public void run() {
        while (gameRunning) {
            
            // 1: sleep for 1/50 sec
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) { }
            
            // 2: animate game objects
            //animating alien
            boolean alienDirectionReversalNeeded = false;
            for (Alien alien : AliensArray) {
                if (alien.move() && alien.isAlive()){
                    alienDirectionReversalNeeded=true;
                }
            }
            if (alienDirectionReversalNeeded) {
                Alien.reverseDirection();
                for (Alien alien : AliensArray) {
                    alien.jumpDownwards();
                }
            }
            //animating playership
            PlayerShip.move();
            //animating bullets
            Iterator<PlayerBullet> iterator = bulletsList.iterator();
            while (iterator.hasNext()) {
                PlayerBullet b = iterator.next();
                if (b.move()) {
                    iterator.remove(); // Remove bullet if it needs to disappear
                }
                for (Alien alien : AliensArray) {
                    if (b.isActive() && alien.isAlive() && checkCollision(b, alien)) {
                        b.setActive(false);
                        alien.setAlive(false);
                        score += 10;
                        System.out.println(score);
                    }
                }
            }
            
            for (Alien alien : AliensArray) {
                if (alien.isAlive() && checkCollision(PlayerShip, alien)) {
                    System.out.println("Game Over");
                    gameRunning = false;
                    //return;
                }
            }
            
            if (score % 300 == 0 && score != 0){
                fleetSpeed += 1;
                newWave();
            }
            
            // 3: force an application repaint
            this.repaint();
        }
    }
    
    // starts a new wave after all enemies are killed
    private void newWave(){
        for (int i=0; i<NUMALIENS; i++) {
            double xx = (i%5)*80 + 70;
            double yy = (i/5)*40 + 50; // integer division!
            AliensArray[i].setPosition(xx, yy);
        }
        //used this implementation due to static error
        for (Alien alien : AliensArray) {
            alien.setAlive(true);
        }
        Alien.setFleetXSpeed(fleetSpeed);
    }
    
    // detects collision between two objects based on the parameters below    
    private boolean checkCollision(Sprite2D sprite1, Sprite2D sprite2) {
        int x1 = (int) sprite1.getX();
        int y1 = (int) sprite1.getY();
        int w1 = sprite1.getWidth();
        int h1 = sprite1.getHeight();
        int x2 = (int) sprite2.getX();
        int y2 = (int) sprite2.getY();
        int w2 = sprite2.getWidth();
        int h2 = sprite2.getHeight();

        return ((x1 < x2 && x1 + w1 > x2) || (x2 < x1 && x2 + w2 > x1)) &&
               ((y1 < y2 && y1 + h1 > y2) || (y2 < y1 && y2 + h2 > y1));
    }
    
    // Three Keyboard Event-Handler functions
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_LEFT && gameRunning){
            PlayerShip.setXSpeed(-4);
        }else if (e.getKeyCode()==KeyEvent.VK_RIGHT && gameRunning){
            PlayerShip.setXSpeed(4);
        } else if (e.getKeyCode()==KeyEvent.VK_SPACE && gameRunning){
            shootBullet();
        } else if(e.getKeyCode()==KeyEvent.VK_ENTER && !gameRunning){
            //restarts game again, doesnt add anything, just changes everything back to initial values as if the game starts again, repaint and thread to ensure animation and paint start again
            gameRunning = true;
            for (int i=0; i<NUMALIENS; i++) {
                double xx = (i%5)*80 + 70;
                double yy = (i/5)*40 + 50; // integer division!
                AliensArray[i].setPosition(xx, yy);
            }
            for (Alien alien : AliensArray) {
                alien.setAlive(true);
            }
            score = 0;
            PlayerShip.setPosition(300,530);
            fleetSpeed = 4;
            Alien.setFleetXSpeed(fleetSpeed);
            
            Thread t = new Thread(this);
            t.start();
            repaint();
        }
    }
    
    public void keyReleased(KeyEvent e) {    
        if (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT){
            PlayerShip.setXSpeed(0);
        }
    }
    
    public void keyTyped(KeyEvent e) {
    }
    //
    
    public void shootBullet() {
        // add a new bullet to our list
        // create and initialise bullets
        ImageIcon icon = new ImageIcon("C:\\bullet.png");
        bulletImage = icon.getImage();
        PlayerBullet b = new PlayerBullet(bulletImage,bulletImage);
        b.setPosition(PlayerShip.x+54/2, PlayerShip.y);
        bulletsList.add(b);
    }

    
    // application's paint method
    public void paint(Graphics g) {
        if (!isInitialised){
            return;
        }
        g = offscreenGraphics;
        
        // canvas is filled according to the game state, when the game isnt running and when the game is running with the according info
        if(gameRunning){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WindowSize.width, WindowSize.height);
            //g.setColor(Color.WHITE);
            //g.drawString("Score: " + score, 50, 50);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font size to 20 (or any desired size)
            g.drawString("Score: " + score, 50, 50); // Draw the score string
        } 
        if(!gameRunning){
            //g.setColor(Color.RED);
            //g.fillRect(0, 0, WindowSize.width, WindowSize.height);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WindowSize.width, WindowSize.height);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Space Invaders", 100, 300);
            g.drawString("Press Enter to Start", 100, 400);
            
        }
        
        // redraw all game objects
        for (Alien alien : AliensArray) {
            if (alien.isAlive() && gameRunning){
                alien.paint(g);
            }
        }
        
        if(gameRunning){
            PlayerShip.paint(g);
        }
        
        Iterator iterator = bulletsList.iterator();
        while(iterator.hasNext() && gameRunning){
            PlayerBullet b = (PlayerBullet) iterator.next();
            if (b.isActive()){
                b.paint(g);
            }
        }
        
        
        // flip the buffers offscreen<-->onscreen
        strategy.show();
    }
    
    // application entry point
    public static void main(String[] args) {
        workingDirectory = System.getProperty("user.dir");
        System.out.println("Working Directory = " + workingDirectory);
        Main w = new Main();
    }
}

