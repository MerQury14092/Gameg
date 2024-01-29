package org.game.engine;

import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.graphics.Window;
import org.game.engine.protocol.math.Vector;
import org.game.engine.swingengine.graphics.BufferedImage;
import org.game.engine.swingengine.graphics.WindowImpl;
import org.game.projectlib.ThreadTools;

public abstract class SwingGame implements Game{
    private final Window window;
    private final Image frameBuffer;
    private volatile int currentFramePerSecond, fps;
    protected SwingGame(int x, int y, int width, int height, String name){
        currentFramePerSecond = 0;
        window = new WindowImpl(new Vector(x, y), new Vector(width, height), name);
        window.setResizable(false);
        frameBuffer = new BufferedImage(new Vector(width, height));

        Thread fpsUpdater = new Thread(() -> {
            for (;;){
                ThreadTools.sleep(1000);
                fps = currentFramePerSecond;
                currentFramePerSecond = 0;
            }
        });
        fpsUpdater.setDaemon(true);
        fpsUpdater.start();
        Thread renderThread = new Thread(() -> {
            for (;;){
                render(frameBuffer);
                window.getContent().getGraphicsContext().draw(new Vector(0, 0), frameBuffer);
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
