/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import java.util.Stack;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafxtoolbase.ComboChain.Chainable;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public class SkillFlash implements Chainable{

    final public static String SKILL_ID = "FLASH";
    private Group root;
    private KeyCode keyCodeToActivate;
    private KeyCode keyCodeToMark;
    private int maxMarkCount = 0;
    private volatile Boolean markingActive = false;
    private volatile BooleanProperty skillReady = new SimpleBooleanProperty(false);
    private final DoubleProperty ownerX = new SimpleDoubleProperty();
    private final DoubleProperty ownerY = new SimpleDoubleProperty();
    private Stack<Mark> marks = new Stack<>();
    private final Player owner;
    public EventHandler<MouseEvent> mouselistener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if (MouseEvent.MOUSE_CLICKED.equals(t.getEventType())) {
                if (markingActive) {
                    if (marks.size() >= maxMarkCount) {
                        marks.remove(0).unbind();
                        skillReady.set(true);
                        System.out.println("marks.capacity():" + marks.capacity());
                    }
                    marks.push(new Mark(t, root));
                }
                System.out.println("marks.size():" + marks.size());
            }
        }
    };
    public EventHandler<KeyEvent> keylistener = new EventHandler<KeyEvent>() {
        Timeline timeToMark = new Timeline(new KeyFrame(Duration.millis(3000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        markingActive = false;
                    }
                }));

        @Override
        public void handle(KeyEvent t) {
            if (keyCodeToMark.equals(t.getCode())) {
                if (KeyEvent.KEY_PRESSED.equals(t.getEventType())) {
                    if (!markingActive) {
                        markingActive = true;
                        timeToMark.play();
                    }
                }
            }
            if (keyCodeToActivate.equals(t.getCode())) {
                if (KeyEvent.KEY_PRESSED.equals(t.getEventType()) && skillReady.getValue()) {
                    activate();
                }
            }
        }
    };

    public SkillFlash(final Group root, int maxMarkCount,final Player owner,final KeyCode keyToMark,final KeyCode keyToActivate,final Scene scene) {
        keyCodeToActivate = keyToActivate;
        keyCodeToMark = keyToMark;
        this.root = root;
        this.maxMarkCount = maxMarkCount;
        this.owner = owner;
        ownerX.bindBidirectional(owner.getHitbox().centerXProperty());
        ownerY.bindBidirectional(owner.getHitbox().centerYProperty());
        scene.addEventHandler(MouseEvent.ANY, mouselistener);
        scene.addEventHandler(KeyEvent.ANY, keylistener);
    }

    private void activate() {
        Mark ziel = marks.remove(0);
        System.out.println("X:" + ziel.getX() + "|Y:" + ziel.getY());
        ownerX.setValue(ziel.getX());
        ownerY.setValue(ziel.getY());
        marks.push(ziel);
    }

    public double activate(final Player target) {
        double x = target.playerHitBox.centerXProperty().doubleValue() - target.playerHitBox.getRadius();
        double y = target.playerHitBox.centerYProperty().doubleValue();
        System.out.println("X:" + x + "|Y:" + y);
        ownerX.setValue(x);
        ownerY.setValue(y);
        return getCooldownDuration(target);
    }

    private double getCooldownDuration(final Player target) {
        double cooldown = owner.getPoint2DFromHitbox().distance(target.getPoint2DFromHitbox()) * 4 - owner.STATS.DEXTERITY.binding.doubleValue() * 4;
        if (cooldown < 0.0) {
            cooldown = 0.0;
        }
        return cooldown;
    }

    @Override
    public double getCooldownDuration(Player owner, Player target) {
        return getCooldownDuration(target);
    }

    @Override
    public Transition getSkillAnimation(Player owner, Player target, Damage damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isReady(Player owner, Player target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class Mark {

        private final Circle mark = new Circle(10.0, Color.BROWN);
        private final Group root;

        public Mark(MouseEvent t, final Group root) {
            this.root = root;
            Circle target = NodeManager.checkPoint(t.getX(), t.getY());
            if (target != null) {
                mark.centerXProperty().bind(target.centerXProperty());
                mark.centerYProperty().bind(target.centerYProperty());
            } else {
                mark.centerXProperty().set(t.getX());
                mark.centerYProperty().set(t.getY());
            }
            root.getChildren().add(mark);
        }

        public void unbind() {
            mark.centerXProperty().unbind();
            mark.centerYProperty().unbind();
            root.getChildren().remove(mark);
        }

        public double getX() {
            return mark.centerXProperty().getValue();
        }

        public double getY() {
            return mark.centerYProperty().getValue();
        }
    }
}
