package mon.game;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class GDriver extends JPanel {

    BufferedImage buffer;
    Graphics b;

    GDriver() {
        this.buffer = new BufferedImage(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        // Define buffer graphics
        this.b = buffer.getGraphics();
    }

    @Override
     public void paintComponent(Graphics g) {

        // Check background

        if (Background.active) {
            // Draw background and check scroll

            if (Background.scrolling) {
                // Do scroll
                Background.x += Background.scrollHSpeed;
                Background.y += Background.scrollVSpeed;
            }

            Integer w = Background.w;
            Integer h = Background.h;

            // If out of bounds then reset
            if (Background.x > w)
                Background.x = 0;
            
            if (Background.y > h)
                Background.y = 0;

            // Draw main background image
            b.drawImage(Background.image, Background.x, Background.y, null);

            // Check all possible angles

            // top
            if (Background.y > 0) {
                b.drawImage(Background.image, Background.x, Background.y - h, null);
            }

            // bottom
            if (Background.y < 0) {
                b.drawImage(Background.image, Background.x, Background.y + h, null);
            }

            // left
            if (Background.x > 0) {
                b.drawImage(Background.image, Background.x - w, Background.y, null);
            }

            // right
            if (Background.x < 0) {
                b.drawImage(Background.image, Background.x + w, Background.y, null);
            }

            // top left
            if (Background.x > 0 && Background.y > 0) {
                b.drawImage(Background.image, Background.x - w, Background.y - h, null);
            }

            // top right
            if (Background.x < 0 && Background.y > 0) {
                b.drawImage(Background.image, Background.x + w, Background.y - h, null);
            }

            // bottom left
            if (Background.x > 0 && Background.y < 0) {
                b.drawImage(Background.image, Background.x - w, Background.y + h, null);
            }

            // bottom right
            if (Background.x < 0 && Background.y < 0) {
                b.drawImage(Background.image, Background.x + w, Background.y + h, null);
            }

        } else {
            // Clear buffer
            b.setColor(Config.BACKGROUND_COLOR);
            b.fillRect(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        }

        // Draw images
        Game.entities.forEach((e) -> {
            // Check if entity is visible then get current sprite
            if (e.visible) {
                if (e.type == "image") {
                    // update animation
                    if (e.sprite.isPlaying()) {
                        if (e.sprite.animationLastGameFrame + e.sprite.speed < Game.currentFrame) {
                            // Update frame
                            e.sprite.animationLastGameFrame = Game.currentFrame;
                            e.sprite.currentFrame = e.sprite.currentFrame + 1;
                            // Check if frame is over limit
                            if (e.sprite.currentFrame * e.sprite.tilesW >= e.sprite.image.getWidth()) {
                                // If is looping then go back zero
                                if (e.sprite.loop) {
                                    e.sprite.currentFrame = 0;
                                } else {
                                    // Stop at last frame
                                    e.sprite.currentFrame = e.sprite.currentFrame-1;
                                    e.sprite.stopAnimation();
                                }
                            }

                        }
                    }

                    b.drawImage(e.sprite.getCurrentImage(), e.x, e.y, null);
                } else if (e.type == "text") {
                    b.setColor(e.color);
                    Font f = new Font("Trebuchet MS", Font.PLAIN, e.size);
                    b.setFont(f);
                    if (e.centered)
                        b.drawString(e.text, (Config.WINDOW_WIDTH / 2) - (b.getFontMetrics().stringWidth(e.text) / 2), e.y);
                    else
                        b.drawString(e.text, e.x, e.y);
                }
            }
        });

        // Swap buffer
        g.drawImage(buffer, Game.buffer_x, Game.buffer_y, Game.buffer_width, Game.buffer_height, Config.BACKGROUND_COLOR, null);
        Game.repaintDone = true;

     }

 }
