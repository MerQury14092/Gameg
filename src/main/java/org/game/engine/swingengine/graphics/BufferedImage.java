package org.game.engine.swingengine.graphics;

import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.GraphicsContext;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;

import java.awt.*;

public class BufferedImage implements Image {
    private final java.awt.image.BufferedImage impl;
    private final Graphics2D implGraphics;
    public BufferedImage(Vector size){
        impl = new java.awt.image.BufferedImage(size.a, size.b, java.awt.image.BufferedImage.TYPE_INT_RGB);
        implGraphics = impl.createGraphics();
    }
    public BufferedImage(java.awt.image.BufferedImage image){
        impl = image;
        implGraphics = (Graphics2D) image.getGraphics();
    }
    @Override
    public void setPixel(Color color, Vector pos) {
        impl.setRGB(
                pos.a,
                pos.b,
                Integer.parseInt(
                    Integer.toHexString(color.getR())+Integer.toHexString(color.getG())+Integer.toHexString(color.getB()),
                    16
                )
        );
    }

    @Override
    public Color getPixel(Vector pos) {
        java.awt.Color color = new java.awt.Color(impl.getRGB(pos.a, pos.b));
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public Vector getSize() {
        return new Vector(impl.getWidth(), impl.getHeight());
    }

    @Override
    public void clear() {
        implGraphics.setColor(java.awt.Color.BLACK);
        implGraphics.fillRect(0, 0, impl.getWidth(), impl.getHeight());
    }

    @Override
    public GraphicsContext getGraphicsContext() {
        return new GraphicsContextImpl(implGraphics);
    }

    java.awt.image.BufferedImage getAwtBufferedImage(){
        return impl;
    }
}
