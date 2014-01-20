/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import java.util.Random;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class EffectCharge extends Group {

    private final Group root;
    private final ParallelTransition parallelTransition = new ParallelTransition();

    private EffectCharge(final Group root) {
        this.root = root;
        Random generator = new Random();

        for (int i = 0; i < 200; i++) {
            double randomDouble = 250.0 + (50.0 * generator.nextDouble());
            double randomX = randomDouble * generator.nextDouble();
            double randomY = randomDouble - randomX;
            Line path = new Line();
            getChildren().add(path);
            if (i % 4 == 0) {
                path.setStartX(randomX);
                path.setStartY(randomY);
            }
            if (i % 4 == 1) {
                path.setStartX(-randomX);
                path.setStartY(randomY);
            }
            if (i % 4 == 2) {
                path.setStartX(randomX);
                path.setStartY(-randomY);
            }
            if (i % 4 == 3) {
                path.setStartX(-randomX);
                path.setStartY(-randomY);
            }
            path.setEndX(0.0);
            path.setEndY(0.0);
            path.setOpacity(0.0);

            Circle ball = new Circle(5.0, Color.BLUE);
            getChildren().add(ball);

            final Duration delay = Duration.millis(1000.0 * generator.nextDouble());

            ScaleTransition scale = new ScaleTransition(Duration.millis(1000.0), ball);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.setDelay(delay);
            scale.setFromX(0.0);
            scale.setFromY(0.0);
            scale.setCycleCount(Timeline.INDEFINITE);

            PathTransition pathTransitionStar = PathTransitionBuilder.create()
                    .node(ball)
                    .path(path)
                    .interpolator(Interpolator.EASE_OUT)
                    .duration(Duration.millis(1000))
                    .orientation(PathTransition.OrientationType.NONE)
                    .cycleCount(Timeline.INDEFINITE)
                    .delay(delay)
                    .build();
            parallelTransition.getChildren().add(pathTransitionStar);
            parallelTransition.getChildren().add(scale);
        }
    }

    public static EffectCharge create(final Group root) {
        EffectCharge effect = new EffectCharge(root);
        root.getChildren().add(effect);
        return effect;
    }

    public ParallelTransition getTransition() {
        return parallelTransition;
    }

    public void killMe() {
        parallelTransition.stop();
        root.getChildren().remove(this);
    }
}
