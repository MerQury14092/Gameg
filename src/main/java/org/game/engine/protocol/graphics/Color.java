package org.game.engine.protocol.graphics;

public class Color {
    private final byte R;
    private final byte G;
    private final byte B;

    public Color(int R, int G, int B) {
        if(R > 255 || G > 255 || B > 255 || R < 0 || G < 0 || B < 0)
            throw new IllegalArgumentException("RGB error!");
        // TODO: Реализовать нормальный вывод ошибки когда пытаемся в коды цветов засунуть не подходящие значения
        this.R = (byte) (R-128);
        this.G = (byte) (G-128);
        this.B = (byte) (B-128);
    }

    public int getR() {
        return R+128;
    }

    public int getG() {
        return G+128;
    }

    public int getB() {
        return B+128;
    }
}
