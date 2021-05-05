package edu.wpi.MochaManticores;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GoogleMapsAPI {

    StaticMapsRequest smr;
    public Image map;

    public GoogleMapsAPI(){
        this.smr = StaticMapsApi.newRequest(App.getContext(), new Size(1000, 1000));
        TextSearchRequest req = PlacesApi.textSearchQuery(App.getContext(), "Brigham and Womens Hospital");
        TextSearchRequest req1 = PlacesApi.textSearchQuery(App.getContext(), "WPI");
        PlacesSearchResponse res = null;
        PlacesSearchResponse res1 = null;
        try {
            res = req.await();
            res1 = req1.await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        String origin = res.results[0].formattedAddress;
        String dest = res1.results[0].formattedAddress;

        DirectionsApiRequest req2 = DirectionsApi.getDirections(App.getContext(), origin, dest);
        req2 = req2.optimizeWaypoints(true);
        DirectionsResult res3 = null;
        try {
            res3 = req2.await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }



        smr = smr.center(res.results[0].geometry.location);
        StaticMapsRequest.Path p = new StaticMapsRequest.Path();
        DirectionsRoute route = res3.routes[0];
        List<LatLng> points = route.overviewPolyline.decodePath();
        for (LatLng pt : points){
            p.addPoint(pt);
        }
        smr = smr.path(p);
        smr = smr.scale(2);
        ImageResult img = null;
        try {
           img = smr.await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
//
        map = new Image(new ByteArrayInputStream(img.imageData));
//
        System.out.println(res1.results);

    }
}
