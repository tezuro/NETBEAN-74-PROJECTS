/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafxtoolbase.ToolBox;


/**
 *
 * @author tezuro
 */
public class SkillSpiderMines {

    private Integer maxActiveSpiderMines = 0;
    private KeyCode keyToActivate;
    private Circle player;
    private Group root;
    public EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            if (keyToActivate.equals(t.getCode())) {
                if (KeyEvent.KEY_PRESSED.equals(t.getEventType())) {
                    new SpiderMine(player, root);
                }
            }
        }
    };

    public SkillSpiderMines(final Group root, int maxActiveMines, KeyCode keyToActivate, Circle player, Scene scene) {
        this.keyToActivate = keyToActivate;
        this.player = player;
        this.root = root;
        maxActiveSpiderMines = maxActiveMines;
        scene.addEventHandler(KeyEvent.ANY, keyListener);
    }

    private static class SpiderMine {

        /**
         * groesse = Damage/ 200
         */
        private double groesse = 10.0;
        /**
         * wie lange die sipdermine das target verfolgt
         */
        private double homingTime = 2000.0;
        private Circle spiderMine = new Circle(groesse, Color.GOLD);
        private volatile boolean isNotFired = true;
        /**
         * der Trigger Circle wird hinzugefuegt und ist nach einer Zeit X scharf
         * wenn ein gegner in reichweite kommt laeuft die spider mine los wie
         * ein homing shot fuer eine zeit Y
         */
        private Circle spiderMineTriggerCircle = new Circle(100.0, Color.AQUAMARINE);
        private Group root;
        final Timeline delay = new Timeline(new KeyFrame(Duration.millis(500)));
        final ChangeListener triggerListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                Circle opfer = ToolBox.checkShapeIntersection(spiderMineTriggerCircle, NodeManager.getNodes());
                if (opfer != null && !Animation.Status.RUNNING.equals(delay.getStatus())) {
                    if (isNotFired) {
                        NodeManager.removeListener(this);
                        isNotFired = false;
                        root.getChildren().remove(spiderMineTriggerCircle);
                        homing(root, spiderMine, opfer);
                    }
                }
            }
        };

        public SpiderMine(Circle player, final Group root) {
            this.root = root;

            spiderMine.centerXProperty().set(player.centerXProperty().getValue());
            spiderMine.centerYProperty().set(player.centerYProperty().getValue());
            spiderMineTriggerCircle.centerXProperty().set(player.centerXProperty().getValue());
            spiderMineTriggerCircle.centerYProperty().set(player.centerYProperty().getValue());

            root.getChildren().add(0, spiderMineTriggerCircle);
            delay.play();
            NodeManager.addListener(triggerListener);

        }

        public void unbind() {
            root.getChildren().remove(spiderMine);
            root.getChildren().remove(spiderMineTriggerCircle);
        }

        private void adjustPath(final Group root, final Timeline pathTimeLine, Circle bullet, Circle homingTarget) {
            pathTimeLine.stop();
            pathTimeLine.getKeyFrames().clear();
            pathTimeLine.getKeyFrames().addAll(upDateShoot(root, bullet, homingTarget.getCenterX(), homingTarget.getCenterY()).getKeyFrames());
            pathTimeLine.playFromStart();
        }

        private Timeline upDateShoot(final Group root, final Circle bullet, Double x, Double y) {

            Point2D pointA = new Point2D(bullet.centerXProperty().getValue(), bullet.centerYProperty().getValue());
            Point2D pointB = new Point2D(x, y);

            double distance = pointA.distance(pointB);
            double duration = distance;
            //100 / 100millis =

            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,
                    new KeyValue(bullet.centerXProperty(), pointA.getX()),
                    new KeyValue(bullet.centerYProperty(), pointA.getY())),
                    new KeyFrame(Duration.millis(duration),
                    new KeyValue(bullet.centerXProperty(), x, Interpolator.LINEAR),
                    new KeyValue(bullet.centerYProperty(), y, Interpolator.LINEAR)));
            timeline.setCycleCount(1);
            return timeline;
        }

        private void homing(final Group root, final Circle sipderMine, final Circle homingTarget) {
            /**
             * homing logic
             */
            final Timeline countDown = new Timeline();
            root.getChildren().add(sipderMine);
            countDown.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(homingTime),
                    new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double x = sipderMine.centerXProperty().getValue();
                    double y = sipderMine.centerYProperty().getValue();
                    root.getChildren().remove(sipderMine);
                    ToolBox.explosion(root, x, y);
                }
            }));
            countDown.play();



            final ChangeListener changeListener = new ChangeListener() {
                @Override
                public void changed(ObservableValue ov, Object t, Object t1) {
                    Circle collisionDetected = ToolBox.checkShapeIntersection(sipderMine, NodeManager.getNodes());
                    if (collisionDetected != null && Animation.Status.RUNNING.equals(countDown.getStatus())) {
                        countDown.stop();
                        double x = sipderMine.centerXProperty().getValue();
                        double y = sipderMine.centerYProperty().getValue();
                        root.getChildren().remove(sipderMine);
                        ToolBox.explosion(root, x, y);
                        ToolBox.starSplash(root, x, y);
                        sipderMine.centerXProperty().removeListener(this);
                        sipderMine.centerYProperty().removeListener(this);
                        homingTarget.centerXProperty().removeListener(this);
                        homingTarget.centerYProperty().removeListener(this);

                    }
                }
            };

            sipderMine.centerXProperty().addListener(changeListener);
            sipderMine.centerYProperty().addListener(changeListener);
            homingTarget.centerXProperty().addListener(changeListener);
            homingTarget.centerYProperty().addListener(changeListener);

            final Timeline pathTimeLine = upDateShoot(root, sipderMine, homingTarget.getCenterX(), homingTarget.getCenterY());
            pathTimeLine.play();
            sipderMine.centerXProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue ov, Object t, Object t1) {
                    adjustPath(root, pathTimeLine, sipderMine, homingTarget);
                }
            });
            sipderMine.centerYProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue ov, Object t, Object t1) {
                    adjustPath(root, pathTimeLine, sipderMine, homingTarget);
                }
            });
        }
    }
}
