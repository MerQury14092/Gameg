package org.game.engine.swingengine.graphics;

import org.game.engine.protocol.core.ClArgs;
import org.game.engine.protocol.graphics.Image;
import org.game.engine.protocol.graphics.Window;
import org.game.engine.protocol.math.Vector;
import org.game.projectlib.ThreadTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class WindowImpl implements Window {
    private final JFrame impl;
    private final BufferedImage buffer;
    private final Map<String, Runnable[]> keyListeners;
    private final List<String> pressedKeys;
    public WindowImpl(Vector pos, Vector size, String title){
        int refreshRate = getRefreshRate();
        System.out.println("REFRESH_RATE = "+refreshRate);
        pressedKeys = new ArrayList<>();
        keyListeners = new HashMap<>();
        buffer = new BufferedImage(size);
        impl = new JFrame(title);
        impl.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        impl.setBounds(pos.a, pos.b, size.a, size.b);
        JPanel content = new JPanel(){
            @Override
            public void paint(Graphics g) {
                g.drawImage(buffer.getAwtBufferedImage(), 0, 0, null);
            }
        };
        content.setPreferredSize(new Dimension(size.a, size.b));
        impl.setContentPane(content);
        impl.pack();
        ThreadTools.runDaemon("repaintThread", () -> {
            for (;;) {
                try {
                    Thread.sleep(1000/refreshRate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                content.repaint();
            }
        });
        ThreadTools.runDaemon("keyActionHandler", () -> {
            for (;;)
            {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < pressedKeys.size(); i++) {
                    String key = pressedKeys.get(i);
                    if(keyListeners.containsKey(key))
                        if( keyListeners.get(key)[1] != null)
                            keyListeners.get(key)[1].run();
                }
            }
        });
        impl.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String location = e.paramString().split(",")[6].split("=")[1];
                String prefix = switch (location){
                    case "KEY_LOCATION_LEFT" -> "l";
                    case "KEY_LOCATION_RIGHT" -> "r";
                    default -> "";
                };
                String keycode = prefix + e.paramString().split(",")[2].split("=")[1].toLowerCase();
                if(!pressedKeys.contains(keycode)) {
                    if (keyListeners.containsKey(keycode))
                        if (keyListeners.get(keycode)[0] != null)
                            keyListeners.get(keycode)[0].run();
                    pressedKeys.add(keycode);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String location = e.paramString().split(",")[4].split("=")[1];
                String prefix = switch (location){
                    case "KEY_LOCATION_LEFT" -> "l";
                    case "KEY_LOCATION_RIGHT" -> "r";
                    default -> "";
                };
                String keycode = prefix + e.paramString().split(",")[2].split("=")[1].toLowerCase();
                if(keyListeners.containsKey(keycode))
                    if (keyListeners.get(keycode)[2] != null)
                        keyListeners.get(keycode)[2].run();
                pressedKeys.remove(keycode);
            }
        });
        impl.setVisible(true);
    }
    @Override
    public void setResizable(boolean resizable) {
        impl.setResizable(resizable);
    }

    // TODO: linux gnome x11: неправильное определение текущей частоты кадров. результат 50, ожидаемо 165
    private int getRefreshRate(){
        // TODO: программа не умеет работать с многомониторными конфигурациями
        // программа настраивается под один монитор и при переносе на 2 монитор программа будет все равно работать
        // с конфигом первого
        if(ClArgs.options.containsKey("refresh_rate"))
            return (int)ClArgs.options.get("refresh_rate");
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var devices = env.getScreenDevices();
        var dmMode = devices[0].getDisplayMode();
        return dmMode.getRefreshRate();
    }

    @Override
    public void resize(Vector newSize) {
        impl.setSize(newSize.a, newSize.b);
    }

    @Override
    public Vector getSize() {
        return new Vector(impl.getWidth(), impl.getHeight());
    }

    @Override
    public void setKeyAction(String keycode, Runnable onClicked, Runnable whilePressed, Runnable onReleased) {
        keyListeners.put(keycode, new Runnable[]{onClicked, whilePressed, onReleased});
    }

    @Override
    public Image getContent() {
        return buffer;
    }
}
