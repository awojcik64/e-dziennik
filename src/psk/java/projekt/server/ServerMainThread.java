package psk.java.projekt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Klasa głównego wątku serwera, implementuje interfejs Runnable
 */
public class ServerMainThread implements Runnable {
    private boolean status=false;
    private ServerSocket serverSocket;
    private ArrayList<ServerConnectionHandler> openedThreads;

    /**
     * Publiczna metoda typu void która ma za zadanie zamknąć gniazdo
     * @throws IOException Może wystąpić wyjątek typu IOException
     */
    public void close() throws IOException {
        status = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Publiczna metoda typu void jest przesłoniętą metodą z interfejsu Runnable, znajduje się w niej to co będzie wykonywane współbierzni
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(27027);
            openedThreads = new ArrayList<ServerConnectionHandler>();
            status = true;
            System.out.println("Zainicjalizowano watek serwera");
            while(status) {
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
