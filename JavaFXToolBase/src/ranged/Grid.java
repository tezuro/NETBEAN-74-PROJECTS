/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ranged;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 *
 * @author tezuro
 */
public class Grid {

    public static Node createGrid() {
        Group grid = new Group();
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                int tmpX = x * 13;
                int tmpY = y * 14;
                if (x % 2 == 0) {
                    tmpY += 7;
                }
                grid.getChildren().add(createHexGroup(Double.parseDouble(tmpX + ".0"), Double.parseDouble(tmpY + ".0")));
            }
        }
        return grid;
    }

    private static Node createHexGroup(Double pointX, Double pointY) {
        Double x = pointX;
        Double y = pointY;

        MyLine polyline = new MyLine(x + 4.0, y + 7.0,
                x + 8.0, y + 0.0,
                x + 4.0, y + -7.0,
                x + -4.0, y + -7.0,
                x + -8.0, y + 0.0,
                x + -4.0, y + 7.0,
                x + 4.0, y + 7.0);

        polyline.setVisible(true);
        polyline.setFill(Color.GREEN);
        return polyline;
    }

    public static class MyLine extends Polyline {

        private MyLine() {
        }

        public MyLine(double... doubles) {
            super(doubles);
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    MyLine.this.setEffect(new Glow());

                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    MyLine.this.setEffect(null);

                }
            });
        }
    }
}
