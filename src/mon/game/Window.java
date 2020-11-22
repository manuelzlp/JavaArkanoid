package mon.game;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.Rectangle;

public class Window extends JFrame implements KeyListener {
    
    public static GDriver g = new GDriver();

    public void keyPressed(KeyEvent e) {

        int k = e.getKeyCode();

        if (Keyboard.keyLastFrame[k] == null)
        Keyboard.keyLastFrame[k] = -Keyboard.keyboardDelay; // First time pressed

        if (Keyboard.keyLastFrame[k] + Keyboard.keyboardDelay <= Game.currentFrame) {
            Keyboard.keys[k] = true;
            Keyboard.keyLastFrame[k] = Game.currentFrame;
        } else {
            Keyboard.keys[k] = false;
        }

    }

    public void keyReleased(KeyEvent e) {
        Keyboard.keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {

    }

    public Window() {
        g = new GDriver();
        this.setBounds(new Rectangle(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g.setDoubleBuffered(false);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.getContentPane().add(this.g);
        this.setVisible(true);
    }

}
