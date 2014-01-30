/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import basis.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafxtoolbase.ComboSystem.Skill;

/**
 *
 * @author tezuro
 */
public class ComboChain implements Skill {

    private final String comboName = "Dashing Strike";

    private final PauseTransition cooldown = new PauseTransition();
    private final SequentialTransition comboAnimation = new SequentialTransition();
    private final Player owner;

    private final List<Chainable> chainables = new ArrayList<>();

    public ComboChain(final Player owner) {

        this.owner = owner;
        owner.STATS.ongoingStatus.addListener(new ListChangeListener<Status>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends Status> change) {
                if (change.getAddedSubList().contains(Status.STUNNED) || change.getAddedSubList().contains(Status.DEAD)) {
                    comboAnimation.stop();
                    owner.STATS.IS_NOT_BUSY.set(true);
                }
            }
        }
        );
    }

    private void startCombo(final Player target) {
        comboAnimation.getChildren().clear();
        final Damage damage = new Damage(target, 0.0);
        double coolDownDuration = 0.0;
        for (Chainable t : chainables) {
            comboAnimation.getChildren().add(t.getSkillAnimation(owner, target, damage, comboAnimation));
            coolDownDuration = coolDownDuration + t.getCooldownDuration(owner, target);
        }
        comboAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                owner.STATS.IS_NOT_BUSY.set(true);
            }
        });
        owner.STATS.IS_NOT_BUSY.set(false);
        cooldown.setDuration(Duration.millis(coolDownDuration));
        ParallelTransition combo = new ParallelTransition(cooldown, comboAnimation);
        combo.play();
    }

    private boolean isReady(final Player target) {
        if (Animation.Status.RUNNING.equals(cooldown.getStatus())) {
            return false;
        }
        for (Chainable t : chainables) {
            if (!t.isReady(owner, target)) {
                return false;
            }
        }

        return true;
    }

    public void start(final Player target) {
        if (isReady(target)) {
            startCombo(target);
        }
    }

    @Override
    public boolean isSkillReady(final Player target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSupportSkill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean executeSkillOnTarget(Player target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static interface FirstSkillOnly {

    }

    public static interface Chainable {

        public double getCooldownDuration(final Player owner, final Player target);

        public Transition getSkillAnimation(final Player owner, final Player target, final Damage damage, final SequentialTransition comboChainAnimation);

        public boolean isReady(final Player owner, final Player target);

    }

}
