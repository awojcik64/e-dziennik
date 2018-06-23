package psk.java.projekt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMainThread implements Runnable {
    private boolean status=false;
    private ServerSocket serverSocket;
    private ArrayList<ServerConnectionHandler> openedThreads;
    public void close()
    {
        status=false;
        try{
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            serverSocket=new ServerSocket(27027);
            status=true;
            System.out.println("Zainicjalizowano watek serwera");
            while(status)
            {
                Socket client=serverSocket.accept();

                openedThreads.add(new ServerConnectionHandler(client));
            }
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Thread join error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
