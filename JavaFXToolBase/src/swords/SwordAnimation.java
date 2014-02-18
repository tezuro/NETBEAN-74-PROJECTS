/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swords;

import basis.Xform;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class SwordAnimation extends Application {

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

        final Image image = new Image(getClass().getResourceAsStream("martin schwerter_2.png"));
        final ImageView sword = new ImageView(image);

        Scene scene = new Scene(root, Color.TRANSPARENT);

        scene.setCamera(new PerspectiveCamera());

        primaryStage.setScene(scene);
        primaryStage.show();

        getSwordAnimation(root, sword).play();
    }

    private Animation getSwordAnimation(final Group root, final ImageView sword) {
        Xform container = new Xform();
        
        container.translateXProperty().set(600);
        container.translateYProperty().set(300);
        
        
        container.getChildren().add(sword);
        root.getChildren().add(container);

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(3000),
                        new KeyValue(container.rx.angleProperty(), 90, Interpolator.LINEAR),
                        new KeyValue(container.rx.angleProperty(), 0),
                        new KeyValue(container.ry.angleProperty(), 0, Interpolator.LINEAR),
                        new KeyValue(container.ry.angleProperty(), 180),
                        new KeyValue(container.rz.angleProperty(), 0, Interpolator.LINEAR),
                        new KeyValue(container.rz.angleProperty(), 45)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);

        return timeline;
    }
}
