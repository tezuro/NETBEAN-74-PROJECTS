 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import java.util.Map;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author tezuro
 */
public class CalculatedBinding extends DoubleBinding {

    private DoubleProperty attribute = new SimpleDoubleProperty(0);
    private final Map<Object, DoubleProperty> staticModifier;
    private final Map<Object, DoubleProperty> percentModifier;

    public CalculatedBinding(final DoubleProperty attribute, final Map<Object, DoubleProperty> staticModifier, final Map<Object, DoubleProperty> percentModifier) {
        this.attribute = attribute;
        this.staticModifier = staticModifier;
        this.percentModifier = percentModifier;
    }

    @Override
    protected double computeValue() {
        double result = attribute.doubleValue();
        for (DoubleProperty dp : staticModifier.values()) {
            result = result + dp.doubleValue();
        }
        for (DoubleProperty dp : percentModifier.values()) {
            result = result * dp.doubleValue();
        }
        return result;
    }

}
