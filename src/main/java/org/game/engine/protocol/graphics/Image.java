package org.game.engine.protocol.graphics;

import org.game.engine.protocol.math.Vector;

public interface Image {
    void setPixel(Color color, Vector pos);
    Color getPixel(Vector pos);
    Vector getSize();
    void clear();
    GraphicsContext getGraphicsContext();
}
