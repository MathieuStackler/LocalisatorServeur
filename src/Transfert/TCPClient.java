package Transfert;

import KMeans.KMeans;

import java.io.*;
import java.net.*;

public class TCPClient {


    public static void main(String argv[]) throws Exception {
        KMeans k = new KMeans();
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Début de la connexion...");
        Socket clientSocket = new Socket("192.168.1.130", 8181);


        //Reception du fichier
        System.out.println("Connexion établit...");
        System.out.println("Reception du fichier...");
        File file = new File(
                "res/",
                "donnees.json");
        byte[] bytes = new byte[1024];
        InputStream is = clientSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(bytes, 0, bytes.length);
        bos.write(bytes, 0, bytesRead);
        bos.close();

        k.lancement();

        //Envoie de la réponse
        System.out.println("Envoie du fichier...");
        File file1 = new File(
                "res/",
                "traitement.json");
        byte[] bytes1 = new byte[(int) file1.length()];
        BufferedInputStream bis;


        bis = new BufferedInputStream(new FileInputStream(file1));
        bis.read(bytes1, 0, bytes1.length);
        OutputStream os = clientSocket.getOutputStream();
        os.write(bytes1, 0, bytes1.length);
        os.flush();

        System.out.println("Connexion fermée...");
        clientSocket.close();
    }
}