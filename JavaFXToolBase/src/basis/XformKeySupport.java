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
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class XformKeySupport extends Group {

    public volatile Rotate rx = new Rotate(0, Rotate.X_AXIS);
    public volatile Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    public volatile Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    private Timeline up;
    private Timeline down;
    private Timeline right;
    private Timeline left;
    private static double STEP_SIZE = 1.0;
    private final DoubleProperty depth = new SimpleDoubleProperty();
    private final DoubleProperty width = new SimpleDoubleProperty();
    private volatile HashSet<KeyCode> currentKeys = new HashSet<>();

    public XformKeySupport() {
        super();
        depth.set(300.0);
        width.set(300.0);
        getTransforms().addAll(rz, ry, rx);
    }

    public XformKeySupport(final Group root) {
        super();
        depth.bind(root.getScene().heightProperty());
        width.bind(root.getScene().widthProperty());
        getTransforms().addAll(rz, ry, rx);
    }

    public XformKeySupport(DoubleProperty depthp, DoubleProperty widthp, boolean inverted) {
        super();
        depth.bind(depthp);
        width.bind(widthp);
        getTransforms().addAll(rz, ry, rx);
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
        for (KeyCode k:currentKeys) {
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
                        up = moveUP(STEP_SIZE);
                    }
                } else {
                    up = moveUP(STEP_SIZE);
                }
            }
        }
        if (KeyCode.DOWN.equals(keyCode)) {
            if (currentKeys.contains(KeyCode.UP)) {
                up.stop();
            } else {
                if (down != null) {
                    if (!Animation.Status.RUNNING.equals(down.getStatus())) {
                        down = moveDOWN(STEP_SIZE);
                    }
                } else {
                    down = moveDOWN(STEP_SIZE);
                }
            }
        }
        currentKeys.add(keyCode);
    }

    private Timeline moveUP(double speed) {
        if (getTranslateY() > 300) {
            //return null;
        }
        System.out.println("moveUP:" + getTranslateY());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(translateYProperty(), getTranslateY())),
                new KeyFrame(Duration.millis(calcSpeedup()),
                new KeyValue(translateYProperty(), depth.get(), Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private Timeline moveDOWN(double speed) {
        System.out.println("moveDOWN:" + getTranslateY());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(translateYProperty(), getTranslateY())),
                new KeyFrame(Duration.millis(calcSpeeddown()),
                new KeyValue(translateYProperty(), 0, Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private Timeline moveRIGHT(double speed) {
        System.out.println("moveRIGHT:" + getTranslateX());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(translateXProperty(), getTranslateX())),
                new KeyFrame(Duration.millis(calcSpeedright()),
                new KeyValue(translateXProperty(), width.get(), Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private Timeline moveLEFT(double speed) {
        System.out.println("moveLEFT:" + getTranslateX());
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(translateXProperty(), getTranslateX())),
                new KeyFrame(Duration.millis(calcSpeedleft()),
                new KeyValue(translateXProperty(), 0, Interpolator.LINEAR)));
        result.play();
        return result;
    }

    private double calcSpeedright() {

        double speed = (width.get() - getTranslateX()) * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        System.out.println("Speed:" + speed);
        return speed;
    }

    private double calcSpeedleft() {
        double speed = getTranslateX() * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }

    private double calcSpeedup() {
        double speed = (depth.get() - getTranslateY()) * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }

    private double calcSpeeddown() {
        double speed = getTranslateY() * STEP_SIZE;

        if (speed < 0) {
            speed = speed * -1;
        }
        return speed;
    }
}
