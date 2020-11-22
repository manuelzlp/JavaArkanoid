package mon.game;

public abstract class GameLoop {

    final static long fpsMs = 1000 / Config.FPS;

    public abstract void loop(String state);

    public void run() throws InterruptedException {

        // Game loop
        
        long startLoopMs;
        long endLoopMs;

        while (true) {
            startLoopMs = System.currentTimeMillis();
            Game.currentFrame++;

            this.loop(Game.currentState);

            Game.repaintDone = false;
            Game.window.g.repaint(); // Redraw graphics  
                       
            endLoopMs = System.currentTimeMillis() - startLoopMs;

            // If faster than expected then wait
            if (endLoopMs < fpsMs)
                Thread.sleep(fpsMs - endLoopMs);
        }

    }
        
}
