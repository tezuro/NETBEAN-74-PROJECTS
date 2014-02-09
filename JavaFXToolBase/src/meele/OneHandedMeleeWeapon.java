/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele;

import basis.Player;
import basis.Xform;
import javafx.animation.ParallelTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafxtoolbase.Damage;
import javafxtoolbase.UnpurgeableBonus;

/**
 *
 * @author tezuro
 */
public class OneHandedMeleeWeapon extends AbstractMeleeWeapon {

    public OneHandedMeleeWeapon(final Player owner, final String weaponName, final double weight) {
        super(owner, weaponName, weight);
    }

    @Override
    public boolean equipWeapon() {

        if (weaponWeight > owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.doubleValue()) {
            return false;
        } else {
            owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.addListener(weightListener);
        }

        for (UnpurgeableBonus bonus : weaponBonus) {
            bonus.equip(owner);
        }
        return true;
    }

    @Override
    public boolean unequipWeapon() {
        owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.removeListener(weightListener);
        for (UnpurgeableBonus bonus : weaponBonus) {
            bonus.unequip(owner);
        }
        return true;
    }

    @Override
    public TYPE getType() {
        return TYPE.ONE_HAND;
    }

    @Override
    void prepareAnimation(ParallelTransition animation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void prepareAnimation(ParallelTransition animation, Damage damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void testAnimation(final Group root, final Circle ownerHitBox, final Scene scene) {
        EventHandler<MouseEvent> mouselistener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if(MouseButton.PRIMARY.equals(t.getButton())){
                    ownerHitBox.centerXProperty();
                    ownerHitBox.centerYProperty();
                    final Xform animationNode = new Xform();
                    
                    
                }
                
            }
        };

        scene.onMousePressedProperty().set(mouselistener);
    }

}
