/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import io.GenericSocket;
import io.IBindable;
import static io.SocketBinding.X_CONSTANT;
import static io.SocketBinding.Y_CONSTANT;
import io.SocketBindingEmpfaenger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafxtoolbase.ComboSystem;
import javafxtoolbase.ComboSystem.Skill;
import javafxtoolbase.PlayerStats;

/**
 *
 * @author tezuro
 */
public class Player implements IBindable {

    public final static String PLAYER_CONSTANT = "P";
    private final String playerName;
    private final GenericSocket gameSocket;
    private final Group root;
    final Circle playerHitBox = new Circle(40.0, Color.GREEN);
    public AllianceBanner alliance = AllianceBanner.BLACK;
    public final PlayerStats STATS = new PlayerStats();
    public final ComboSystem CS = new ComboSystem();
    public final Set<Skill> comboSkills = new HashSet<>();
    private final XformKeySupportInterpolation keysupport;
    

    
    
    private final HashMap<String, SocketBindingEmpfaenger> enpfaengerBindings = new HashMap<>();
    private final ChangeListener xListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1) {
            gameSocket.sendMessage(PLAYER_CONSTANT + "|" + playerHitBox.getId() + "|" + X_CONSTANT + "|" + playerHitBox.centerXProperty().get() + "|");
        }
    };
    private final ChangeListener yListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1) {
            gameSocket.sendMessage(PLAYER_CONSTANT + "|" + playerHitBox.getId() + "|" + Y_CONSTANT + "|" + playerHitBox.centerYProperty().get() + "|");
        }
    };

    public Player(final Group root, Scene scene, GenericSocket socket, String name) {
        playerName = name;
        this.root = root;
        gameSocket = socket;
        playerHitBox.setId(IdGenerator.getNextID());
        playerHitBox.centerXProperty().addListener(xListener);
        playerHitBox.centerYProperty().addListener(yListener);
        root.getChildren().add(playerHitBox);
        keysupport = new XformKeySupportInterpolation(this, scene);
        
    }

    public Point2D getPoint2DFromHitbox(){
        return new Point2D(playerHitBox.centerXProperty().doubleValue(), playerHitBox.centerYProperty().doubleValue());
    }
    
    public Circle getHitbox(){
        return playerHitBox;
    }
    /**
     *
     * @param data
     */
    @Override
    public void proccessData(String[] data) {
        SocketBindingEmpfaenger binding = enpfaengerBindings.get(data[1]);
        /**
         * hier muss noch hin was gemacht werden muss 
         * 1 daueraktuallisieren bei player objekten (diese muessen als node hinzugefuegt werden) 
         * 2 starten von linearen objectbewegungen 
         * 3 loeschen von objecten 
         * 4 wenn ein player object angelegt wird muss auch dieser player sich senden
         * so das er beim empfaenger sichbar ist
         */
        if (binding != null) {
            binding.updateMe(data);
        } else {
            Circle circle = new Circle(40.0, Color.GREEN);
            NodeManager.addNode(circle);
            enpfaengerBindings.put(data[1], new SocketBindingEmpfaenger(root, circle, data[1]));
        }
    }

    @Override
    public String getID() {
        return PLAYER_CONSTANT;
    }

    boolean isSkillEquiped(String SKILL_ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
