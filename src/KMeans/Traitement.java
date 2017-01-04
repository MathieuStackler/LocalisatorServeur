package KMeans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nahia on 30/12/2016.
 */
public class Traitement {


    private static int valeur=0;
    public static List<Point> myList = new ArrayList();
    public static double x_max = 0;
    public static double x_min = 100;
    public static double y_max = 0;
    public static double y_min = 100;


    public static int data() {

        //Ouverture du fichier
        FileReader input = null;
        try {
            input = new FileReader("res/donnees.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufRead = new BufferedReader(input);
        String myLine = null;

        try {
            myLine = bufRead.readLine();    //première ligne de consigne
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ( (myLine = bufRead.readLine()) != null){
                String[] array = myLine.split(",");
                valeur++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Ouverture du fichier
        input = null;
        try {
            input = new FileReader("res/donnees.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufRead = new BufferedReader(input);
        myLine = null;

        try {
            myLine = bufRead.readLine();    //première ligne de consigne
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;

        try {
            while ( (myLine = bufRead.readLine()) != null && i<valeur-1){
                String[] array = myLine.split(",");

                System.out.println(i);
                double x = Float.parseFloat(array[3]);
                double y = Float.parseFloat(array[5]);
                myList.add(i, new Point(x, y));
                i++;

                if (x_max < Float.parseFloat(array[3])){
                    x_max = Float.parseFloat(array[3]);
                }
                if (x_min > Float.parseFloat(array[3])){
                    x_min = Float.parseFloat(array[3]);
                }

                if (y_max < Float.parseFloat(array[5])){
                    y_max = Float.parseFloat(array[5]);
                }
                if (y_min > Float.parseFloat(array[5])){
                    y_min = Float.parseFloat(array[5]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return valeur-1;
    }



}
