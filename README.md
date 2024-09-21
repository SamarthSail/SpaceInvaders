# Space Invaders Game

Welcome to Space Invaders, a simple 2D shooter game based on the classic arcade game. In this project, you will control a spaceship to shoot down alien invaders as they descend upon Earth. The game features smooth animations, bullet mechanics, and increasing difficulty as the player progresses through the waves. This project is meant to demonstrate good coding practices using efficient OOP principles, ex: Encapsulation, Inheritance, Polymorphism, etc; Threading and Double Buffering for smooth game loop and animations; and effective use of Array and ArrayList data structures, along with other common key coding and design principles.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Installation and Setup](#installation-and-setup)
- [Game Controls](#game-controls)
- [How to Play](#how-to-play)

## Features
- **Smooth animations**: The game features animated alien sprites and a moving player ship.
- **Dynamic difficulty**: The game becomes harder as the player scores higher.
- **Collision detection**: Bullets can destroy aliens, and aliens colliding with the spaceship will end the game.
- **Basic scoreboard**: Your score increases as you destroy alien ships.

## Project Structure

The project is divided into the following classes:

### 1. `Main`
The main game loop is handled in the `Main` class. It extends `JFrame` and implements `Runnable` and `KeyListener` for handling keyboard inputs and game logic.

**Key methods:**
- `run()`: The core game loop responsible for updating the game state.
- `paint(Graphics g)`: Renders the game graphics including the spaceship, aliens, and bullets.
- `keyPressed(KeyEvent e)`: Handles player input such as movement and shooting.
- `newWave()`: Resets and spawns a new wave of aliens when the previous wave is defeated.

### 2. `Alien`
Represents the alien invaders in the game. Aliens move across the screen and drop down as they reach the edges.

**Key methods:**
- `move()`: Controls the horizontal movement of the alien fleet.
- `jumpDownwards()`: Causes the aliens to drop down when they hit the screen edge.
- `setAlive(boolean alive)`: Used to set whether the alien is "alive" or "dead."

### 3. `Spaceship`
Represents the player's spaceship. The player controls the spaceship's horizontal movement and shooting.

**Key methods:**
- `move()`: Updates the spaceship's position based on player input.
- `setXSpeed(double dx)`: Adjusts the horizontal speed of the spaceship based on keyboard input.

### 4. `PlayerBullet`
Represents the bullets fired by the spaceship. Bullets move upwards on the screen and destroy aliens on contact.

**Key methods:**
- `move()`: Moves the bullet upwards and deactivates it when it leaves the screen.
- `isActive()`: Returns whether the bullet is still active on the screen.

### 5. `Sprite2D`
An abstract base class used for all 2D sprites, such as the spaceship, aliens, and bullets.

**Key methods:**
- `paint(Graphics g)`: Handles drawing the sprite with simple animation.
- `setPosition(double xx, double yy)`: Sets the initial position of the sprite.
- `getX()` and `getY()`: Returns the current position of the sprite.
- `getWidth()` and `getHeight()`: Returns the dimensions of the sprite.

## Installation and Setup

Follow these steps to set up and run the game on your local machine:

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/space-invaders-game.git
    ```

2. Navigate to the project directory:

    ```bash
    cd space-invaders-game
    ```

3. Compile the project: Compile the Java files using a Java compiler or your preferred IDE (e.g., IntelliJ IDEA or Eclipse).

4. Run the game: Run the `Main` class as a Java application.

## Game Controls

- **Left Arrow**: Move the spaceship left.
- **Right Arrow**: Move the spaceship right.
- **Spacebar**: Shoot bullets from the spaceship.
- **Enter**: Start or restart the game.

## How to Play

1. **Start the game**: Press `Enter` to start the game.
2. **Move the spaceship**: Use the `Left` and `Right` arrow keys to move your spaceship across the bottom of the screen.
3. **Shoot aliens**: Press the `Spacebar` to fire bullets upwards and destroy the invading aliens.
4. **Avoid collision**: If any alien reaches your spaceship or if the alien fleet drops to the bottom of the screen, the game ends.
5. **Score points**: Earn 10 points for each alien destroyed. After each wave of 30 aliens, a new wave appears with increasing speed.
