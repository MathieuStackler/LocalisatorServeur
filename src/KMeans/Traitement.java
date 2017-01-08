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
    public static int numberCluster;
    public static List<Point> myList = new ArrayList();
    public static double x_max = 0;
    public static double x_min = 100;
    public static double y_max = 0;
    public static double y_min = 100;

    private static final int LATITUDE_VALUE_INDEX = 4;
    private static final int LONGITUDE_VALUE_INDEX = 6;


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
            input = new FileReader("res/param.json");
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


        try {
            while ( (myLine = bufRead.readLine()) != null){
                String[] array = myLine.split(",");
                float nbCluster = Float.parseFloat(array[0]);
                numberCluster = (int) nbCluster;
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
                    i++;
                    double x = Float.parseFloat(array[LATITUDE_VALUE_INDEX]);
                    double y = Float.parseFloat(array[LONGITUDE_VALUE_INDEX]);
                    System.out.println("x : " + x);
                    System.out.println("y : " + y);
                    myList.add(i-1, new Point(x, y));

                    if (x_max < Float.parseFloat(array[LATITUDE_VALUE_INDEX])){
                        x_max = Float.parseFloat(array[LATITUDE_VALUE_INDEX]);
                    }
                    if (x_min > Float.parseFloat(array[LATITUDE_VALUE_INDEX])){
                        x_min = Float.parseFloat(array[LATITUDE_VALUE_INDEX]);
                    }

                    if (y_max < Float.parseFloat(array[LONGITUDE_VALUE_INDEX])){
                        y_max = Float.parseFloat(array[LONGITUDE_VALUE_INDEX]);
                    }
                    if (y_min > Float.parseFloat(array[LONGITUDE_VALUE_INDEX])){
                        y_min = Float.parseFloat(array[LONGITUDE_VALUE_INDEX]);
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
