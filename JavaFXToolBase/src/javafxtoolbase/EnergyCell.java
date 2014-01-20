/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

/**
 *
 * @author tezuro
 */
public class EnergyCell {

    final private Attribute energyCellLevel = new Attribute();
    final private Attribute energyStorageLevel = new Attribute();

    final private Attribute energy = new Attribute();
    
    private boolean isEquiped = false;
    
    public void equip(){
        isEquiped = true;
    }
    public void unequip(){
        isEquiped = false;
    }
    public boolean isEEquiped(){
        return isEquiped;
    }

    public void loadFor(double energyPerSecond) {
        energy.base.set(energy.base.doubleValue() + energyPerSecond);
        if (energy.base.doubleValue() > (energyCellLevel.base.doubleValue() * energyStorageLevel.base.doubleValue() * 2)) {
            energy.base.set(energyCellLevel.base.doubleValue() * energyStorageLevel.base.doubleValue() * 2);
        }
    }

    public boolean getEnergy(double requestedEnergy) {
        if (energy.base.doubleValue() < requestedEnergy) {
            return false;
        } else {
            energy.base.set(energy.base.doubleValue() - requestedEnergy);
            return true;
        }
    }

    public double getWeight() {
        if(isEquiped){
            return 0.0;
        }
        return 40000 / energyStorageLevel.base.doubleValue();
    }

}
