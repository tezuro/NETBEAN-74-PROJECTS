/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ranged;

import basis.*;
import io.GenericSocket;
import io.IBindable;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafxtoolbase.ToolBox;

/**
 *
 * @author tezuro
 */
public class TestWeapon implements IBindable {

    private static final String WEAPON_ID = "EXPERIMENT_ONE";
    private static final String CREATE = "C";
    private static final String DESTROY = "D";
    private static final String CREATE_HOMING = "CH";
    private static final String UPDATE_HOMING = "UH";
    /**
     * aufbau der test weapon string S|ID(EXPERIMENT_ONE)|DESTROY|ID
     * S|ID(EXPERIMENT_ONE)|CREATE|AX|AY|BX|BY|SPEED|SIZE| RETURNS ID
     */
    private Group root;
    private Circle ownerHitBox;
    private final GenericSocket socket;
    HashMap<String, Circle> bullets = new HashMap<>();
    public EventHandler<MouseEvent> mouselistener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            homingshot(root, ownerHitBox, t.getX(), t.getY());
        }
    };

    public TestWeapon(final Group root, Circle ownerHitBox, Scene scene, GenericSocket socket) {
        this.root = root;
        this.ownerHitBox = ownerHitBox;
        this.socket = socket;
        scene.onMousePressedProperty().set(mouselistener);
    }

    public void homingshot(final Group root, Circle start, Double x, Double y) {
        Point2D pointA = new Point2D(start.centerXProperty().getValue(), start.centerYProperty().getValue());
        Point2D pointB = new Point2D(x, y);
        boolean homingShotNotFired = true;
        for (final Circle homingTarget : NodeManager.getNodes()) {
            if (homingTarget.contains(pointB) && homingShotNotFired) {
                homingShotNotFired = false;
                homing(root, pointA, homingTarget);
            }
        }
        if (homingShotNotFired) {
            shoot(root, start, x, y);
        }
    }

    private void adjustPath(final Group root, final Timeline pathTimeLine, Circle bullet, Circle homingTarget) {
        pathTimeLine.stop();
        pathTimeLine.getKeyFrames().clear();
        pathTimeLine.getKeyFrames().addAll(upDateShoot(root, bullet, homingTarget.getCenterX(), homingTarget.getCenterY()).getKeyFrames());
        pathTimeLine.playFromStart();
    }

    public Timeline upDateShoot(final Group root, final Circle bullet, Double x, Double y) {

        Point2D pointA = new Point2D(bullet.centerXProperty().getValue(), bullet.centerYProperty().getValue());
        Point2D pointB = new Point2D(x, y);

        double distance = pointA.distance(pointB);
        double duration = distance;
        //100 / 100millis =

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(bullet.centerXProperty(), pointA.getX()),
                        new KeyValue(bullet.centerYProperty(), pointA.getY())),
                new KeyFrame(Duration.millis(duration),
                        new KeyValue(bullet.centerXProperty(), x, Interpolator.LINEAR),
                        new KeyValue(bullet.centerYProperty(), y, Interpolator.LINEAR)));
        timeline.setCycleCount(1);
        return timeline;
    }

    public Timeline shoot(final Group root, Circle start, Double x, Double y) {

        Point2D pointA = new Point2D(start.centerXProperty().getValue(), start.centerYProperty().getValue());
        Point2D pointB = new Point2D(x, y);

        double distance = pointA.distance(pointB);
        double duration = distance;
        //100 / 100millis =
        final Circle bullet = new Circle(20.0, Color.BLACK);
        bullet.setId(IdGenerator.getNextID());
        root.getChildren().add(bullet);
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(bullet.centerXProperty(), pointA.getX()),
                        new KeyValue(bullet.centerYProperty(), pointA.getY())),
                new KeyFrame(Duration.millis(duration),
                        new KeyValue(bullet.centerXProperty(), x, Interpolator.LINEAR),
                        new KeyValue(bullet.centerYProperty(), y, Interpolator.LINEAR)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double x = bullet.centerXProperty().getValue();
                double y = bullet.centerYProperty().getValue();
                root.getChildren().remove(bullet);
                String msg = WEAPON_ID + "|" + DESTROY + "|" + bullet.getId() + "|";
                socket.sendMessage(msg);
                ToolBox.explosion(root, x, y);
            }
        });

        String msg = WEAPON_ID + "|" + CREATE + "|"
                + pointA.getX() + "|"
                + pointA.getY() + "|"
                + x + "|"
                + y + "|"
                + duration + "|"
                + bullet.getId() + "|"
                + 20.0 + "|";
        socket.sendMessage(msg);

        timeline.setCycleCount(1);

        bullet.centerXProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                Circle collisionDetected = ToolBox.checkShapeIntersection(bullet, NodeManager.getNodes());
                if (collisionDetected != null && Animation.Status.RUNNING.equals(timeline.getStatus())) {
                    timeline.stop();
                    double x = bullet.centerXProperty().getValue();
                    double y = bullet.centerYProperty().getValue();
                    root.getChildren().remove(bullet);
                    String msg = WEAPON_ID + "|" + DESTROY + "|" + bullet.getId() + "|";
                    socket.sendMessage(msg);
                    ToolBox.explosion(root, x, y);
                    ToolBox.starSplash(root, x, y);
                }
            }
        });
        bullet.centerYProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                Circle collisionDetected = ToolBox.checkShapeIntersection(bullet, NodeManager.getNodes());
                if (collisionDetected != null && Animation.Status.RUNNING.equals(timeline.getStatus())) {
                    timeline.stop();
                    double x = bullet.centerXProperty().getValue();
                    double y = bullet.centerYProperty().getValue();
                    root.getChildren().remove(bullet);
                    String msg = WEAPON_ID + "|" + DESTROY + "|" + bullet.getId() + "|";
                    socket.sendMessage(msg);
                    ToolBox.explosion(root, x, y);
                    ToolBox.starSplash(root, x, y);
                }
            }
        });
        timeline.play();
        return timeline;
    }

    private void homing(final Group root, Point2D pointA, final Circle homingTarget) {
        /**
         * homing logic
         */
        final Timeline countDown = new Timeline();
        final Circle bullet = new Circle(20.0, Color.BLACK);
        bullet.setId(IdGenerator.getNextID());
        root.getChildren().add(bullet);
        bullet.setCenterX(pointA.getX());
        bullet.setCenterY(pointA.getY());

        String msg = WEAPON_ID + "|"
                + CREATE_HOMING + "|"
                + bullet.getId() + "|"
                + pointA.getX() + "|"
                + pointA.getY() + "|"
                + 20.0 + "|";
        socket.sendMessage(msg);

        countDown.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(2000),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                double x = bullet.centerXProperty().getValue();
                                double y = bullet.centerYProperty().getValue();
                                root.getChildren().remove(bullet);
                                String msg = WEAPON_ID + "|" + DESTROY + "|" + bullet.getId() + "|";
                                socket.sendMessage(msg);
                                ToolBox.explosion(root, x, y);
                            }
                        }));
        countDown.play();

        final ChangeListener changeListenerUpdate = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                String msg = WEAPON_ID + "|"
                        + UPDATE_HOMING + "|"
                        + bullet.getId() + "|"
                        + bullet.centerXProperty().get() + "|"
                        + bullet.centerYProperty().get() + "|";
                socket.sendMessage(msg);
            }
        };

        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                Circle collisionDetected = ToolBox.checkShapeIntersection(bullet, NodeManager.getNodes());
                if (collisionDetected != null && Animation.Status.RUNNING.equals(countDown.getStatus())) {
                    countDown.stop();
                    double x = bullet.centerXProperty().getValue();
                    double y = bullet.centerYProperty().getValue();
                    root.getChildren().remove(bullet);
                    ToolBox.explosion(root, x, y);
                    ToolBox.starSplash(root, x, y);
                    bullet.centerXProperty().removeListener(changeListenerUpdate);
                    bullet.centerYProperty().removeListener(changeListenerUpdate);
                    bullet.centerXProperty().removeListener(this);
                    bullet.centerYProperty().removeListener(this);
                    homingTarget.centerXProperty().removeListener(this);
                    homingTarget.centerYProperty().removeListener(this);
                    String msg = WEAPON_ID + "|" + DESTROY + "|" + bullet.getId() + "|";
                    socket.sendMessage(msg);
                }
            }
        };

        bullet.centerXProperty().addListener(changeListenerUpdate);
        bullet.centerYProperty().addListener(changeListenerUpdate);
        bullet.centerXProperty().addListener(changeListener);
        bullet.centerYProperty().addListener(changeListener);
        homingTarget.centerXProperty().addListener(changeListener);
        homingTarget.centerYProperty().addListener(changeListener);

        final Timeline pathTimeLine = upDateShoot(root, bullet, homingTarget.getCenterX(), homingTarget.getCenterY());
        pathTimeLine.play();
        bullet.centerXProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                adjustPath(root, pathTimeLine, bullet, homingTarget);
            }
        });
        bullet.centerYProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                adjustPath(root, pathTimeLine, bullet, homingTarget);
            }
        });
    }

    @Override
    public void proccessData(String[] data) {
        if (CREATE.equals(data[1])) {
            final Circle bullet = new Circle(20.0, Color.BLACK);
            bullet.setId(data[7]);
            bullets.put(data[7], bullet);
            root.getChildren().add(bullet);
            final Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(bullet.centerXProperty(), Double.parseDouble(data[2])),
                            new KeyValue(bullet.centerYProperty(), Double.parseDouble(data[3]))),
                    new KeyFrame(Duration.millis(Double.parseDouble(data[6])),
                            new KeyValue(bullet.centerXProperty(), Double.parseDouble(data[4]), Interpolator.LINEAR),
                            new KeyValue(bullet.centerYProperty(), Double.parseDouble(data[5]), Interpolator.LINEAR)));
            timeline.play();
            return;
        }
        if (CREATE_HOMING.equals(data[1])) {
            final Circle bullet = new Circle(20.0, Color.BLACK);
            bullet.setId(data[2]);
            bullet.centerXProperty().set(Double.parseDouble(data[3]));
            bullet.centerYProperty().set(Double.parseDouble(data[4]));
            bullets.put(data[2], bullet);
            root.getChildren().add(bullet);
            return;
        }
        if (UPDATE_HOMING.equals(data[1])) {
            Circle bullet = bullets.get(data[2]);
            if (bullet != null) {
                bullet.centerXProperty().set(Double.parseDouble(data[3]));
                bullet.centerYProperty().set(Double.parseDouble(data[4]));
            }
            return;
        }
        if (DESTROY.equals(data[1])) {
            Circle c = bullets.remove(data[2]);
            if (c != null) {
                ToolBox.explosion(root, c.centerXProperty().getValue(), c.centerYProperty().getValue());
                root.getChildren().remove(c);
            }
        }
    }

    @Override
    public String getID() {
        return WEAPON_ID;
    }
}
