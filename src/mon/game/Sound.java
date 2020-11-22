package mon.game;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    // Music clip
    static Clip music = null;

    static Vector<Clip> fx = new Vector<Clip>();

    static String sound_folder = Config.RESOURCES_DIR + "/sound/";

    public static void loadMusic(String filename) {

        File audioFile = new File(sound_folder + filename);

        AudioInputStream audioStream = null;

        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            
        }

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format);

        try {
            music = (Clip) AudioSystem.getLine(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            music.open(audioStream);
        } catch (Exception e) {

        }

    }

    public static void playMusic() {
        
        if (!Config.SOUND_ENABLED)
            return;

        if (!music.isActive()) {
            music.start();
            music.loop(-1);
        }
    }

    public static void stopMusic() {
        if (music.isActive())
        music.stop();
    }

    public static boolean isMusicPlaying() {
        return music.isActive();
    }

    /**
     * FX Sounds
     */

    public static int loadSound(String filename) {

        // Load the audio file
        File audioFile = new File(sound_folder + filename);

        AudioInputStream audioStream = null;

        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            
        }

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format);

        Clip newFx = null;

        try {
            newFx = (Clip) AudioSystem.getLine(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            newFx.open(audioStream);
        } catch (Exception e) {

        }

        fx.add(newFx);

        return fx.size() - 1;
    }

    public static void playSound(int id) { // Will override sound

        if (!Config.SOUND_ENABLED)
            return;

        if (fx.get(id).isRunning())
            fx.get(id).stop();
        fx.get(id).setMicrosecondPosition(0);
        fx.get(id).start();
    }

    public static void playSound(int id, boolean override) { // Will override sound

        if (!Config.SOUND_ENABLED)
            return;

        if (override) {
            if (fx.get(id).isRunning())
                fx.get(id).stop();
            fx.get(id).setMicrosecondPosition(0);
            fx.get(id).start();
        } else {
            if (!fx.get(id).isRunning()) {
                fx.get(id).setMicrosecondPosition(0);
                fx.get(id).start();
            }
        }
    }
        
}
