package org.game.engine.test;

import org.game.engine.SwingGame;
import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;
import org.game.engine.swingengine.graphics.BufferedImage;
import org.game.projectlib.ThreadTools;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test extends SwingGame {
    volatile double x, y;
    Image img;
    Image[] images;

    @Override
    public void load() {
        try {
            img = new BufferedImage(ImageIO.read(Test.class.getResourceAsStream("/apple.png")));
            images = Stream.generate(() -> img.clone()).limit(50).toList().toArray(new Image[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Test() {
        super(10, 10, 1920, 1080, "DEBUG");
        x = 0; y = 0;
        getWindow().setKeyAction("w", null, () -> y-=0.1, null);
        getWindow().setKeyAction("a", null, () -> x-=0.1, null);
        getWindow().setKeyAction("s", null, () -> y+=0.1, null);
        getWindow().setKeyAction("d", null, () -> x+=0.1, null);
    }

    @Override
    public void render(Image frameBuffer) {
        frameBuffer.clear();
        frameBuffer.getGraphicsContext().fill(new Vector(0, 0), new Vector(160, 30), new Color(255,255,255));
        frameBuffer.getGraphicsContext().print(new Vector(10, 20), String.valueOf(getFPS()));

    }

    public static void main(String[] args) {
        new Test();
    }
}