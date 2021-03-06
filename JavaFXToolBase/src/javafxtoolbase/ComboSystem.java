/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import basis.BattleManagment;
import basis.Player;
import basis.SkillType;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author tezuro
 */
public class ComboSystem {

    /**
     * SingelPlayer -> defined ComboChains MultiPlayer -> start combo check all
     * ready/inRange skills of all other (freind or foe check) and let them
     * execute them on the target
     *
     * MultiPlayer can trigger ComboChains too
     *
     * all skills are executed at once
     */
    /**
     * wenn combokey gedrueckt wird ist start combo auf true
     *
     * on release wieder auf false
     *
     * falls ein comboable skill gestartet wurde wird wieder auf false gestellt
     */
    private final BooleanProperty startCombo = new SimpleBooleanProperty(false);

    /**
     *
     * @param origin
     * @param target
     * @param comboCount
     */
    public void combo(final Player origin, final Player target, final int comboCount) {
        List<Player> followUpList;
        if (origin.alliance.equals(target.alliance)) {
            followUpList = BattleManagment.getFriends(target);
        } else {
            followUpList = BattleManagment.getFoe(target);
        }
        for (Player p : followUpList) {
            if (executeNextSkillInCombo(origin, p, target, comboCount)) {
                break;
            }
        }
    }

    public void startCombo(Player origin, Player target) {
        combo(origin, target, 0);
    }

    private boolean executeNextSkillInCombo(final Player origin, final Player followUpPlayer, final Player target, final int comboCount) {
        final SkillType lookingForSupportSkill = origin.alliance.equals(target.alliance)?SkillType.SUPPORT:SkillType.OFFENSIVE;

        for (Skill skill : followUpPlayer.comboSkills) {
            if (skill.isSupportSkill().equals(lookingForSupportSkill)  && skill.isSkillReady(target)) {
                /**
                 * skillausfuehren und true zurueckgeben weil der rest wieder in
                 * der skill verarbeitung gemacht wird Rekursive bis nichts mehr
                 * gefunden wird was followup machen koennte
                 */
                skill.executeSkillOnTarget(target);
                return true;
            }
        }
        /**
         * skillListe durchgehen und schauen ob ein combo skill ready ist wenn
         * das der fall ist true zurueck geben und den skill ausfuehren
         */
        return false;
    }

    public interface Skill {

        public boolean isSkillReady(final Player target);

        public SkillType isSupportSkill();

        public boolean executeSkillOnTarget(final Player target);

    }

}
