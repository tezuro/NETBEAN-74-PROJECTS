/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import basis.*;
import java.util.HashMap;

/**
 *
 * @author tezuro
 */
public final class GameSocketListener implements SocketListener {
    
    //http://docs.oracle.com/javaee/7/tutorial/doc/websocket.htm

    public static final boolean SERVER = true;
    public static final boolean CLIENT = false;
    private final GenericSocket socket;
    private final HashMap<String, SocketBindingEmpfaenger> enpfaengerBindings = new HashMap<>();
    private final HashMap<String, SocketBindingSender> senderBindings = new HashMap<>();
    private BindableManager bm;

    public GameSocketListener(boolean isServer) {
        socket = isServer ? new SocketServer(this) : new SocketClient(this);
        socket.connect();
    }

    public void setBindableManager(BindableManager newBM) {
        bm = newBM;
    }

    /**
     * sollte nur auf EMPFAENGER SEITE BENUTZT WERDEN
     *
     * @param line
     */
    @Override
    public void onMessage(String line) {
        String[] data = SocketBindingEmpfaenger.getDataFromLine(line);
        bm.processBindable(data);
    }

    public GenericSocket getSocket() {
        return socket;
    }

    @Override
    public void onClosedStatus(Boolean isClosed) {
        System.out.println("Server Status:" + isClosed.toString());
    }
}
