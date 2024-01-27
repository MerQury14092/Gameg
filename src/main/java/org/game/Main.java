package org.game;

import org.game.engine.Game;
import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;

public class Main extends Game {
    volatile double w;
    volatile boolean l, r;
    protected Main() {
        super(10, 10, 500, 500, "Hello!");
        w = 0;

        getWindow().setKeyListener("d", null, () -> {
            w -= 0.15;
            l = true;
        }, null);
        getWindow().setKeyListener("lctrl", null, () -> {
            w += 0.1;
            r = true;
        }, null);
    }

    @Override
    protected void render(Image frameBuffer) {
        frameBuffer.clear();
        frameBuffer.getGraphicsContext().fill(new Vector(0, 0), new Vector((int) w,500), new Color(255, 255, 255));
        frameBuffer.getGraphicsContext().print(new Vector(10, 20), String.valueOf(getFPS()));
        if(l) {
            frameBuffer.getGraphicsContext().fill(new Vector(0, 0), new Vector(10, 10), new Color(255, 0, 0));
            l = false;
        }
        if(r) {
            frameBuffer.getGraphicsContext().fill(new Vector(10, 0), new Vector(10, 10), new Color(0, 0, 255));
            r = false;
        }
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}