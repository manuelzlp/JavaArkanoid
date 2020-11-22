import mon.game.*;

public class App {
    public static void main(String[] args) throws Exception {

        
        Config.WINDOW_WIDTH  = 800;
        Config.WINDOW_HEIGHT = 600;

        //Game.setFullscreen(true);

        // Load sounds
        Sound.loadSound("hit.wav");
        Sound.loadMusic("song.wav");

        // Disable sound
        //Config.SOUND_ENABLED = false;

        Game.setState("gameLoop", true);

        Loop loop = new Loop();

        loop.run();

    }
}
