package fr.polytech.localisator.finder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.polytech.localisator.KMeans.Point;
import fr.polytech.localisator.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shafiq on 15/02/2017.
 */
public class MatchingClusterFinder {

    Logger logger = LoggerFactory.getLogger(MatchingClusterFinder.class);

    private static final String CENTROID_LIST_FILE_NAME = "centroidList.json";
    private static final String DATA_PATH = "res";

    private static final int DISTANCE_MAX = 1000;
    List<String> userIdList;

    public MatchingClusterFinder() {
        File folder = new File("res/");
        File[] listOfFiles = folder.listFiles();
        userIdList = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getName());
            } else if (file.isDirectory()) {
                userIdList.add(file.getName());
            }
        }

        for(String userId: userIdList) {
            logger.info(userId);
        }
    }

    public List<Point> getCentroidList(String userId) {
        String centroidData = "";
        try {
            centroidData = new String(Files.readAllBytes(Paths.get(DATA_PATH + "/" + userId + "/" + CENTROID_LIST_FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        List<Point> centroidList = gson.fromJson(centroidData, new TypeToken<List<Point>>(){}.getType());

        return centroidList;
    }

    public List<String> findMatchCluster(String userId) {
        List<String> matchedUserId =  new ArrayList<>();

        List<Point> userCentroid = getCentroidList(userId);

        for (String targetId: userIdList) {
            // Ignore if the target id is the same with user id
            if(!targetId.equals(userId)) {
                List<Point> targetCentroid = getCentroidList(targetId);

                if((pointIsNearby(userCentroid.get(0), targetCentroid.get(0), DISTANCE_MAX)
                        || pointIsNearby(userCentroid.get(0), targetCentroid.get(1), DISTANCE_MAX))
                        && (pointIsNearby(userCentroid.get(1), targetCentroid.get(0), DISTANCE_MAX)
                        || pointIsNearby(userCentroid.get(1), targetCentroid.get(1), DISTANCE_MAX))){
                    logger.info(userId + " is a match with " + targetId);
                    matchedUserId.add(targetId);
                }
            }
        }

        return matchedUserId;
    }

    private boolean pointIsNearby(Point point, Point point1, int distance) {
        return calcDistance(point.getX(), point1.getX(),
                point.getY(), point1.getY(), 0, 0) < distance;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public double calcDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
