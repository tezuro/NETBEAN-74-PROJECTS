/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class NullifySkill {

    Integer skillLevel = 100;
    PauseTransition cooldown = new PauseTransition();
    ObservableList<Charge> charges = FXCollections.observableArrayList();;

    public NullifySkill() {
        cooldown.setCycleCount(1);
        cooldown.setDuration(Duration.seconds(20.00));
        cooldown.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                charges.add(new Charge());
            }
        });
        charges.addListener(new ListChangeListener<Charge>() {

            @Override
            public void onChanged(Change<? extends Charge> change) {
                if (charges.size() < (skillLevel / 100)) {
                    cooldown.play();
                }
            }
        });
        cooldown.play();

    }

    public boolean isAbilityNullified() {
        if (charges.isEmpty()) {
            return false;
        } else {
            charges.remove(0);
            return true;
        }
    }

    protected static class Charge {

        public Charge() {
        }
    }
}
