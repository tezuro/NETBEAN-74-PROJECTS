/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import io.GameSocketListener;
import javafx.application.Application;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ranged.TestWeapon;

/**
 *
 * @web java-buddy.blogspot.com
 */
public class TestServerMain extends Application {

    double rotateXValue, rotateYValue, anchorAngleX, anchorAngleY;
    final Group root = new Group();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setDepthTest(DepthTest.ENABLE);
        root.setOpacity(50.0);
        primaryStage.setTitle("Battlefield");
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setFullScreen(true);

        Scene scene = new Scene(root, Color.TRANSPARENT);

        scene.setCamera(new PerspectiveCamera());

        GameSocketListener t = new GameSocketListener(GameSocketListener.SERVER);

        final Player player1 = new Player(root, scene, t.getSocket(), "Teszuro");
        final TestWeapon tw = new TestWeapon(root, player1.playerHitBox, scene, t.getSocket());

        SkillFlash skill = new SkillFlash(root, 3, player1, KeyCode.SPACE, KeyCode.X, scene);

        SkillSpiderMines spiderMines = new SkillSpiderMines(root, 3, KeyCode.S, player1.playerHitBox, scene);

        SkillOrb orbSkill = new SkillOrb(root, player1.playerHitBox);
        orbSkill.addOrb(root, 3000.0, 500.0);
        orbSkill.addOrb(root, 6000.0, 1000.0);
        orbSkill.addOrb(root, 8000.0, 10000.0);
        orbSkill.readyUP();

        BindableManager bm = new BindableManager();
        bm.addBindable(tw);
        bm.addBindable(player1);
        t.setBindableManager(bm);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
