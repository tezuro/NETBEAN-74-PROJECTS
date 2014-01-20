package io;

import com.sun.deploy.config.Config;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import javafx.application.Platform;

/**
 *
 * @author jimc
 */
public class SocketServer extends GenericSocket
        implements SocketListener {

    private SocketListener fxListener;
    private ServerSocket serverSocket;

    public SocketServer(SocketListener fxListener,
            int port, int debugFlags) {
        super(port, debugFlags);
        this.fxListener = fxListener;
    }

    public SocketServer(SocketListener fxListener) {
        this(fxListener, DEFAULT_PORT, DEBUG_NONE);
    }

    public SocketServer(SocketListener fxListener, int port) {
        this(fxListener, port, DEBUG_NONE);
    }

    /**
     * Called whenever a message is read from the socket. In JavaFX, this method
     * must be run on the main thread and is accomplished by the
     * Entry.deferAction() call. Failure to do so *will* result in strange
     * errors and exceptions.
     *
     * @param line Line of text read from the socket.
     */
    @Override
    public void onMessage(final String line) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fxListener.onMessage(line);
            }
        });
    }

    /**
     * Called whenever the open/closed status of the Socket changes. In JavaFX,
     * this method must be run on the main thread and is accomplished by the
     * Entry.deferAction() call. Failure to do so *will* result in strange
     * errors and exceptions.
     *
     * @param isClosed true if the socket is closed
     */
    @Override
    public void onClosedStatus(final Boolean isClosed) {
        fxListener.onClosedStatus(isClosed);
    }

    /**
     * Initialize the SocketServer up to and including issuing the accept()
     * method on its socketConnection.
     * @throws java.net.SocketException
     */
    @Override
    public void initSocketConnection() throws SocketException {
        try {
            /*
             * Create server socket
             */
            serverSocket = new ServerSocket(port);
            /*
             * Allows the socket to be bound even though a previous
             * connection is in a timeout state.
             */
            serverSocket.setReuseAddress(true);
            /*
             * Wait for connection
             */
            if (debugFlagIsSet(DEBUG_STATUS)) {
                System.out.println("Waiting for connection");
            }
            socketConnection = serverSocket.accept();
            if (debugFlagIsSet(DEBUG_STATUS)) {
                System.out.println("Connection received from "
                        + socketConnection.getInetAddress().getHostName());
            }
        } catch (IOException e) {
            if (debugFlagIsSet(DEBUG_EXCEPTIONS)) {
                e.printStackTrace();
            }
            throw new SocketException();
        }
    }

    /**
     * For SocketServer class, additional ServerSocket instance has to be
     * closed.
     */
    @Override
    public void closeAdditionalSockets() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
