package org.game.engine.swingengine.graphics;

import org.game.engine.protocol.graphics.Color;
import org.game.engine.protocol.graphics.ComplexImage;
import org.game.engine.protocol.graphics.GraphicsContext;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.math.Vector;

import java.awt.*;

public class GraphicsContextImpl implements GraphicsContext {
    private final Graphics2D impl;

    public GraphicsContextImpl(Graphics2D impl) {
        this.impl = impl;
        impl.setColor(java.awt.Color.BLACK);
    }

    @Override
    public void draw(Vector pos, Image image) {
        if(!(image instanceof BufferedImage) && !(image instanceof ComplexBufferedImage))
            throw new RuntimeException("incompatible image and graphicscontext");
        // TODO: Реализовать нормальный вывод ошибки когда в свинговом контексте пытаемся отрисовать не свинговую картинку
        if(image instanceof BufferedImage)
            impl.drawImage(((BufferedImage) image).getAwtBufferedImage(), pos.a, pos.b, null);
        else
            impl.drawImage(((BufferedImage)((ComplexBufferedImage) image).getResult()).getAwtBufferedImage(), pos.a, pos.b, null);
    }

    @Override
    public void drawComplexImage(Vector pos, Image... images) {
        for (Image image : images) {
            if(!(image instanceof BufferedImage) && !(image instanceof ComplexBufferedImage))
                throw new RuntimeException("incompatible image and graphicscontext");
            // TODO: Реализовать нормальный вывод ошибки когда в свинговом контексте пытаемся отрисовать не свинговую картинку
        }
        BufferedImage[] swingimages = new BufferedImage[images.length];
        for (int i = 0; i < images.length; i++) {
            swingimages[i] = (BufferedImage) images[i];
        }
        draw(pos, new ComplexBufferedImage(swingimages));
    }

    @Override
    public void draw(Vector pos, Image image, Vector size) {
        if(!(image instanceof BufferedImage))
            throw new RuntimeException("incompatible image and graphicscontext");
        // TODO: Реализовать нормальный вывод ошибки когда в свинговом контексте пытаемся отрисовать не свинговую картинку
        impl.drawImage(((BufferedImage) image).getAwtBufferedImage(), pos.a, pos.b, size.a, size.b,null);
    }

    @Override
    public void fill(Vector pos, Vector size, Color color) {
        impl.setColor(new java.awt.Color(color.getR(), color.getG(), color.getB()));
        impl.fillRect(pos.a, pos.b, size.a, size.b);
        impl.setColor(java.awt.Color.BLACK);
    }

    @Override
    public void print(Vector pos, String text) {
        impl.drawString(text, pos.a, pos.b);
    }
}
