package edu.wpi.MochaManticores.Services;

import java.util.HashMap;
import java.util.LinkedList;

public class ServiceMap {
    private static final HashMap<ServiceRequestType, LinkedList<ServiceRequest>> myMap = new HashMap<>();

    /**
     * function: addToType()
     * @param type type of service request
     * @param request the service request that needs to be added
     */
    public static void addToType(ServiceRequestType type, ServiceRequest request) {
        //adds a linked list to key if there is no linked list already there
        myMap.computeIfAbsent(type, k -> new LinkedList<>());
        myMap.get(type).add(request);
    }

    /**
     * function: getServiceRequestsForType()
     * @param type type of service requests
     * @return a linked list of service requests
     */
    public static LinkedList<ServiceRequest> getServiceRequestsForType(ServiceRequestType type) {
        return myMap.get(type);
    }

    /**
     * function: delRequest()
     * @param type type of service request
     * @param request the service request that needs to be deleted
     */
    public static void delRequest(ServiceRequestType type, ServiceRequest request) {
        myMap.get(type).remove(request);
    }
}

enum ServiceRequestType {
    InternalTransportation,
    ExternalTransportation,
    FloralDelivery,
    FoodDelivery,
    SanitationServices,
    Emergency
}
