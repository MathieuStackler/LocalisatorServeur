package fr.polytech.localisator.KMeans;

import java.util.Random;

/**
 * Created by Nahia on 30/12/2016.
 */
public class Point {

    private double x = 0;
    private double y = 0;
    private int clusterNumber = 0;

    public Point(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX()  {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    //Calculates the distance between two points.
    protected static double distance(Point p, Point centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }

    //Calculates the distance between two points.
    protected static double distance(double p, double centroid) {
        return centroid-p;
    }

    //Creates random point
    protected static Point createRandomPoint(double minX, double maxX, double minY, double maxY) {
        Random r = new Random();
        double x = minX + (maxX - minX) * r.nextDouble();
        double y = minY + (maxY - minY) * r.nextDouble();
        return new Point(x,y);
    }

    public String toString() {
        return ""+x+","+y+" Cluster nb: " + clusterNumber;
    }
}
