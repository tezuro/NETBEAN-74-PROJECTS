/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

/**
 *
 * @author tezuro
 */
public class NodeManager {

    private static final ArrayList<Circle> nodes = new ArrayList<>();
    private static final ArrayList<ChangeListener> allMyListener = new ArrayList<>();

    private NodeManager() {
    }

    public static void addListener(ChangeListener myListener) {
        allMyListener.add(myListener);
        for (Circle c : nodes) {
            c.centerXProperty().addListener(myListener);
            c.centerYProperty().addListener(myListener);
        }
    }

    public static void removeListener(ChangeListener myListener) {
        allMyListener.remove(myListener);
        for (Circle c : nodes) {
            c.centerXProperty().removeListener(myListener);
            c.centerYProperty().removeListener(myListener);
        }
    }

    public static ArrayList<Circle> getNodes() {
        return nodes;
    }

    public static void addNode(Circle c) {
        nodes.add(c);
        for (ChangeListener myListener : allMyListener) {
            c.centerXProperty().addListener(myListener);
            c.centerYProperty().addListener(myListener);
        }
    }

    /**
     * gibt das erste object zurueck was intersect
     *
     * @param point
     * @return
     */
    public static Circle checkPoint(Point2D point) {
        for (Circle c : nodes) {
            if (c.contains(point)) {
                return c;
            }
        }
        return null;
    }

    public static Circle checkPoint(double x, double y) {
        for (Circle c : nodes) {
            if (c.contains(x, y)) {
                return c;
            }
        }
        return null;
    }
}
