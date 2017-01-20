package fr.polytech.localisator.Transfert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shafiq on 04/01/2017.
 */
public class TCPServer {

    static final int SocketServerPORT = 8181;

    public static void main(String argv[]) throws Exception {
        while(true) {
            ServerSocketThread serverSocketThread = new ServerSocketThread();
            serverSocketThread.run();
        }

    }
}
