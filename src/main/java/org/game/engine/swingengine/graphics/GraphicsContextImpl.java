package org.game.engine.swingengine.graphics;

import org.game.engine.protocol.graphics.Color;
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
        if(!(image instanceof BufferedImage))
            throw new RuntimeException("incompatible image and graphicscontext");
        // TODO: Реализовать нормальный вывод ошибки когда в свинговом контексте пытаемся отрисовать не свинговую картинку
        impl.drawImage(((BufferedImage) image).getAwtBufferedImage(), pos.a, pos.b, null);
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
