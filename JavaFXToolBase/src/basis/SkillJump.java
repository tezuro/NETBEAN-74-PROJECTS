/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafxtoolbase.Attribute;
import javafxtoolbase.ComboChain.Chainable;
import javafxtoolbase.ComboChain.FirstSkillOnly;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public class SkillJump implements Chainable, FirstSkillOnly {

    private final Attribute skillRange = new Attribute();

    private final Player owner;

    private final PauseTransition cooldown = new PauseTransition();

    public EventHandler<MouseEvent> mouselistener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if (MouseEvent.MOUSE_CLICKED.equals(t.getEventType()) && MouseButton.SECONDARY.equals(t.getButton())) {
                jumpTo(t.getX(), t.getY());
            }
        }
    };

    public SkillJump(Player owner, final Scene scene) {
        this.owner = owner;
        cooldown.setCycleCount(1);
        scene.addEventHandler(MouseEvent.ANY, mouselistener);
    }

    public double activate(final Player target) {
        jumpTo(target.getHitbox().getCenterX(), target.getHitbox().getCenterY());
        return getCooldown();
    }

    public void jumpTo(double x, double y) {
        cooldown.setDuration(Duration.millis(getCooldown()));
        cooldown.play();
        PathTransition jump = new PathTransition();
        jump.setCycleCount(1);
        jump.setDuration(Duration.millis(getJumpDuration()));
        jump.setPath(new Line(owner.getHitbox().getCenterX(), owner.getHitbox().getCenterY(), x, y));
        jump.play();
        owner.STATS.MANA.reduceBase(owner.getPoint2DFromHitbox().distance(new Point2D(x, y)) * 4);
    }

    public void startCooldown() {
        cooldown.setDuration(Duration.millis(getCooldown()));
        cooldown.play();
    }

    public boolean isReady() {
        return !Animation.Status.RUNNING.equals(cooldown.getStatus());
    }

    public boolean isInRange(Player target) {
        return skillRange.binding.doubleValue() >= owner.getPoint2DFromHitbox().distance(target.getPoint2DFromHitbox());
    }

    public double getCooldown() {
        return 500.0 - owner.STATS.DEXTERITY.binding.doubleValue();
    }

    private double getJumpDuration() {
        return 500.0 - owner.STATS.AGILITY.binding.doubleValue();
    }

    @Override
    public double getCooldownDuration(Player owner, Player target) {
        return getCooldown();
    }

    @Override
    public Transition getSkillAnimation(final Player owner, final Player target, final Damage damage, final SequentialTransition comboChainAnimation) {
        PathTransition jump = new PathTransition();
        jump.setCycleCount(1);
        jump.setDuration(Duration.millis(getJumpDuration()));
        jump.setPath(new Line(owner.getHitbox().getCenterX(), owner.getHitbox().getCenterY(), target.getHitbox().getCenterX(), target.getHitbox().getCenterY()));
        jump.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                damage.comboCount.set(damage.comboCount.get() + 1);
            }
        });
        return jump;
    }

    @Override
    public boolean isReady(Player owner, Player target) {
        return isReady();
    }
}
