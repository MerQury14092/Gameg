package org.game.engine;

import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.graphics.Window;

public interface Game {
    void render(Image frameBuffer);
    Window getWindow();
    int getFPS();
}
