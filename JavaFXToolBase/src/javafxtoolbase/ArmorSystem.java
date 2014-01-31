/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.beans.binding.DoubleBinding;

/**
 *
 * @author tezuro
 */
public class ArmorSystem {
    private final Map<BodyLocation,ArmorPart> equipedArmor = new HashMap<>();
    final private Attribute dexterity;
    public DoubleBinding accuracyDebuff;

    ArmorSystem(Attribute dexterity) {
        this.dexterity = dexterity;
        equipedArmor.put(BodyLocation.leftUpperArm, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.rightUpperArm, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.leftLowerArm, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.rightLowerArm, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.head, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.chest, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.leftUpperLeg, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.rightUpperLeg, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.leftLowerLeg, new ArmorPart(dexterity.binding));
        equipedArmor.put(BodyLocation.rightLowerLeg, new ArmorPart(dexterity.binding));
        rebind();
    }
    
    private void rebind(){
        accuracyDebuff = equipedArmor.get(BodyLocation.leftUpperArm).accuracyDebuff
                .add(equipedArmor.get(BodyLocation.rightUpperArm).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.leftLowerArm).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.rightLowerArm).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.chest).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.head).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.leftUpperLeg).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.leftLowerLeg).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.rightUpperLeg).accuracyDebuff)
                .add(equipedArmor.get(BodyLocation.rightLowerLeg).accuracyDebuff);
    }

    public Damage attack(Damage damage) {
        Random r = new Random();
        double hit = r.nextDouble();
        

        if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.leftUpperArm).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.rightUpperArm).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.leftLowerArm).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.rightLowerArm).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.chest).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.head).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.leftUpperLeg).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.leftLowerLeg).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.rightUpperLeg).calcArmorReduction(damage));
        } else if (0.0 <= hit && hit <= 0.015) {
            damage.armorReduction.set(equipedArmor.get(BodyLocation.rightLowerLeg).calcArmorReduction(damage));
        }
        return damage;
    }

    public void equip(final ArmorPart armorpart) {
        equipedArmor.put(armorpart.location, armorpart);
        rebind();
    }
    
    public void unequip(BodyLocation location) {
        equipedArmor.put(location, new ArmorPart(dexterity.binding));
        rebind();
    }
}
