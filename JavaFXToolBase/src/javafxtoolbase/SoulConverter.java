/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * Gewicht: 20000g/SC-LVL (Mindestens LVL 1) 
 * ManaDrain LVL: 10 * MD-LVL / Second
 * LifeDrain LVL: 10 * LD-LVL / Second(TÖTEN) 
 * Efficiency LVL: 
 * Energy: ((10 * MD-LVL) + (10 * LD-LVL)) * Efficiency LVL/100
 *
 * @author tezuro
 */
class SoulConverter {

    final private PauseTransition timer = new PauseTransition(Duration.millis(1000.0));
    final private Attribute soulConverterLevel = new Attribute(1.0);
    final private Attribute manaDrainLevel = new Attribute();
    final private Attribute lifeDrainLevel = new Attribute();
    final private Attribute efficencyLevel = new Attribute();

    private boolean isActive = false;
    final private PlayerStats stats;

    public SoulConverter(PlayerStats stats) {
        this.stats = stats;
        timer.setCycleCount(1);
        timer.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
            }
        });
    }

    private void update() {
        if (isActive && drainMana() && drainLife()) {
            double energyPerSecond = ((10 * manaDrainLevel.binding.doubleValue()) + (10 * lifeDrainLevel.binding.doubleValue())) * efficencyLevel.binding.doubleValue() / 100;
            if (stats.energyCell.isEEquiped()) {
                stats.energyCell.loadFor(energyPerSecond);
            }
            timer.play();
        }
    }

    public void deactivate() {
        isActive = false;
    }

    public void activate() {
        isActive = true;
        timer.play();
    }

    private boolean drainMana() {
        stats.MANA.base.set(stats.MANA.base.get() - (manaDrainLevel.base.doubleValue() * 10));
        return stats.MANA.base.doubleValue() > 0.0;
    }

    private boolean drainLife() {
        stats.LIFE.base.set(stats.LIFE.base.get() - (lifeDrainLevel.base.doubleValue() * 10));
        return stats.LIFE.base.doubleValue() > 0.0;
    }

    public double getWeight() {
        return 20000 / soulConverterLevel.binding.doubleValue();
    }
}
