package Transfert;

import KMeans.KMeans;

import java.io.*;
import java.net.Socket;

//Thread qui envoie le .json quand une demande est faite
public class FileTxThread extends Thread {
    Socket socket;

    FileTxThread(Socket socket){
        this.socket= socket;
    }

    @Override
    public void run() {
        KMeans k = new KMeans();
        File file1 = new File("donnees.json");
        try {
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[1024];
        InputStream is = null;
        try {
            is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(file1);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(bytes, 0, bytes.length);
            bos.write(bytes, 0, bytesRead);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            k.lancement();
        } catch (IOException e) {
            e.printStackTrace();
        }


        File file2 = new File("traitement.json");

        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Envoie du fichier...\n");

        byte[] bytes1 = new byte[(int) file1.length()];
        BufferedInputStream bis;

        try {
            bis = new BufferedInputStream(new FileInputStream(file2));
            bis.read(bytes1, 0, bytes1.length);
            OutputStream os = socket.getOutputStream();
            os.write(bytes1, 0, bytes1.length);
            os.flush();

            System.out.println("Connexion fermée...");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Fermeture de la socket");
                socket.close();
                this.interrupt();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}