/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import javafx.animation.PauseTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class ArmorPart {

    public BodyLocation location = BodyLocation.chest;
    public SimpleDoubleProperty accuracyDebuff = new SimpleDoubleProperty(0.0);
    private final Attribute armor = new Attribute();
    private final Attribute maxdebuff = new Attribute(5000.0);
    private final PauseTransition timer = new PauseTransition(Duration.millis(100.0));
    private boolean isEquiped = false;
    final private DoubleBinding dexterity;

    public ArmorPart(final DoubleBinding dexterity) {
        this.dexterity = dexterity;
        timer.setCycleCount(1);
        timer.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
            }
        });
    }

    private void update() {
        if (isEquiped) {
            double newValue = accuracyDebuff.get() - (dexterity.get() / 100);
            if (newValue < 0.0) {
                accuracyDebuff.set(0.0);
            } else {
                accuracyDebuff.set(newValue);
            }
            timer.play();
        }
    }

    public void equip() {
        isEquiped = true;
        timer.play();
    }

    public void unequip() {
        isEquiped = false;
    }

    public double calcArmorReduction(Damage damage) {
        double debuffAddition = 100.0 - ((damage.originalDamage + 1) / (armor.binding.doubleValue() * 200 + 1));
        if ((accuracyDebuff.get() + debuffAddition) > maxdebuff.binding.doubleValue()) {
            accuracyDebuff.set(maxdebuff.binding.doubleValue());
        } else {
            accuracyDebuff.set(accuracyDebuff.get() + debuffAddition);
        }
        return debuffAddition;
    }

}
