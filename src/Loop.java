import mon.game.Background;
import mon.game.Config;
import mon.game.Game;
import mon.game.GameLoop;
import mon.game.Keyboard;
import mon.game.Sound;
import mon.game.Entity;
import mon.game.Sprite;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class Loop extends GameLoop {

    static String action;

    public void loop(String state) {

        switch (state) {

            case "mainMenu":

                break;

            case "gameLoop":
                gameLoop();
                break;

        }

        // General keys

        if (Keyboard.keyDown(KeyEvent.VK_ESCAPE))
            System.exit(0);

    }

    // VARIABLES

    boolean ballGoLeft;
    boolean ballGoUp = true;
    int ballSpeed = 5;
    int playerSpeed = 5;
    Integer level_bricks;
    Integer bricks_broken;
    public static Vector<Integer> bricks_id = new Vector<Integer>();

    Entity player1 = new Entity();
    Entity ball = new Entity();

    // Text
    Entity levelText = new Entity();
    Entity pressSpace = new Entity();
    Entity livesLeft = new Entity();
    Entity gameOverText = new Entity();
    Entity lifeLost = new Entity();
    

    // STATES

    void gameLoop() {

        if (!Game.isStateReady()) {
            Game.setStateReady();

            Game.entities.clear();

            // Load background
            Background.setBackground("bg.jpg");
            Background.setScroll(1, 1);
            Background.startScroll();

            // Play music
            if (!Sound.isMusicPlaying())
                Sound.playMusic();

            player1.setSprite("player.bmp");
            player1.setVisible(true);
            player1.x = 350;
            player1.y = 550;
            player1.sprite.setTransparent();

            Game.entities.add(player1);

            // Ball
            ball.setSprite("ball.bmp");
            ball.x = 390;
            ball.y = 530;
            ball.sprite.setTransparent();
            ball.setVisible(true);

            Game.entities.add(ball);

            Arkanoid.currentLevel = 1;
            Arkanoid.lives = 3;

            // Clear level
            bricks_id.clear();

            // Write text
            levelText.y = 330;
            levelText.setText("Current level: " + Arkanoid.currentLevel, Color.white, 25, true);
            Game.entities.add(levelText);

            pressSpace.y = 450;
            pressSpace.setText("Press SPACEBAR to start!", Color.white, 25, true);
            Game.entities.add(pressSpace);

            gameOverText.y = 200;
            gameOverText.setText("GAME OVER!! Press SPACEBAR to start again!", Color.white, 25, true);
            Game.entities.add(gameOverText);

            livesLeft.y = 200;
            livesLeft.setText("Lives left: " + Arkanoid.lives, Color.white, 25, true);
            Game.entities.add(livesLeft); 
            
            lifeLost.y = 400;
            lifeLost.setText("You lost a life!", Color.white, 30, true);
            Game.entities.add(lifeLost);

            action = "waiting start";
        }

        switch (action) {

            case "waiting start":

                gameOverText.setVisible(false);
                lifeLost.setVisible(false);
                livesLeft.setVisible(true);
                levelText.setVisible(true);
                pressSpace.setVisible(true);

                // Reset ball
                ball.x = 390;
                ball.y = 530;
                // Reset player
                player1.x = 350;
                player1.y = 550;
                ballGoLeft = false;
                ballGoUp = true;

                if (bricks_id.size() < 1) {
                    Brick yellowBrick = new Brick();
                    // Check if we need to load the level
                    switch (Arkanoid.currentLevel) {

                        case 1:
       
                            for (int x = 1; x < 7; x++) {
                                for (int y = 0; y < 5; y++) {
                                    int tileX = x * 100;
                                    int tileY = y * 25;
        
                                    Entity newBrick = new Entity();
        
                                    newBrick.setSprite(yellowBrick.sprite.image);
                                    newBrick.setVisible(true);
        
                                    newBrick.x = tileX;
                                    newBrick.y = tileY;
        
                                    Game.entities.add(newBrick);
                                    bricks_id.add(Game.entities.size() - 1);
        
                                }
                            }
       
                            break;
        
                        case 2:

                            for (int x = 1; x < 7; x+=2) {
                                for (int y = 0; y < 7; y+=2) {
                                    int tileX = x * 100;
                                    int tileY = y * 25;
        
                                    Entity newBrick = new Entity();
        
                                    newBrick.setSprite(yellowBrick.sprite.image);
                                    newBrick.setVisible(true);
        
                                    newBrick.x = tileX;
                                    newBrick.y = tileY;
        
                                    Game.entities.add(newBrick);
                                    bricks_id.add(Game.entities.size() - 1);
        
                                }
                            }
        
                            break;
        
                        case 3:

                            for (int x = 0; x < 8; x++) {
                                for (int y = 0; y < 7; y+=2) {
                                    int tileX = x * 100;
                                    int tileY = y * 25;
        
                                    Entity newBrick = new Entity();
        
                                    newBrick.setSprite(yellowBrick.sprite.image);
                                    newBrick.setVisible(true);
        
                                    newBrick.x = tileX;
                                    newBrick.y = tileY;
        
                                    Game.entities.add(newBrick);
                                    bricks_id.add(Game.entities.size() - 1);
        
                                }
                            }
        
                            break;
        
                    }

                    level_bricks = bricks_id.size();
                    bricks_broken = 0;
                } // End level creation

                if (Keyboard.keys[KeyEvent.VK_SPACE]) {
                    ball.sprite.startAnimation();
                    action = "playing";
                }
                break;

            case "playing":

                livesLeft.setVisible(false);
                levelText.setVisible(false);
                pressSpace.setVisible(false);

                // Check if level is finished

                if (bricks_broken.equals(level_bricks)) {
                    if (Config.DEBUG)
                        System.out.println("Level completed");
                    action = "levelCompleted";
                    break;
                }

                // Ball bounce against bricks
                for (int i = 0; i < bricks_id.size(); i++) {

                    Entity currentBrick = Game.entities.get(bricks_id.get(i));

                    if (!currentBrick.isVisible())
                        continue;

                    if (ball.isTouching(currentBrick)) {

                        currentBrick.setVisible(false);
                        bricks_broken++;

                        if (!ballGoUp)
                            ballGoLeft = true;
                        ballGoUp = !ballGoUp;

                        Sound.playSound(0, false);

                        break;
                    }
                }

                // Ball bounce against player
                if (player1.isTouching(ball) && ballGoUp == false) {

                    ballGoUp = true;

                    if (Keyboard.keys[KeyEvent.VK_LEFT])
                        ballGoLeft = true;

                    if (Keyboard.keys[KeyEvent.VK_RIGHT])
                        ballGoLeft = false;

                }

                // Bounce against walls
                if (ball.x <= 0)
                    ballGoLeft = false;

                if (ball.x >= 780)
                    ballGoLeft = true;

                if (ball.y <= 0)
                    ballGoUp = false;

                // Life lost
                if (ball.y >= 580) {
                    action = "lifeLost";
                    lifeLost.setVisible(true);
                }

                // Move ball
                if (ballGoUp)
                    ball.y -= ballSpeed;
                else
                    ball.y += ballSpeed;

                if (ballGoLeft)
                    ball.x -= ballSpeed;
                else
                    ball.x += ballSpeed;

                // Set scroll to zero
                Background.setScroll(1, 1);

                // Keys
                if (Keyboard.keys[KeyEvent.VK_LEFT] && player1.x >= 0) {
                    Background.setScroll(2, 1);
                    player1.x -= playerSpeed;
                }

                if (Keyboard.keys[KeyEvent.VK_RIGHT] && player1.x <= 700) {
                    Background.setScroll(-1, 1);
                    player1.x += playerSpeed;
                }
                break;

            case "lifeLost":
        
                Arkanoid.lives--;
                livesLeft.setText("Lives left: " + Arkanoid.lives, Color.white, 25, true);
                if (Config.DEBUG)
                    System.out.println("Lives left: " + Arkanoid.lives);

                if (Arkanoid.lives.equals(-1)) {
                    action = "gameOver";
                } else {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {

                    }
                    action = "waiting start";
                }
            break;

            case "levelCompleted":
            Arkanoid.currentLevel++;
            bricks_id.clear();
            levelText.setText("Current level: " + Arkanoid.currentLevel, Color.white, 25, true);
            if (Config.DEBUG)
                System.out.println("Level completed. Next level: " + Arkanoid.currentLevel);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {

            }

            if (Arkanoid.currentLevel < 4) {
                action = "waiting start";
            } else {
                action = "gameOver";
            }
            break;

           case "gameOver":
                gameOverText.setVisible(true);
                if (Keyboard.keys[KeyEvent.VK_SPACE]) {
                    Game.stateReady = false; // Reset game
                }
           break; 

        }
        
    }

}
