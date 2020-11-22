package mon.game;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Background {

    static String bg_folder = Config.RESOURCES_DIR + "/background/";
    static Integer scrollVSpeed = 0;
    static Integer scrollHSpeed = 0;
    static BufferedImage image;
    public static Integer w;
    public static Integer h;
    static Integer x = 0;
    static Integer y = 0;
    static Integer scrollLastFrame = 0;
    static boolean scrolling = false;
    static boolean active = false;

    public static void setBackground(String image_location) {

        File imgFile = new File(bg_folder + image_location);
       
        try {
            image = ImageIO.read(imgFile);
            w = image.getWidth();
            h = image.getHeight();
        } catch(IOException er) {

        }

        active = true;

    }
    
    public static void setScroll(int hSpeed, int vSpeed) {

        scrollVSpeed = vSpeed;
        scrollHSpeed = hSpeed;

    }

    public static void startScroll() {       
        scrolling = true;
    }

    public static void stopScroll() {
        scrolling = false;
    }

}
