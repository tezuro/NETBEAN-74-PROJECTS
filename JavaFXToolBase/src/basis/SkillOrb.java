/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import io.IBindable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafxtoolbase.ToolBox;

/**
 *
 * @author tezuro
 */
public class SkillOrb implements IBindable{
    
    public static final String ID = "ORBS";

    private static final Logger LOG = Logger.getLogger(SkillOrb.class.getName());
    private static final double DURATION = 2000.0;
    private Circle skillRange = new Circle(300.0, Color.AQUA);
    private List<DoubleProperty> orbcharges = new ArrayList<>();
    private int damageFactorLVL;
    private Ellipse ellipse = new Ellipse(70.0, 70.0);
    private Xform orbLaufbahn = new Xform();
    private ArrayList<Orb> orbs = new ArrayList<>();
    private ChangeListener rangeChecker = new ChangeListener() {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1) {
            Circle collisionDetected = ToolBox.checkShapeIntersection(skillRange, NodeManager.getNodes());
            if (collisionDetected != null) {
                for (Orb o : orbs) {
                    o.activate(collisionDetected, orbLaufbahn);
                }
            }
        }
    };

    public SkillOrb(final Group root, Circle ownerHitBox, KeyCode keyToMark) {
        skillRange.centerXProperty().bind(ownerHitBox.centerXProperty());
        skillRange.centerYProperty().bind(ownerHitBox.centerYProperty());
        skillRange.setOpacity(0.5);
        ellipse.setOpacity(0.0);

        skillRange.centerXProperty().addListener(rangeChecker);
        skillRange.centerYProperty().addListener(rangeChecker);
        orbLaufbahn.translateXProperty().bind(ownerHitBox.centerXProperty());
        orbLaufbahn.translateYProperty().bind(ownerHitBox.centerYProperty());
        NodeManager.addListener(rangeChecker);
        root.getChildren().add(0, skillRange);
        root.getChildren().add(orbLaufbahn);
    }

    public void addOrb(final Group root, double fullCharge, double charging) {
        Orb o = new Orb(root, fullCharge, charging, ellipse, DURATION, orbLaufbahn);
        orbs.add(o);
        o.orbNumber = orbs.size() - 1;
    }

    public void readyUP() {
        for (Orb o : orbs) {
            o.getTransition().playFrom(Duration.millis(DURATION / orbs.size() * o.orbNumber));
        }
    }

    @Override
    public void proccessData(String[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getID() {
        return ID;
    }

    public static class Orb {

        public double orbNumber = 0;
        private Timeline chargingTimeLine;
        private volatile boolean isReady = false;
        private double charge;
        private final Circle orbSphere = new Circle(10.0);
        private PathTransition pathTransitionStar;
        private final Group root;

        /**
         *
         * @param fullCharge
         * @param charging
         */
        public Orb(final Group root, double fullCharge, double charging, Ellipse ellipse, double duration, Xform orbLaufbahn) {
            this.root = root;
            charge = fullCharge;

            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setIterations(3);

            chargingTimeLine = new Timeline(new KeyFrame(Duration.ZERO,
                    new KeyValue(orbSphere.fillProperty(), Color.YELLOW),
                    new KeyValue(boxBlur.widthProperty(), 10.0),
                    new KeyValue(boxBlur.heightProperty(), 10.0)),
                    new KeyFrame(Duration.millis(fullCharge * 1000 / charging),
                    new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    isReady = true;
                }
            }, new KeyValue(boxBlur.widthProperty(), 0.0, Interpolator.LINEAR),
                    new KeyValue(boxBlur.widthProperty(), 0.0, Interpolator.LINEAR),
                    new KeyValue(orbSphere.fillProperty(), Color.RED, Interpolator.LINEAR)));
            chargingTimeLine.play();
            pathTransitionStar = PathTransitionBuilder.create()
                    .node(orbSphere)
                    .path(ellipse)
                    .interpolator(Interpolator.LINEAR)
                    .duration(Duration.millis(duration))
                    .orientation(PathTransition.OrientationType.NONE)
                    .cycleCount(Timeline.INDEFINITE)
                    .build();


            orbLaufbahn.getChildren().add(orbSphere);
        }

        public void activate(Circle target, Xform orbLaufbahn) {
            if (isReady) {
                isReady = false;
                final Line lineOfFire = new Line();
                lineOfFire.startXProperty().bind(orbLaufbahn.translateXProperty().add(orbSphere.translateXProperty()));
                lineOfFire.startYProperty().bind(orbLaufbahn.translateYProperty().add(orbSphere.translateYProperty()));
                lineOfFire.endXProperty().bind(target.centerXProperty());
                lineOfFire.endYProperty().bind(target.centerYProperty());
                lineOfFire.setEffect(new Glow(3.0));
                lineOfFire.setFill(Color.WHITESMOKE);
                lineOfFire.minWidth(1.0);
                lineOfFire.minHeight(1.0);
                ToolBox.starSplash(root, target.centerXProperty().getValue(), target.centerYProperty().getValue());

                root.getChildren().add(lineOfFire);
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), lineOfFire);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0f);
                fadeTransition.setCycleCount(1);
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // we are done remove us from scene
                        root.getChildren().remove(lineOfFire);
                        lineOfFire.startXProperty().unbind();
                        lineOfFire.startYProperty().unbind();
                        lineOfFire.endXProperty().unbind();
                        lineOfFire.endYProperty().unbind();
                    }
                });
                fadeTransition.play();
                chargingTimeLine.play();
            }
        }

        public PathTransition getTransition() {
            return pathTransitionStar;
        }
    }
}
