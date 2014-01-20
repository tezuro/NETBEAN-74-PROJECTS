package javafxtoolbase;

import java.util.Random;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.transform.Transform;

public class Blade extends Path {

    /**
     * The {@code double} value that is closer than any other to <i>pi</i>, the
     * ratio of the circumference of a circle to its diameter.
     */
    public static final double PI = 3.14159265358979323846;
    private static final Random RANDOM = new Random();
    public final Color SPRING_COLOR = Color.color(RANDOM.nextDouble() * 0.5, RANDOM.nextDouble() * 0.5 + 0.5, 0.).darker();
    public final Color AUTUMN_COLOR = Color.color(RANDOM.nextDouble() * 0.4 + 0.3, RANDOM.nextDouble() * 0.1 + 0.4, RANDOM.nextDouble() * 0.2);
    private final static double width = 3;
    private double x = RANDOM.nextDouble() * 170;
    private double y = RANDOM.nextDouble() * 20 + 20;
    private double h = (50 * 1.5 - y / 2) * RANDOM.nextDouble() * 0.3;
    public SimpleDoubleProperty phase = new SimpleDoubleProperty();

    public Blade() {

        getElements().add(new MoveTo(0, 0));
        final QuadCurveTo curve1;
        final QuadCurveTo curve2;
        getElements().add(curve1 = new QuadCurveTo(-10, h, h / 4, h));
        getElements().add(curve2 = new QuadCurveTo(-10, h, width, 0));

        setFill(AUTUMN_COLOR); //autumn color of blade
        setStroke(null);

        getTransforms().addAll(Transform.translate(x, y));

        curve1.yProperty().bind(new DoubleBinding() {
            {
                super.bind(curve1.xProperty());
            }

            @Override
            protected double computeValue() {

                final double xx0 = curve1.xProperty().get();
                return Math.sqrt(h * h - xx0 * xx0);
            }
        }); //path of top of blade is circle

        //code to bend blade
        curve1.controlYProperty().bind(curve1.yProperty().add(-h / 4));
        curve2.controlYProperty().bind(curve1.yProperty().add(-h / 4));

        curve1.xProperty().bind(new DoubleBinding() {
            final double rand = RANDOM.nextDouble() * (PI / 4);

            {
                super.bind(phase);
            }

            @Override
            protected double computeValue() {
                return (h / 4) + ((Math.cos(phase.get() + (x + 400.0) * PI / 1600 + rand) + 1) / 2.0) * (-3.0 / 4) * h;
            }
        });
    }
}
