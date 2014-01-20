/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class ToolBox extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void grenadePath(final Group root, Double x, Double y) {
        //PathTransition grenadePath = PathTransitionBuilder.create().path(new Ellipse()).
    }

    public static Circle checkShapeIntersection(Circle block, ArrayList<Circle> nodes) {
        for (Circle c : nodes) {
            if (c != block) {
                Shape intersect = Shape.intersect(block, c);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    return c;
                }
            }
        }
        return null;
    }

    public static void explosion(final Group root, Double x, Double y) {
        final Circle ball = new Circle(x, y, 0);
        final RadialGradient gradient = new RadialGradient(0,
                .1,
                x,
                y,
                100,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.YELLOW),
                new Stop(1, Color.RED));
        ball.setFill(gradient);
        root.getChildren().add(ball);
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                new KeyValue(ball.radiusProperty(), 100, Interpolator.EASE_OUT),
                new KeyValue(ball.opacityProperty(), 0f)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(ball);
            }
        });
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void bulletReal(final Group root, final Group start, Double x, Double y) {
        Circle player2 = new Circle(40.0, Color.BLACK);
        player2.contains(y, y);
        //player2.intersects(player2.);
        //https://gist.github.com/jewelsea/1441960

    }

    public static void bullet(final Group root, final Group start, Double x, Double y) {
        final Line lineOfFire = new Line(start.getTranslateX(), start.getTranslateY(), x, y);
        lineOfFire.setEffect(new Glow(3.0));
        lineOfFire.setFill(Color.WHITESMOKE);
        lineOfFire.minWidth(1.0);
        lineOfFire.minHeight(1.0);
        root.getChildren().add(lineOfFire);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), lineOfFire);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // we are done remove us from scene
                root.getChildren().remove(lineOfFire);
            }
        });
        fadeTransition.play();
    }

    public static void bullet(final Group root, Double xStart, Double yStart, Double xEnd, Double yEnd) {
        //final Line lineOfFire = new Line(start.centerXProperty().getValue(), start.centerYProperty().getValue(), x, y);
        final Line lineOfFire = new Line(xStart, yStart, xEnd, yEnd);
        lineOfFire.setEffect(new Glow(3.0));
        lineOfFire.setFill(Color.WHITESMOKE);
        lineOfFire.minWidth(1.0);
        lineOfFire.minHeight(1.0);
        root.getChildren().add(lineOfFire);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), lineOfFire);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // we are done remove us from scene
                root.getChildren().remove(lineOfFire);
            }
        });
        fadeTransition.play();
    }

    public static void starSplash(final Group root, Double x, Double y) {
        final Ellipse ellipse = new Ellipse();
        ellipse.setRadiusX(20.0f);
        ellipse.setRadiusY(70.0f);
        ellipse.setCenterX(x);
        ellipse.setCenterY(y);
        ellipse.setRotate(180.0);

        final Text damage = new Text("3570");
        damage.setFont(new Font(Font.getDefault().getFamily(), 30));

        final Polyline star = new Polyline(x + 16.0, y + 28.0,//rechts oben
                x + 10.0, y + 10.0,
                x + 36.0, y + 5.0,//rechts
                x + 10.0, y - 10.0,
                x + 16.0, y - 28.0,//rechts unten
                x + 0.0, y - 15.0,
                x - 16.0, y - 28.0,//link unten
                x - 10.0, y - 10.0,
                x - 36.0, y - 5.0,//links
                x - 10.0, y + 10.0,
                x - 16.0, y + 28.0,//links oben
                x - 0.0, y + 15.0,
                x + 16.0, y + 28.0);//rechts oben
        star.setFill(Color.RED);
        //star.setOpacity(y);

        root.getChildren().add(star);
        root.getChildren().add(damage);

        FadeTransition fadeTransitionStar = new FadeTransition(Duration.millis(1500), star);
        fadeTransitionStar.setFromValue(0.7);
        fadeTransitionStar.setToValue(0f);
        fadeTransitionStar.setCycleCount(1);
        fadeTransitionStar.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // we are done remove us from scene
                root.getChildren().remove(star);
            }
        });

        PathTransition pathTransitionStar = PathTransitionBuilder.create()
                .node(star)
                .path(ellipse)
                .interpolator(Interpolator.LINEAR)
                .duration(Duration.millis(3000))
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .build();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), damage);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // we are done remove us from scene
                root.getChildren().remove(damage);
            }
        });

        PathTransition pathTransition = PathTransitionBuilder.create()
                .node(damage)
                .path(ellipse)
                .interpolator(Interpolator.LINEAR)
                .duration(Duration.millis(3000))
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .build();

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                fadeTransitionStar,
                pathTransitionStar,
                fadeTransition,
                pathTransition);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();

    }

    public static void doIt(final Group root, MouseEvent event) {
        final Ellipse ellipse = new Ellipse();
        ellipse.setRadiusX(20.0f);
        ellipse.setRadiusY(70.0f);
        ellipse.setCenterX(event.getX());
        ellipse.setCenterY(event.getY());
        ellipse.setRotate(180.0);
        final Text damage = new Text("3570");
        damage.setFont(new Font(Font.getDefault().getFamily(), 30));

        root.getChildren().add(damage);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), damage);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // we are done remove us from scene
                root.getChildren().remove(damage);
            }
        });


        PathTransition pathTransition = PathTransitionBuilder.create()
                .node(damage)
                .path(ellipse)
                .interpolator(Interpolator.LINEAR)
                .duration(Duration.millis(3000))
                .orientation(PathTransition.OrientationType.NONE)
                .cycleCount(1)
                .build();

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                fadeTransition,
                pathTransition);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();
    }

    public static Node getPictureNode() {
        Image i = new Image("background/artleo.com-7014.jpg");
        ImageView iv = new ImageView(i);
        iv.setScaleY(1.0);
        iv.setScaleX(1.0);

        return iv;
    }

    public void grenade() {
        //Sphere grenade = new Sphere(50.0, 50);
        //grenade.set
    }
}
