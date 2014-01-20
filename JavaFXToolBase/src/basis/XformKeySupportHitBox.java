/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class XformKeySupportHitBox {

    private Timeline up;
    private Timeline down;
    private Timeline right;
    private Timeline left;
    private static double STEP_SIZE = 1.0;
    private DoubleProperty depth = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private volatile HashSet<KeyCode> currentKeys = new HashSet<>();
    private Circle hitbox = null;
    private EventHandler pressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            keyPressed(t);
        }
    };
    private EventHandler released = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            keyReleased(t);
        }
    };

    public XformKeySupportHitBox(final Group root, Circle hitbox, Scene scene, boolean inverted) {
        super();
        depth.bind(root.getScene().heightProperty());
        width.bind(root.getScene().widthProperty());
        this.hitbox = hitbox;
        scene.setOnKeyPressed(pressed);
        scene.setOnKeyReleased(released);

    }

    public void keyReleased(KeyEvent e) {
        KeyCode keyCode = e.getCode();


        if (KeyCode.LEFT.equals(keyCode)) {

            if (currentKeys.contains(KeyCode.RIGHT)) {
                if (left != null) {
                    left.stop();
                }
                right = moveRIGHT(STEP_SIZE);
            } else {
                left.stop();
            }
        }
        if (KeyCode.RIGHT.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.LEFT)) {
                if (right != null) {
                    right.stop();
                }
                left = moveLEFT(STEP_SIZE);
            } else {
                right.stop();
            }
        }

        if (KeyCode.UP.equals(keyCode)) {

            if (currentKeys.contains(KeyCode.DOWN)) {
                if (up != null) {
                    up.stop();
                }
                down = moveDOWN(STEP_SIZE);
            } else {
                up.stop();
            }
        }
        if (KeyCode.DOWN.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.UP)) {
                if (down != null) {
                    down.stop();
                }
                up = moveUP(STEP_SIZE);
            } else {
                down.stop();
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
                right.stop();
            } else {
                if (left != null) {
                    if (!Animation.Status.RUNNING.equals(left.getStatus())) {
                        left = moveLEFT(STEP_SIZE);
                    }
                } else {
                    left = moveLEFT(STEP_SIZE);
                }
            }
        }
        if (KeyCode.RIGHT.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.LEFT)) {
                left.stop();
            } else {
                if (right != null) {
                    if (!Animation.Status.RUNNING.equals(right.getStatus())) {
                        right = moveRIGHT(STEP_SIZE);
                    }
                } else {
                    right = moveRIGHT(STEP_SIZE);
                }
            }
        }
        if (KeyCode.UP.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.DOWN)) {
                down.stop();
            } else {
                if (up != null) {
                    if (!Animation.Status.RUNNING.equals(up.getStatus())) {
                        up = moveDOWN(STEP_SIZE);
                    }
                } else {
                    up = moveDOWN(STEP_SIZE);
                }
            }
        }
        if (KeyCode.DOWN.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.UP)) {
                up.stop();
            } else {
                if (down != null) {
                    if (!Animation.Status.RUNNING.equals(down.getStatus())) {
                        down = moveUP(STEP_SIZE);
                    }
                } else {
                    down = moveUP(STEP_SIZE);
                }
            }
        }
        currentKeys.add(keyCode);
    }

    private Timeline moveUP(double speed) {

        System.out.println("moveUP:" + hitbox.centerYProperty().getValue());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(hitbox.centerYProperty(), hitbox.centerYProperty().getValue())),
                new KeyFrame(Duration.millis(calcSpeedup()),
                new KeyValue(hitbox.centerYProperty(), depth.get(), Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private Timeline moveDOWN(double speed) {
        System.out.println("moveDOWN:" + hitbox.centerYProperty().getValue());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(hitbox.centerYProperty(), hitbox.centerYProperty().getValue())),
                new KeyFrame(Duration.millis(calcSpeeddown()),
                new KeyValue(hitbox.centerYProperty(), 0, Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private Timeline moveRIGHT(double speed) {
        System.out.println("moveRIGHT:" + hitbox.centerXProperty().getValue());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(hitbox.centerXProperty(), hitbox.centerXProperty().getValue())),
                new KeyFrame(Duration.millis(calcSpeedright()),
                new KeyValue(hitbox.centerXProperty(), width.get(), Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private Timeline moveLEFT(double speed) {
        System.out.println("moveLEFT:" + hitbox.centerXProperty().getValue());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(hitbox.centerXProperty(), hitbox.centerXProperty().getValue())),
                new KeyFrame(Duration.millis(calcSpeedleft()),
                new KeyValue(hitbox.centerXProperty(), 0, Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private double calcSpeedright() {

        double speed = (width.get() - hitbox.centerXProperty().getValue()) * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }

    private double calcSpeedleft() {
        double speed = hitbox.centerXProperty().getValue() * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }

    private double calcSpeedup() {
        double speed = (depth.get() - hitbox.centerYProperty().getValue()) * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }

    private double calcSpeeddown() {
        double speed = hitbox.centerYProperty().getValue() * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }
}
