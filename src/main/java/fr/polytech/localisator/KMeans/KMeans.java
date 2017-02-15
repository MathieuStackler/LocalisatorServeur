package fr.polytech.localisator.KMeans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nahia on 30/12/2016.
 */
public class KMeans {

    //Number of Clusters. This metric should be related to the number of points
    private int nbClusters;

    //Number of Points
    private int NUM_POINTS;

    //Min and Max X and Y
    private double MIN_X = 0;
    private double MAX_X = 0;
    private double MIN_Y = 0;
    private double MAX_Y = 0;

    private List<Point> points;
    private List<Cluster> clusters;
    private Traitement t;
    private String donnees;

    private String userId;

    public KMeans(String id, int nbCluster) {
        this.points = new ArrayList();
        this.clusters = new ArrayList();
        userId = id;
        t = new Traitement(userId);
        this.nbClusters = nbCluster;
    }

    public void lancement() throws IOException {

        init();
        calculate();

        //Print
        System.out.println("Premier centroid : " + clusters.get(0).getCentroid());
        System.out.println("Second centroid : " + clusters.get(1).getCentroid());
        donnees = "Données traitées \n";

        for (int j = 0; j< nbClusters; j++){
            donnees += "Latitude," + String.valueOf(clusters.get(j).getCentroid().getX()) + ",Longitude," + String.valueOf(clusters.get(j).getCentroid().getY()) + "\n";
        }

        System.out.println(donnees);
        //kmeans.ecriture();
    }

    //Initializes the process
    public void init() {

        //Get the coord
        NUM_POINTS = t.getNbPoint();

        MIN_X = t.getX_min();
        MAX_X = t.getX_max();
        MIN_Y = t.getY_min();
        MAX_Y = t.getY_max();

        System.out.println("Nombre de clusters : " + nbClusters);
        for (int i = 0; i < NUM_POINTS; i++){
            double x = t.getMyList().get(i).getX();
            double y = t.getMyList().get(i).getY();
            points.add(i, new Point(x, y));
        }

        //Create Clusters
        //Set Random Centroids
        for (int i = 0; i < nbClusters; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = Point.createRandomPoint(MIN_X, MAX_X, MIN_Y, MAX_Y);
            centroid.setClusterNumber(i);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();
    }

    private void plotClusters() {
        for (int i = 0; i < nbClusters; i++) {
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    //The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while(!finish) {
            //Clear cluster state
            clearClusters();

            List<Point> lastCentroids = getCentroids();

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List<Point> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            plotClusters();

            if(distance == 0) {
                finish = true;
            }
        }
    }

    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List getCentroids() {
        List centroids = new ArrayList(nbClusters);
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(), aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        for(Point point : points) {
            min = max;
            for(int i = 0; i < nbClusters; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setClusterNumber(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for(Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if(n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }

    public List<Cluster> getClusters() {
        return clusters;
    }
}