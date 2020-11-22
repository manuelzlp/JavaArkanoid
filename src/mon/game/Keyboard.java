package mon.game;

public class Keyboard {

    // Keyboard information
    public static boolean[] keys = new boolean[256];
    public static Integer[] keyLastFrame = new Integer[256];
    public static Integer keyboardDelay = 0;

    public static boolean keyDown(int key) {
        return keys[key];
    }
    
}
