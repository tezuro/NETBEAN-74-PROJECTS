/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele.modifier;

import javafxtoolbase.Attribute;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public class ModifierArmorPiercing {

    private final Attribute piercingLevel = new Attribute();

    public void hit(Damage damage) {
        damage.armorReduction.set(getPiercedArmor(damage.armorReduction.doubleValue()));
    }

    private double getPiercedArmor(double armorDamageReduction) {
        return (1.0 - piercingLevel.base.doubleValue() / 1000) * armorDamageReduction;
    }

}
