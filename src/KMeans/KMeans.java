package KMeans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import KMeans.Cluster;
import KMeans.Point;

/**
 * Created by Nahia on 30/12/2016.
 */
public class KMeans {
    //Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS = 2;
    //Number of Points
    private int NUM_POINTS;
    //Min and Max X and Y
    private double MIN_X = 0;
    private double MAX_X = 0;
    private double MIN_Y = 0;
    private double MAX_Y = 0;

    private List<Point> points;
    private static List<Cluster> clusters;
    private static Traitement t;
    private static String donnees;

    public KMeans() {
        this.points = new ArrayList();
        this.clusters = new ArrayList();
    }

   // public static void main(String[] args) throws IOException {

        public static void lancement() throws IOException {

        KMeans kmeans = new KMeans();
        kmeans.init();
        kmeans.calculate();


        //Print
        System.out.println("Premier centroid : " + clusters.get(0).getCentroid());
        System.out.println("Second centroid : " + clusters.get(1).getCentroid());
        donnees = "Données traitées \n" +
                "Latitude," + String.valueOf(clusters.get(0).getCentroid().getX()) + ",Longitude," + String.valueOf(clusters.get(0).getCentroid().getY()) + "\n" +
                "Latitude," + String.valueOf(clusters.get(1).getCentroid().getX()) + ",Longitude," + String.valueOf(clusters.get(1).getCentroid().getY()) + "\n";

        System.out.println(donnees);
        kmeans.ecriture();
    }

    //Initializes the process
    public void init() {
        //Get the coord
        NUM_POINTS = t.data();
        MIN_X = Traitement.x_min;
        MAX_X = Traitement.x_max;
        MIN_Y = Traitement.y_min;
        MAX_Y = Traitement.y_max;

        Traitement.myList.size();

        for (int i = 0; i < NUM_POINTS; i++){

            double x = Traitement.myList.get(i).getX();
            double y = Traitement.myList.get(i).getY();
            points.add(i, new Point(x, y));
        }

        //Create Clusters
        //Set Random Centroids
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = Point.createRandomPoint(MIN_X, MAX_X, MIN_Y, MAX_Y);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();


    }

    private void plotClusters() {
        for (int i = 0; i < NUM_CLUSTERS; i++) {
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
        List centroids = new ArrayList(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(),aux.getY());
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
            for(int i = 0; i < NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
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

    private void ecriture() throws IOException {

        File ff;
        ff = new File("traitement.json");

        FileWriter ffw = new FileWriter(ff);

        ffw.write(donnees);

        ffw.close();
    }
}