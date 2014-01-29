/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import basis.Player;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author tezuro
 */
public class Damage {

    public final IntegerProperty comboCount = new SimpleIntegerProperty(0);
    public final BooleanProperty isCritical = new SimpleBooleanProperty(false);
    final public double originalDamage;
    public final DoubleProperty damage = new SimpleDoubleProperty();
    public final DoubleProperty armorReduction = new SimpleDoubleProperty(0.0);
    private final Player target;

    public Damage(Player target, double damage) {
        this.originalDamage = damage;
        this.damage.set(damage);
        this.target = target;
    }

    public void doTheDamage() {
        target.STATS.LIFE.reduceBase(damage.doubleValue());
    }

}
