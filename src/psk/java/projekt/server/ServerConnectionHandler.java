package psk.java.projekt.server;

import java.net.Socket;
import java.sql.Connection;

public class ServerConnectionHandler implements Runnable {
    private Socket client;


    public ServerConnectionHandler(Socket client) {
        this.client = client;
        Thread thread = new Thread(this, "e-dziennik Connection Handler");
    }

    @Override
    public void run() {

    }
}
