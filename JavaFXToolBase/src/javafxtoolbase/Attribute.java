/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author tezuro
 */
public class Attribute {

    public final String id;

    final public DoubleProperty base = new SimpleDoubleProperty(0);
    final public Map<Object, DoubleProperty> staticModifier = new HashMap<>();
    final public Map<Object, DoubleProperty> percentModifier = new HashMap<>();
    final public DoubleBinding binding = new CalculatedBinding(base, staticModifier, percentModifier);

    public Attribute() {
        this.id = "NOT_SET";
    }

    public Attribute(String id) {
        this.id = id;
    }

    public Attribute(double baseValue) {
        this.id = "NOT_SET";
        base.set(baseValue);
    }

    public Attribute(String id, double baseValue) {
        this.id = id;
        base.set(baseValue);
    }

    public boolean reduceBase(double d) {
        base.set(base.doubleValue() - d);
        return base.doubleValue() <= 0;
    }
    public void increaseBase(double d) {
        base.set(base.doubleValue() + d);
    }

}
