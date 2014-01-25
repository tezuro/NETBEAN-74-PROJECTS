/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele.modifier;

import basis.Player;
import javafxtoolbase.Attribute;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public class ModifierManaCombustion {

    private final Attribute combustionLevel = new Attribute();

    public void hit(final Player target, final Damage damage) {
        if (target.STATS.MANA.reduceBase(combustionLevel.base.doubleValue())) {
            damage.damage.set(damage.damage.doubleValue() + combustionLevel.base.doubleValue());
        }
    }
}
