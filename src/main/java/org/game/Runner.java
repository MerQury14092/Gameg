package org.game;

import org.game.engine.protocol.core.ClArgs;
import org.game.game.Main;
public class Runner {
    public static void main(String[] args) {
        ClArgs.init(args);
        new Main();
    }
}
