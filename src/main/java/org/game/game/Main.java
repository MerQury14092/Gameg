package org.game.game;

import org.game.engine.SwingGame;
import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;

public class Main extends SwingGame {
    public Main() {
        super(10, 10, 500, 500, "Hello Game!");
    }

    @Override
    public void render(Image frameBuffer) {
        frameBuffer.getGraphicsContext().fill(new Vector(0,0), new Vector(500,500), new Color(255, 255, 255));
        frameBuffer.getGraphicsContext().print(new Vector(220, 220), "Hello world!");
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}