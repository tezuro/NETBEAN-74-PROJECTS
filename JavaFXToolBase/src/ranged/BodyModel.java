/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ranged;

import basis.Xform;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author tezuro
 */
public class BodyModel extends Group {

    private Timeline currentTimeline;
    final private Xform body = createBodyLine(60.0);
    final private Xform leftUpperArm = createBodyLine(30.0);
    final private Xform leftArm = createBodyLine(30.0);
    final private Xform leftUpperLeg = createBodyLine(-30.0);
    final private Xform leftLeg = createBodyLine(-30.0);
    final private Xform rightUpperArm = createBodyLine(30.0);
    final private Xform rightArm = createBodyLine(30.0);
    final private Xform rightUpperLeg = createBodyLine(-30.0);
    final private Xform rightLeg = createBodyLine(-30.0);
    final private Xform leftFoot = createBodyLine(10.0);
    final private Xform rightFoot = createBodyLine(10.0);

    private Timeline standardMove() {
        Timeline result = new Timeline();
        result.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(translateYProperty(), getTranslateY())),
                new KeyFrame(Duration.seconds(1.0),
                        new KeyValue(translateYProperty(), getTranslateY() + 20.0, Interpolator.LINEAR)));
        result.setCycleCount(Animation.INDEFINITE);
        result.setAutoReverse(true);
        result.play();
        return result;
    }

    public void createModel() {

        getChildren().add(createBody());
        leftUpperArm.rx.setAngle(-150.0);
        rightUpperArm.rx.setAngle(150.0);
        leftArm.rx.setAngle(-30.0);
        rightArm.rx.setAngle(30.0);

        leftUpperLeg.rx.setAngle(-30.0);
        rightUpperLeg.rx.setAngle(30.0);
        leftLeg.rx.setAngle(45.0);
        rightLeg.rx.setAngle(-45.0);

        leftFoot.rz.setAngle(90);
        rightFoot.rz.setAngle(90.0);

        body.rx.setAngle(180);
        body.ry.setAngle(90);

        standardMove();

    }

    private Node createBody() {
        body.getChildren().addAll(leftUpperArm, rightUpperArm, leftUpperLeg, rightUpperLeg);

        leftUpperArm.getChildren().add(leftArm);
        rightUpperArm.getChildren().add(rightArm);

        leftUpperLeg.getChildren().add(leftLeg);
        rightUpperLeg.getChildren().add(rightLeg);

        leftLeg.getChildren().add(leftFoot);
        rightLeg.getChildren().add(rightFoot);

        rightUpperArm.setTranslateY(60.0);
        leftUpperArm.setTranslateY(60.0);
        leftArm.setTranslateY(30.0);
        rightArm.setTranslateY(30.0);

        leftLeg.setTranslateY(-30.0);
        rightLeg.setTranslateY(-30.0);

        leftFoot.setTranslateY(-30.0);
        rightFoot.setTranslateY(-30.0);

        body.setTranslateY(-70.0);

        return body;
    }

    private Xform createBodyLine(double length) {
        final Xform bodyline = new Xform();
        bodyline.setDepthTest(DepthTest.ENABLE);
        final Line line = new Line(0.0, 0.0, 0.0, length);
        line.setFill(Color.BLACK);
        line.setStroke(Color.BLACK);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeType(StrokeType.OUTSIDE);
        line.setStrokeWidth(3.0);

        final Line line2 = new Line(0.0, 0.0, 0.0, length);
        line2.setFill(Color.BLUEVIOLET);
        line2.setEffect(new Glow(30.0));
        line2.setStroke(Color.BLUEVIOLET);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);
        line2.setStrokeType(StrokeType.OUTSIDE);
        line2.setStrokeWidth(3.0);

        final Xform containerA = new Xform();
        containerA.getChildren().add(line);

        final Xform containerB = new Xform();
        containerB.getChildren().add(line2);
        containerB.setRotationAxis(Rotate.Y_AXIS);
        containerB.setRotate(90.0);

        bodyline.getChildren().addAll(containerA, containerB);
        return bodyline;
    }

    public BodyModel() {
    }
    final Timeline idle = new Timeline();
    KeyFrame startOfIdle = new KeyFrame(Duration.ZERO,
            new KeyValue(body.rx.angleProperty(), -30.0),
            new KeyValue(leftUpperLeg.rx.angleProperty(), -30.0),
            new KeyValue(rightUpperLeg.rx.angleProperty(), 30.0),
            new KeyValue(leftUpperArm.rx.angleProperty(), 180),
            new KeyValue(rightUpperArm.rx.angleProperty(), 180),
            new KeyValue(leftArm.rx.angleProperty(), 180),
            new KeyValue(rightArm.rx.angleProperty(), 180),
            new KeyValue(leftLeg.rx.angleProperty(), 180),
            new KeyValue(rightLeg.rx.angleProperty(), 180),
            new KeyValue(leftFoot.rx.angleProperty(), 180),
            new KeyValue(rightFoot.rx.angleProperty(), 180));

    public void idle() {
        if (currentTimeline.equals(idle)) {
            currentTimeline.stop();
        }
        currentTimeline = idle;
        currentTimeline.play();

        RotateTransition rotateTransitionA = new RotateTransition(Duration.millis(1500), this);
        rotateTransitionA.setAxis(Rotate.Y_AXIS);
        rotateTransitionA.setFromAngle(0);
        rotateTransitionA.setToAngle(90.0);
        rotateTransitionA.setCycleCount(Timeline.INDEFINITE);
        rotateTransitionA.setAutoReverse(true);
        //rotateTransitionA.play();

        RotateTransition rotateTransitionB = new RotateTransition(Duration.millis(1500), this);
        rotateTransitionB.setAxis(Rotate.X_AXIS);
        rotateTransitionB.setFromAngle(0);
        rotateTransitionB.setToAngle(90.0);
        rotateTransitionB.setCycleCount(Timeline.INDEFINITE);
        rotateTransitionB.setAutoReverse(true);
        //rotateTransitionB.play();

    }
}
