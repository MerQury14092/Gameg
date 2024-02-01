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
        System.setProperty("sun.java2d.opengl", "true");
        load();
        currentFramePerSecond = 0;
        window = new WindowImpl(new Vector(x, y), new Vector(width, height), name);
        window.setResizable(false);
        frameBuffer = new BufferedImage(new Vector(width, height));

        ThreadTools.runDaemon("fpsUpdater", () -> {
            for (;;){
                ThreadTools.sleep(125);
                fps = currentFramePerSecond*8;
                currentFramePerSecond = 0;
            }
        });
        ThreadTools.runDaemon("renderThread", () -> {
            for (;;){
                render(frameBuffer);
                window.getContent().getGraphicsContext().draw(new Vector(0, 0), frameBuffer);
                currentFramePerSecond += 1;
            }
        });
    }

    @Override
    public void load() {}

    public int getFPS(){
        return fps;
    }
    public Window getWindow(){
        return window;
    }
}
