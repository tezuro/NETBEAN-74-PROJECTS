/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxtoolbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Leaf extends Ellipse {

    public final Color AUTUMN_COLOR;
    private static final Random RANDOM = new Random();
    private final int N = 5;
    private final List<Ellipse> petals = new ArrayList<>(2 * N + 1);

    public Leaf(Branch parentBranch) {
        //super(0, parentBranch.length / 2., 2, parentBranch.length / 2.);
        setScaleX(0);
        setScaleY(0);

        double rand = RANDOM.nextDouble() * 0.5 + 0.3;
        AUTUMN_COLOR = Color.color(RANDOM.nextDouble() * 0.1 + 0.8, rand, rand / 2);

        Color color = new Color(RANDOM.nextDouble() * 0.5, RANDOM.nextDouble() * 0.5 + 0.5, 0, 1);
        //if (parentBranch.globalH < 400 && RANDOM.nextDouble() < 0.8) { //bottom leaf is darker
            color = color.darker();
        //}
        setFill(color);
    }

}