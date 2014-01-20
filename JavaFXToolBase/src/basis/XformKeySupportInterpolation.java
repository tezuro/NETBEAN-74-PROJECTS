/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

/**
 *
 * @author tezuro
 */
public class XformKeySupportInterpolation extends Group {

    public volatile Rotate rx = new Rotate(0, Rotate.X_AXIS);
    public volatile Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    public volatile Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    private final KeyControl control;

    private final EventHandler pressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            keyPressed(t);
        }
    };
    private final EventHandler released = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            keyReleased(t);
        }
    };

    private volatile HashSet<KeyCode> currentKeys = new HashSet<>();

    public XformKeySupportInterpolation(final Player owner, Scene scene) {
        super();
        getTransforms().addAll(rz, ry, rx);
        this.control = new KeyControl(owner);
        scene.setOnKeyPressed(pressed);
        scene.setOnKeyReleased(released);
    }

    public void keyReleased(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        if (KeyCode.LEFT.equals(keyCode)) {
            control.LEFT.set(false);
            if (currentKeys.contains(KeyCode.RIGHT)) {
                control.RIGHT.set(true);
            }
        }
        if (KeyCode.RIGHT.equals(keyCode)) {
            control.RIGHT.set(false);
            if (currentKeys.contains(KeyCode.LEFT)) {
                control.LEFT.set(true);
            }
        }
        if (KeyCode.UP.equals(keyCode)) {
            control.UP.set(false);
            if (currentKeys.contains(KeyCode.DOWN)) {
                control.DOWN.set(true);
            }
        }
        if (KeyCode.DOWN.equals(keyCode)) {
            control.DOWN.set(false);
            if (currentKeys.contains(KeyCode.UP)) {
                control.UP.set(true);
            }
        }
        currentKeys.remove(keyCode);
        for (KeyCode k : currentKeys) {
            System.out.print(k.getName() + ";");
        }
    }

    public void keyPressed(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        ArrayList<Integer> listy = new ArrayList<>();
        Collections.sort(listy);

        if (KeyCode.LEFT.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.RIGHT)) {
                control.RIGHT.set(false);
            } else {
                control.LEFT.set(true);
            }
        }
        if (KeyCode.RIGHT.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.LEFT)) {
                control.LEFT.set(false);
            } else {
                control.RIGHT.set(true);
            }
        }
        if (KeyCode.UP.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.DOWN)) {
                control.DOWN.set(false);
            } else {
                control.UP.set(true);
            }
        }
        if (KeyCode.DOWN.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.UP)) {
                control.UP.set(false);
            } else {
                control.DOWN.set(true);
            }
        }
        currentKeys.add(keyCode);
    }
}
