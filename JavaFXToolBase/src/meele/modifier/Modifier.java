/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele.modifier;

import basis.Player;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public interface Modifier {

    void hit(final Player target, final Damage damage);
}
