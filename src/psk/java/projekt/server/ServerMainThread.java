package psk.java.projekt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMainThread implements Runnable {
    private boolean status=false;
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket=new ServerSocket(27027);
            status=true;
            while(status)
            {
                Socket klient=serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
