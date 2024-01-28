package org.game.engine;

import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.graphics.Window;
import org.game.engine.protocol.math.Vector;
import org.game.engine.swingengine.graphics.WindowImpl;

public abstract class SwingGame implements Game{
    private final Window window;
    private volatile int currentFramePerSecond, fps;
    protected SwingGame(int x, int y, int width, int height, String name){
        currentFramePerSecond = 0;
        window = new WindowImpl(new Vector(x, y), new Vector(width, height), name);
        window.setResizable(false);

        Thread fpsUpdater = new Thread(() -> {
            for (;;){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                fps = currentFramePerSecond;
                currentFramePerSecond = 0;
            }
        });
        fpsUpdater.setDaemon(true);
        fpsUpdater.start();
        Thread renderThread = new Thread(() -> {
            for (;;){
                render(window.getContent());
                currentFramePerSecond += 1;
            }
        });
        renderThread.setDaemon(true);
        renderThread.start();
        System.out.println("render thread started");
    }

    public abstract void render(Image frameBuffer);
    public int getFPS(){
        return fps;
    }
    public Window getWindow(){
        return window;
    }
}
