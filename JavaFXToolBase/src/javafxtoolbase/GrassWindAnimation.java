/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
class GrassWindAnimation extends Transition {

    final private Duration animationTime = Duration.seconds(3);
    final private DoubleProperty phase = new SimpleDoubleProperty(0);
    final private Timeline tl = new Timeline(Animation.INDEFINITE);

    public GrassWindAnimation(List<Blade> blades) {

        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        setCycleDuration(animationTime);
        for (Blade blade : blades) {
            blade.phase.bind(phase);
        }
    }

    @Override
    protected void interpolate(double frac) {
        phase.set(frac * 2 * Math.PI);
    }
}
