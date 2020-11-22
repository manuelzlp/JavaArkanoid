package mon.game;

import java.util.Vector;
import java.awt.Color;

public class Game {
    
    public static Integer currentFrame = 0;
    public static String currentState = "start";
    public static boolean stateReady = false;
    public static Window window = new Window();
    public static Integer buffer_x = 0;
    public static Integer buffer_y = 0;
    public static Integer buffer_width = Config.WINDOW_WIDTH;
    public static Integer buffer_height = Config.WINDOW_HEIGHT;
    public static boolean repaintDone = false;

    public static Vector<Entity> entities = new Vector<Entity>();

    public static Vector<Integer> global = new Vector<Integer>();

    public static boolean fullscreen = false;

    // Set full screen

    public static void setFullscreen(boolean state) {

        window.setBackground(Color.black);

        fullscreen = state;

        Integer screenW = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        Integer screenH = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        if (state) {
            Game.window.setBounds(0, 0, screenW, screenH);

            float radius = screenW / (float) Config.WINDOW_WIDTH;

            if (Config.WINDOW_HEIGHT * radius > screenH) {
                radius = screenH / (float) Config.WINDOW_HEIGHT;
            }

            Game.buffer_width = (int) (Config.WINDOW_WIDTH * radius);
            Game.buffer_height = (int) (Config.WINDOW_HEIGHT * radius);

            Game.buffer_x = (screenW - Game.buffer_width) / 2;
            Game.buffer_y = (screenH - Game.buffer_height) / 2;
        } else {
            Game.window.setBounds((int) (screenW - Config.WINDOW_WIDTH) / 2, (int) (screenH - Config.WINDOW_HEIGHT) / 2, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
            Game.buffer_x = 0;
            Game.buffer_y = 0;
            Game.buffer_width = Config.WINDOW_WIDTH;
            Game.buffer_height = Config.WINDOW_HEIGHT;
        }

    }

    // Manage state

    public static void setState(String state) {
        currentState = state;

        stateReady = false;
    }

    public static void setState(String state, boolean reset) {
        currentState = state;

        if (reset)
            stateReady = false;

    }

    public static String getState() {
        return currentState;
    }

    public static boolean isStateReady() {
        return stateReady;
    }

    public static void setStateReady() {
        stateReady = true;
    }

}