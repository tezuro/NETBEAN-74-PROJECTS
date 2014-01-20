/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxtoolbase;

import java.util.List;

/**
 *
 * @author tezuro
 */
public class Damage {
    
    final public double originalDamage;
    private double damage = 0.0;
    private double armorReduction = 0.0;
    
    public Damage(double damage) {
        this.originalDamage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
    
    
    public void setPlayerStats(PlayerStats players){
        
    }
    
    public void setArmorDamageReduction(double armorReduction){
        this.armorReduction = armorReduction;
    }
    public double getArmorDamageReduction(){
        return armorReduction;
    }
    
    public void setResistances(List<Resistance> listOfResistances){
        
    }
    
    public void doTheDamage(){
        
    }
    
}
