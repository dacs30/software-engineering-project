package edu.wpi.MochaManticores.Services;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

public class ServiceMap {
    private static final HashMap<ServiceRequestType, HashMap<String, ServiceRequest>> myMap = new HashMap<>();
    public static ServiceRequestType InternalTransportation = ServiceRequestType.InternalTransportation;
    public static ServiceRequestType ExternalTransportation = ServiceRequestType.ExternalTransportation;
    public static ServiceRequestType Emergency = ServiceRequestType.Emergency;
    public static ServiceRequestType FloralDelivery = ServiceRequestType.FloralDelivery;
    public static ServiceRequestType SanitationServices = ServiceRequestType.SanitationServices;
    public static ServiceRequestType FoodDelivery = ServiceRequestType.FoodDelivery;

    /**
     * function: addRequest()
     * @param type type of service request
     * @param request the service request that needs to be added
     */
    public static void addRequest(ServiceRequestType type, ServiceRequest request) {
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
        LinkedList<ServiceRequest> temp = new LinkedList<ServiceRequest>();
        for (ServiceRequest value : myMap.get(type).values()) {
            temp.add(value);
        }
        return temp;
    }

    /**
     * function: delRequest()
     * @param type type of service request
     * @param request the service request that needs to be deleted
     */
    public static void delRequest(ServiceRequestType type, ServiceRequest request) throws FileNotFoundException {
        myMap.get(type).remove(request);
    }

    /**
     * function: getRequest()
     * @param type type of request
     * @param requestID string to represent Request
     */
    public static ServiceRequest getRequest(ServiceRequestType type, String requestID){
        myMap.get(type).
    }
}

public enum ServiceRequestType {
    InternalTransportation,
    ExternalTransportation,
    FloralDelivery,
    FoodDelivery,
    SanitationServices,
    Emergency
}
