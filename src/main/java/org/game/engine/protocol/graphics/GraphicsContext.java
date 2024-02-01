package org.game.engine.protocol.graphics;

import org.game.engine.protocol.math.Vector;

public interface GraphicsContext {
    void draw(Vector pos, Image image);
    void drawComplexImage(Vector pos, Image... images);
    void draw(Vector pos, Image image, Vector size);
    void fill(Vector pos, Vector size, Color color);
    void print(Vector pos, String text);
}
