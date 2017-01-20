package fr.polytech.localisator.Transfert;

import fr.polytech.localisator.KMeans.KMeans;

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
        KMeans k = new KMeans("", 2);

        //Traitement de donnees.json
        File file1 = new File("res/" ,"donnees.json");

        System.out.println(file1.getParentFile().exists());

        if(!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
        }

        try {
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[2048];
        InputStream is = null;

            try {
                is = socket.getInputStream();
                FileOutputStream fos1 = new FileOutputStream(file1);
                BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
                int bytesRead1 = is.read(bytes, 0, bytes.length);
                bos1.write(bytes);//, 0, bytesRead1);
                bos1.close();

            } catch (IOException e) {
                e.printStackTrace();
            }



  /*      try {
            k.lancement();
        } catch (IOException e) {
            e.printStackTrace();
        } */


        //Traitement de param.json
        File file3 = new File("res/" ,"param.json");
        try {
            file3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes3 = new byte[1024];
        InputStream is3 = null;
        try {

            is3 = socket.getInputStream();
            FileOutputStream fos3 = new FileOutputStream(file3);
            BufferedOutputStream bos3 = new BufferedOutputStream(fos3);
            int bytesRead3 = is3.read(bytes3, 0, bytes3.length);
            bos3.write(bytes3, 0, bytesRead3);
            bos3.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            k.lancement();
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Traitement de traitement.json
        File file2 = new File("res/", "traitement.json");

        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Envoie du fichier...\n");

        byte[] bytes1 = new byte[(int) file2.length()];
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