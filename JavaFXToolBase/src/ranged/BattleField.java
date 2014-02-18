/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ranged;

import basis.EffectCharge;
import basis.Xform;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafxtoolbase.ToolBox;

/**
 *
 * @web java-buddy.blogspot.com
 */
public class BattleField extends Application {

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
        scene.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override
            public void handle(ZoomEvent t) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        scene.setCamera(new PerspectiveCamera());

        final Node battlefield = Grid.createGrid();

        final BodyModel bodyModel = new BodyModel();

        bodyModel.createModel();
        final Xform modelContainer = new Xform();
        modelContainer.rz.setAngle(90.0);
        modelContainer.rx.setAngle(90.0);
        modelContainer.getChildren().add(bodyModel);
        bodyModel.setTranslateZ(bodyModel.getTranslateZ() + 200.0);
        //final XformKeySupport keySupport;
        //keySupport = new XformKeySupport();
        //keySupport.getChildren().add(modelContainer);

        battlefield.setTranslateX(-400.0);
        battlefield.setTranslateY(-350.0);

        final Xform container = new Xform();
        container.getChildren().add(battlefield);
        //container.getChildren().add(keySupport);
        root.getChildren().add(container);
        container.setTranslateX(400.0);
        container.setTranslateY(400.0);
        //Mouse button pressed - clear path and start from the current X, Y.
        scene.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                anchorAngleX = container.rx.getAngle();
                rotateXValue = event.getX();
                //anchorAngleX = battlefieldX.getRotate();

                anchorAngleY = container.ry.getAngle();
                rotateYValue = event.getY();
                //anchorAngleY = battlefieldY.getRotate();
                ToolBox.explosion(root, event.getX(), event.getY());
                //ToolBox.bullet(root,  event.getX(), event.getY());
                //ToolBox.energyField(root, event.getX(), event.getY());
                ToolBox.starSplash(root, event.getX(), event.getY());
                //path.getElements().clear();
                //path.getElements().add(new MoveTo(event.getX(), event.getY()));
            }
        });

        //Mouse dragged - add current point.
        scene.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                container.rx.setAngle(anchorAngleX + rotateYValue - event.getSceneY());
                //battlefieldX.setRotate(anchorAngleX + rotateYValue -  event.getSceneY());

                container.ry.setAngle(anchorAngleY + rotateXValue - event.getSceneX());
                //battlefieldY.setRotate(anchorAngleY + rotateXValue -  event.getSceneX());
                //ToolBox.bullet(root, event.getX(), event.getY());
                //path.getElements().add(new LineTo(event.getX(), event.getY()));
            }
        });

        //Mouse button released,  finish path.
        scene.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                //keySupport.keyPressed(t);
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                //.keyReleased(t);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

        EffectCharge effect = EffectCharge.create(container);
        effect.getTransition().play();
        //WindowBucket g = new WindowBucket();
        //g.start(primaryStage);
    }
}
