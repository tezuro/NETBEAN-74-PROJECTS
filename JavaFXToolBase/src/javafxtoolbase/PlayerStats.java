/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import basis.SkillFlash;
import basis.SkillJump;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import meele.MeeleWeapon;

/**
 *
 * @author tezuro
 */
public class PlayerStats {

    final private Attribute STRENGTH = new Attribute("strength");
    final public Attribute AGILITY = new Attribute("AGILITY");
    final public Attribute DEXTERITY = new Attribute("DEXTERITY");
    final private Attribute INTELIGENCY = new Attribute("INTELIGENCY");
    final private Attribute VITALITY = new Attribute("VITALITY");
    final private Attribute WILL = new Attribute("WILL");
    final private Attribute VISION = new Attribute("VISION");
    final public Attribute MOVEMENT = new Attribute("MOVEMENT", 20.0);
    final private Map<String, Attribute> ATTRIBUTE = new HashMap<>();

    final public ObservableList<Status> ongoingStatus = FXCollections.emptyObservableList();

    final public Attribute LIFE = new Attribute();
    final public Attribute MANA = new Attribute();

    final private DoubleBinding MAX_LIFE = VITALITY.binding.multiply(100.0).multiply(VITALITY.binding.divide(2.0));
    final private DoubleBinding MAX_MANA = INTELIGENCY.binding.multiply(100.0).multiply(INTELIGENCY.binding.divide(2.0));

    final private PauseTransition timer = new PauseTransition(Duration.millis(1000.0));
    
    final public BooleanProperty IS_NOT_BUSY = new SimpleBooleanProperty(true);

    final private DoubleBinding LIFE_REGENERATION = DEXTERITY.binding.divide(100.00).multiply(MAX_LIFE.divide(100.00));
    final private DoubleBinding MANA_REGENERATION = DEXTERITY.binding.divide(100.00).multiply(MAX_MANA.divide(100.00));
    
    final public DoubleBinding MAX_WEAPON_WEIGHT_ONE_HANDED = STRENGTH.binding.multiply(100.0);
    final public DoubleBinding MAX_WEAPON_WEIGHT_TWO_HANDED = STRENGTH.binding.multiply(200.0);
    
    

    final public NullifySkill nullify = new NullifySkill();

    final public ArmorSystem armor = new ArmorSystem(DEXTERITY);
    final private SoulConverter soulConverter = new SoulConverter(this);
    final public EnergyCell energyCell = new EnergyCell();
    final private EnergyShield energyShield = new EnergyShield();
    final public SkillFlash flash = null;
    final public SkillJump jump = null;
    final public ComboSystem CS;
    
    public MeeleWeapon meleeWeapon = MeeleWeapon.NO_WEAPON;

    public PlayerStats() {
        CS = new ComboSystem();
        ATTRIBUTE.put(STRENGTH.id, STRENGTH);
        ATTRIBUTE.put(AGILITY.id, AGILITY);
        ATTRIBUTE.put(DEXTERITY.id, DEXTERITY);
        ATTRIBUTE.put(INTELIGENCY.id, INTELIGENCY);
        ATTRIBUTE.put(VITALITY.id, VITALITY);
        ATTRIBUTE.put(WILL.id, WILL);
        ATTRIBUTE.put(VISION.id, VISION);
        LIFE.base.set(MAX_LIFE.doubleValue());
        MANA.base.set(MAX_MANA.doubleValue());
        timer.setCycleCount(1);
        timer.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean notDead = true;
                if (notDead) {
                    if (MAX_LIFE.doubleValue() < (LIFE.base.doubleValue() + LIFE_REGENERATION.doubleValue())) {
                        LIFE.base.set(LIFE.base.doubleValue() + LIFE_REGENERATION.doubleValue());
                    } else {
                        LIFE.base.set(MAX_LIFE.doubleValue());
                    }
                    if (MAX_MANA.doubleValue() < (MANA.base.doubleValue() + MANA_REGENERATION.doubleValue())) {
                        MANA.base.set(MANA.base.doubleValue() + MANA_REGENERATION.doubleValue());
                    } else {
                        MANA.base.set(MAX_MANA.doubleValue());
                    }
                    timer.play();
                }
            }
        });
    }

    public Attribute getAttributeByID(String id) {
        return ATTRIBUTE.get(id);
    }

    public Attribute getRandomAttribute() {
        Random r = new Random();
        return (Attribute) ATTRIBUTE.values().toArray()[r.nextInt(ATTRIBUTE.values().size())];
    }

}
