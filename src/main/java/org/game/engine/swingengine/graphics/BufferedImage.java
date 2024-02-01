package org.game.engine.swingengine.graphics;

import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.GraphicsContext;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

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
    public BufferedImage(InputStream source) throws IOException {
        var incompat_img = ImageIO.read(source);
        var compat_img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration().createCompatibleImage(incompat_img.getWidth(), incompat_img.getHeight(),
                        incompat_img.getTransparency());
        Graphics2D g2d = compat_img.createGraphics();
        g2d.drawImage(incompat_img, 0, 0, compat_img.getWidth(), compat_img.getHeight(), null);
        impl = compat_img;
        implGraphics = g2d;
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
    public Image clone() {
        java.awt.image.BufferedImage clone = new java.awt.image.BufferedImage(impl.getWidth(), impl.getHeight(), impl.getType());
        var g2d = clone.createGraphics();
        g2d.drawImage(impl, 0, 0, null);
        g2d.dispose();
        return new BufferedImage(clone);
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
