/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele.modifier;

import basis.Player;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafxtoolbase.Attribute;

/**
 *
 * @author tezuro
 */
public class ModifierStackingMovementSlow {

    final private Attribute slowDuration = new Attribute();
    final private Attribute slowIntensity = new Attribute();
    final private Attribute maxStackCount = new Attribute();
    final private Map<Player, Attribute> mapping = new HashMap<>();

    public void hit(final Player target) {
        if (!mapping.containsKey(target)) {
            mapping.put(target, new Attribute());
        }

        if (mapping.get(target).base.doubleValue() < maxStackCount.base.doubleValue()) {
            mapping.get(target).increaseBase(1.0);
            final PauseTransition timer = new PauseTransition(Duration.millis(slowDuration.base.doubleValue() * 10));
            timer.setCycleCount(1);
            timer.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mapping.get(target).reduceBase(1.0);
                    target.STATS.MOVEMENT.staticModifier.remove(timer);
                }
            });
            target.STATS.MOVEMENT.staticModifier.put(timer, new SimpleDoubleProperty(slowIntensity.base.doubleValue() * -0.7));
            timer.play();
        }
    }

}
