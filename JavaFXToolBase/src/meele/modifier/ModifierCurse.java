/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele.modifier;

import basis.Player;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafxtoolbase.Attribute;
import javafxtoolbase.Damage;
import javafxtoolbase.Status;

/**
 *
 * @author tezuro
 */
public class ModifierCurse implements Modifier{

    private final Attribute modifierLevel = new Attribute();
    private final Player owner;
    private final Map<String, Curse> targetMapping = new HashMap<>();

    public ModifierCurse(final Player owner) {
        this.owner = owner;
        owner.STATS.ongoingStatus.addListener(new ListChangeListener<Status>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends Status> change) {
                if (change.getAddedSubList().contains(Status.DEAD)) {
                    for (Curse c : targetMapping.values()) {
                        c.cleanUp();
                    }
                    targetMapping.clear();
                }
            }
        });
    }
    @Override
    public void hit(final Player target, final Damage damage) {
        int count = (int) (modifierLevel.base.doubleValue() / 100);
        for (int i = 0; i < count; i++) {
            Attribute randomAttributeFromTarget = target.STATS.getRandomAttribute();
            final String key = target.toString() + randomAttributeFromTarget.id;
            if (targetMapping.containsKey(key)) {
                targetMapping.get(key).increaseCurseCount();
            } else {
                ListChangeListener listener = new ListChangeListener<Status>() {

                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Status> change) {
                        if (change.getAddedSubList().contains(Status.DEAD)) {
                            targetMapping.get(key).cleanUp();
                            targetMapping.remove(key);
                        }
                    }
                };
                Curse curse = new Curse(target, listener, owner.STATS.getAttributeByID(randomAttributeFromTarget.id), randomAttributeFromTarget);
                curse.init();
                targetMapping.put(key, curse);
            }
        }
    }

    private static class Curse {

        private final ListChangeListener listener;
        private final SimpleDoubleProperty targetProperty = new SimpleDoubleProperty(-1.0);
        private final SimpleDoubleProperty ownerProperty = new SimpleDoubleProperty(1.0);
        private final Attribute randomAttributeTarget;
        private final Attribute randomAttributeOwner;
        private final Player target;

        public Curse(Player target, ListChangeListener listener, Attribute randomAttributeTarget, Attribute randomAttributeOwner) {
            this.listener = listener;
            this.target = target;
            this.randomAttributeTarget = randomAttributeTarget;
            this.randomAttributeOwner = randomAttributeOwner;
            target.STATS.ongoingStatus.addListener(listener);
        }

        public void init() {
            randomAttributeTarget.staticModifier.put(this, targetProperty);
            randomAttributeOwner.staticModifier.put(this, ownerProperty);
        }

        public void increaseCurseCount() {
            targetProperty.set(targetProperty.doubleValue() - 1.0);
            ownerProperty.set(ownerProperty.doubleValue() + 1.0);
        }

        public void cleanUp() {
            target.STATS.ongoingStatus.removeListener(listener);
            randomAttributeTarget.staticModifier.remove(this);
            randomAttributeOwner.staticModifier.remove(this);
        }

    }

}
