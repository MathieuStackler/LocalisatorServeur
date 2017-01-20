package fr.polytech.localisator;

import com.google.gson.Gson;
import fr.polytech.localisator.KMeans.Cluster;
import fr.polytech.localisator.KMeans.KMeans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by shafiq on 14/01/2017.
 */
public class Main {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        get("/hello", (req, res) ->  {
            logger.info("Getting GET request on /hello");
            return  "Hello World";
        });

        post("/upload", (request, response) -> {

            String location = "data";          // the directory location where files will be stored
            long maxFileSize = 100000000;       // the maximum size allowed for uploaded files
            long maxRequestSize = 100000000;    // the maximum size allowed for multipart/form-data requests
            int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    location, maxFileSize, maxRequestSize, fileSizeThreshold);
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    multipartConfigElement);

            Collection<Part> parts = request.raw().getParts();
            for (Part part : parts) {
                System.out.println("Name: " + part.getName());
                System.out.println("Size: " + part.getSize());
                System.out.println("Filename: " + part.getSubmittedFileName());
            }

            String fName = request.raw().getPart("file").getSubmittedFileName();
            System.out.println("Title: " + request.raw().getParameter("title"));
            System.out.println("File: " + fName);

            // id of the sender is stored in description
            String id = request.queryParams("id");
            logger.info("Getting POST request on /upload from " + id);

            int nbCluster = Integer.parseInt(request.queryParams("nbCluster"));
            logger.info("nbCluster: " + nbCluster);

            String newJson = IOUtils.toString(request.raw().getPart("file").getInputStream());
            logger.info("json: \n" + newJson);

            Part uploadedFile = request.raw().getPart("file");
            Path out = Paths.get(location + "/" + id, fName);

            out.toFile().getParentFile().mkdirs();
            out.toFile().createNewFile();

            try (final InputStream in = uploadedFile.getInputStream()) {
                logger.info("Writing data...");
                Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                uploadedFile.delete();
            }

            // cleanup
            multipartConfigElement = null;
            parts = null;
            uploadedFile = null;

            KMeans k = new KMeans(id, nbCluster);
            k.lancement();

            List<Cluster> responseCluster = k.getClusters();

            for(int i=0; i<responseCluster.size(); i++) {
                logger.info("Cluster " + i + ": " + responseCluster.get(i).getCentroid().getX() + ","
                        + responseCluster.get(i).getCentroid().getY());
            }

            Gson gson = new Gson();
            String clusterJson = gson.toJson(responseCluster);
            //logger.info("JsonResponse: " + clusterJson);

            response.type("application/json");
            response.body(clusterJson);

            return response.body();
        });
    }
}
