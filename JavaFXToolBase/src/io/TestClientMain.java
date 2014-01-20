/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import basis.BindableManager;
import basis.EffectCharge;
import basis.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ranged.TestWeapon;

public class TestClientMain extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Animation");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);
        GameSocketListener g = new GameSocketListener( GameSocketListener.CLIENT);

        Player player1 = new Player(root, scene, g.getSocket(), "Bofredo");

        BindableManager bm = new BindableManager();
        final TestWeapon tw = new TestWeapon(root, player1.getHitbox(), scene, g.getSocket());
        bm.addBindable(player1);
        bm.addBindable(tw);
        g.setBindableManager(bm);
        primaryStage.setScene(scene);
        
        
        primaryStage.show();
        EffectCharge effect = EffectCharge.create(root);
        //EffectEnergyBall effect = EffectEnergyBall.create(root);
        //EffectEnergyBall effect2 = EffectEnergyBall.create(root);
        effect.getTransition().play();
        //effect2.getTransition().play();
        
        effect.translateXProperty().bind(player1.getHitbox().centerXProperty());
        effect.translateYProperty().bind(player1.getHitbox().centerYProperty());
        
        //effect2.translateXProperty().bind(player1.getHitbox().centerXProperty());
        //effect2.translateYProperty().bind(player1.getHitbox().centerYProperty());

    }
}
