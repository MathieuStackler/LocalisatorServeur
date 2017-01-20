package fr.polytech.localisator.Transfert;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static fr.polytech.localisator.Transfert.TCPServer.SocketServerPORT;

//Création du thread qui attend une connexion
public class ServerSocketThread extends Thread {

    @Override
    public void run() {
        Socket socket = null;

        try {
            ServerSocket serverSocket = new ServerSocket(SocketServerPORT);

            while(true) {
                System.out.println("En attente d'une connexion...\n");
                socket = serverSocket.accept();
                System.out.println("Connexion établie...\n");
                FileTxThread fileTxThread = new FileTxThread(socket);
                fileTxThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}