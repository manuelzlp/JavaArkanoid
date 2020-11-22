package mon.game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Entity {
    
    Rectangle hitbox;
    public boolean solid;
    public Integer x;
    public Integer y;
    public Sprite sprite;
    Integer imgId = 0;
    boolean visible = false;
    public Integer w;
    public Integer h;

    public String type = "image";

    public Integer hitbox_x = 0;
    public Integer hitbox_y = 0;
    public Integer hitbox_w = 0;
    public Integer hitbox_h = 0;

    // Text entity
    public String text;
    public Color color;
    public Integer size;
    boolean centered;

    public void setSprite(String image, int tilesW, int tilesH) {
        this.sprite = new Sprite(image, tilesW, tilesH);
        this.w = this.sprite.w;
        this.h = this.sprite.h;

        this.type = "image";

        // set hitbox
        this.hitbox_w = this.w / tilesW;
        this.hitbox_h = this.h / tilesH;
    }

    public void setSprite(String image) {
        this.sprite = new Sprite(image, 0, 0);
        this.w = this.sprite.w;
        this.h = this.sprite.h;

        // set hitbox
        this.hitbox_w = this.w;
        this.hitbox_h = this.h;
    }

    public void setSprite(BufferedImage image, int tilesW, int tilesH) {
        this.sprite = new Sprite(image, tilesW, tilesH);
        this.w = this.sprite.w;
        this.h = this.sprite.h;

        // set hitbox
        this.hitbox_w = this.w / tilesW;
        this.hitbox_h = this.h / tilesH;
    }

    public void setSprite(BufferedImage image) {
        this.sprite = new Sprite(image, 0, 0);
        this.w = this.sprite.w;
        this.h = this.sprite.h;

        // set hitbox
        this.hitbox_w = this.w;
        this.hitbox_h = this.h;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean state) {
        if (state) {          
            this.visible = true;
        } else {
            this.visible = false;
        }
    }

    // Collision

    public boolean isTouching(Entity object) {

        Rectangle myHitbox = new Rectangle(this.x + this.hitbox_x, this.y + this.hitbox_y, this.hitbox_w, this.hitbox_h);

        Rectangle otherHitbox = new Rectangle(object.x + object.hitbox_x, object.y + object.hitbox_y, object.hitbox_w, object.hitbox_h);

        return myHitbox.intersects(otherHitbox);
    }

    // Text entity

    public void setText(String text, Color color, Integer size, boolean centered) {
        this.type = "text";
        this.text = text;
        this.color = color;
        this.size = size;
        this.centered = centered;
    }

}
