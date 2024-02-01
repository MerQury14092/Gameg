package org.game.engine.swingengine.graphics;

import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.ComplexImage;
import org.game.engine.protocol.graphics.GraphicsContext;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;
import org.game.projectlib.ThreadTools;

public class ComplexBufferedImage implements ComplexImage {
    BufferedImage res;

    public ComplexBufferedImage(BufferedImage... images){
        if (images.length == 0) {
            res = new BufferedImage(new Vector(1, 1));
            return;
        }
        res = (BufferedImage) images[0].clone();
        ThreadTools.runAsyncTask(() -> {
            var g2d = res.getGraphicsContext();
            for (int i = 1; i < images.length; i++) {
                g2d.draw(new Vector(0, 0), images[i]);
            }
        });

    }

    @Override
    public Image getResult() {
        return res;
    }

    @Override
    public void setPixel(Color color, Vector pos) {
        res.setPixel(color, pos);
    }

    @Override
    public Color getPixel(Vector pos) {
        return res.getPixel(pos);
    }

    @Override
    public Vector getSize() {
        return res.getSize();
    }

    @Override
    public void clear() {
        res.clear();
    }

    @Override
    public GraphicsContext getGraphicsContext() {
        return res.getGraphicsContext();
    }

    @Override
    public Image clone() {
        return res.clone();
    }
}
