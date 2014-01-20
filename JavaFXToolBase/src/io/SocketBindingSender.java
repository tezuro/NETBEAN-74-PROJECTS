/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author tezuro
 */
public class SocketBindingSender extends SocketBinding {

    private GenericSocket socket;
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private ChangeListener xListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1) {
            socket.sendMessage(id + "|" + X_CONSTANT + "|" + x.get()+ "|");
        }
    };
    private ChangeListener yListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue ov, Object t, Object t1) {
            socket.sendMessage(id + "|" + Y_CONSTANT + "|" + y.get()+ "|");
        }
    };

    /**
     * sender
     *
     * @param socket
     */
    public SocketBindingSender(final GenericSocket socket, DoubleProperty xx, DoubleProperty yy, String id) {
        super(id);
        this.socket = socket;
        x.bind(xx);
        y.bind(yy);

        x.addListener(xListener);
        y.addListener(yListener);
    }
}
