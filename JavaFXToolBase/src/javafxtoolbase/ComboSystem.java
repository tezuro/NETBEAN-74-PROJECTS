/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import basis.BattleManagment;
import basis.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tezuro
 */
public class ComboSystem {

    /**
     *
     * @param origin
     * @param startSkill
     * @param target
     * @return 
     */
    public Player combo(Player origin, Comboable startSkill, Player target) {
        List<Player> followUpList;
        if (origin.alliance.equals(target.alliance)) {
            followUpList = BattleManagment.getFriends(target);
        } else {
            followUpList = BattleManagment.getFoe(target);
        }
        for (Player p : followUpList) {
            if(checkForFollowUpSkills(p)){
                return p;
            }
        }
        return null;
    }
    
    public void startCombo(Player origin, Player target){
        
    }

    private boolean checkForFollowUpSkills(Player p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class Comboable { 
        
        private final List<Damage> damageList = new ArrayList<>();

        public Comboable() {
        }
        
        public void finish(){
            
        }
    }

}
