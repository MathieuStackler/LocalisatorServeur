package fr.polytech.localisator.KMeans;

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

    private int nbPoint = 0;
    private int numberCluster;
    private List<Point> myList = new ArrayList();
    private double x_max = 0;
    private double x_min = 100;
    private double y_max = 0;
    private double y_min = 100;

    private final int LATITUDE_VALUE_INDEX = 4;
    private final int LONGITUDE_VALUE_INDEX = 6;

    private final String dataPath = "res";

    public Traitement(String userId) {
        data(userId);
    }

    public void data(String userId) {

        // Open the data file
        //Ouverture du fichier
        FileReader input = null;
        try {
            input = new FileReader(dataPath + "/" + userId + "/donnees.json");
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

            int i = 0;

            while ( (myLine = bufRead.readLine()) != null){
                String[] array = myLine.split(",");
                nbPoint++;
                double x = Float.parseFloat(array[LATITUDE_VALUE_INDEX]);
                double y = Float.parseFloat(array[LONGITUDE_VALUE_INDEX]);
                System.out.println("x : " + x);
                System.out.println("y : " + y);
                myList.add(i, new Point(x, y));
                i++;

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

    }

    public int getNbPoint() {
        return nbPoint;
    }

    public double getX_max() {
        return x_max;
    }

    public double getX_min() {
        return x_min;
    }

    public double getY_max() {
        return y_max;
    }

    public double getY_min() {
        return y_min;
    }

    public int getNumberCluster() {
        return numberCluster;
    }

    public List<Point> getMyList() {
        return myList;
    }
}
