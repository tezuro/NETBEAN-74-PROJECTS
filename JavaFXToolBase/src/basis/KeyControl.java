/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
class KeyControl extends Transition {

    final public BooleanProperty UP = new SimpleBooleanProperty(false);
    final public BooleanProperty DOWN = new SimpleBooleanProperty(false);
    final public BooleanProperty RIGHT = new SimpleBooleanProperty(false);
    final public BooleanProperty LEFT = new SimpleBooleanProperty(false);
    final private Player owner;

    public KeyControl(final Player owner) {
        this.owner = owner;
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        setCycleDuration(Duration.INDEFINITE);
        play();
    }

    @Override
    protected void interpolate(double frac) {
        if (UP.getValue()) {
            owner.playerHitBox.centerYProperty().set(owner.playerHitBox.centerYProperty().doubleValue() - owner.STATS.MOVEMENT.binding.doubleValue());
        }
        if (DOWN.getValue()) {
            owner.playerHitBox.centerYProperty().set(owner.playerHitBox.centerYProperty().doubleValue() + owner.STATS.MOVEMENT.binding.doubleValue());
        }
        if (RIGHT.getValue()) {
            owner.playerHitBox.centerXProperty().set(owner.playerHitBox.centerXProperty().doubleValue() + owner.STATS.MOVEMENT.binding.doubleValue());
        }
        if (LEFT.getValue()) {
            owner.playerHitBox.centerXProperty().set(owner.playerHitBox.centerXProperty().doubleValue() - owner.STATS.MOVEMENT.binding.doubleValue());
        }
    }
}
