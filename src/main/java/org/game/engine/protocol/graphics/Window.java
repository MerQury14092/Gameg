package org.game.engine.protocol.graphics;

import org.game.engine.protocol.math.Vector;

public interface Window {
    void setResizable(boolean resizable);
    void resize(Vector newSize);
    Vector getSize();

    /**
     * Функция, задающая на конретную клавишу конкретные действия
     *
     * @param keycode Код клавиши, для разносторонних клавиш префикс l - left, r - right обязателен. Все буквы должны быть строчными
     *                Примеры: a - клавиша A, escape - клавиша ESC, lshift - левый SHIFT, ralt - правый ALT
     *
     * @param onClicked Функция выполняемая единожды при нажатии, при ненадобности оставить null
     *
     * @param whilePressed Функция выполняемая постоянно, пока клавиша нажата, при ненадобности оставить null
     *
     * @param onReleased Функция выполняемая единожды при отпуске клавиши, при ненадобности оставить null
     */
    void setKeyListener(String keycode, Runnable onClicked, Runnable whilePressed, Runnable onReleased);
    Image getContent();
}
