package org.game.engine.test;

import org.game.engine.SwingGame;
import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;
import org.game.projectlib.ThreadTools;

public class Test extends SwingGame {
    volatile double w;
    volatile boolean l, r;
    protected Test() {
        super(10, 10, 500, 500, "DEBUG");
        w = 0;

        getWindow().setKeyAction("d", null, () -> {
            w -= 0.15;
            l = true;
        }, () -> {
            l = false;
        });
        getWindow().setKeyAction("lctrl", null, () -> {
            w += 0.1;
            r = true;
        }, () -> {
            r = false;
        });
    }

    @Override
    public void render(Image frameBuffer) {
        frameBuffer.clear();
        frameBuffer.getGraphicsContext().fill(new Vector(0, 0), new Vector((int) w,500), new Color(255, 255, 255));
        frameBuffer.getGraphicsContext().print(new Vector(10, 20), String.valueOf(getFPS()));
        if(l) {
            frameBuffer.getGraphicsContext().fill(new Vector(0, 0), new Vector(10, 10), new Color(255, 0, 0));
        }
        if(r) {
            frameBuffer.getGraphicsContext().fill(new Vector(10, 0), new Vector(10, 10), new Color(0, 0, 255));
        }
        frameBuffer.getGraphicsContext().fill(new Vector(150, 150), new Vector(50,50), new Color(0, 255, 255));
    }

    public static void main(String[] args) {
        new Test();
    }
}