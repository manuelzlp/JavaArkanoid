package mon.game;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Sprite { 
    
    public Integer speed = 0;
    Integer w;
    Integer h;
    Integer tilesY;
    public BufferedImage image;
    String img_folder = Config.RESOURCES_DIR + "/img/";
    boolean playAnimation = false;
    public Integer animationLastGameFrame;
    public boolean loop = true;
    Integer transparentColor = -1;

    public Integer currentAnimation = 0;
    public Integer currentFrame = 0;

    Integer tilesW;
    Integer tilesH;

    Sprite(String image_location, Integer tilesW, Integer tilesH) {

        File imgFile = new File(img_folder + image_location);

        try {
            this.image = ImageIO.read(imgFile);
            this.w = this.image.getWidth();
            this.h = this.image.getHeight();

            if (tilesW.equals(0))
                tilesW = this.w;

            if (tilesH.equals(0))
                tilesH = this.h;

            this.tilesW = tilesW;
            this.tilesH = tilesH;

        } catch (IOException e) {
            System.out.println(imgFile);
            System.out.println("error");
        }

    }

    Sprite(BufferedImage image, Integer tilesW, Integer tilesH) {

        this.image = image;
        this.w = this.image.getWidth();
        this.h = this.image.getHeight();

        if (tilesW.equals(0))
            tilesW = this.w;

        if (tilesH.equals(0))
            tilesH = this.h;

        this.tilesW = tilesW;
        this.tilesH = tilesH;

    }

    public void setTransparent() {

        // Make transparent with first pixel

        BufferedImage newImg = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TRANSLUCENT);

        Integer transparentColor;

        if (this.transparentColor.equals(-1))
            transparentColor = this.image.getRGB(0, 0);
        else
            transparentColor = this.transparentColor;
            
        for (int x=0;x<this.image.getWidth();x++) {
            for (int y=0;y<this.image.getHeight();y++) {
                int pixel = this.image.getRGB(x, y);
                if (pixel != transparentColor)
                    newImg.setRGB(x, y, pixel);
            }
        }

        this.image = newImg;

    }

    public void startAnimation() {
        this.animationLastGameFrame = Game.currentFrame;
        this.playAnimation = true;
    }

    public void stopAnimation() {
        this.playAnimation = false;
    }

    public boolean isPlaying() {
        return this.playAnimation;
    }

    public void resetAnimation() {
        this.currentFrame = 0;
    }

    public BufferedImage getCurrentImage() {
        return this.image.getSubimage(this.currentFrame * tilesW, this.currentAnimation * tilesH, tilesW, tilesH);
    }

}
