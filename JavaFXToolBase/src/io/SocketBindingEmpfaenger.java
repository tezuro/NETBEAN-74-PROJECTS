/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

/**
 *
 * @author tezuro
 */
public class SocketBindingEmpfaenger extends SocketBinding {
    private Group root;
    private Circle c;

    /**
     * empfaenger
     *
     * @param socket
     */
    public SocketBindingEmpfaenger(final Group root, Circle c, String id) {
        super(id);
        this.root = root;
        this.c = c;
        root.getChildren().add(c);
    }

    public void updateMe(String[] data) {
        if (X_CONSTANT.equals(data[2])) {
            c.centerXProperty().setValue(Double.parseDouble(data[3]));
        } else {
            c.centerYProperty().setValue(Double.parseDouble(data[3]));
        }
    }
    
    public void destroyMe(){
        root.getChildren().remove(c);
    }
}
