package edu.wpi.MochaManticores.Services;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

public class ServiceMap {
    private final HashMap<ServiceRequestType, HashMap<String, ServiceRequest>> myMap = new HashMap<>();
    //private final HashMap<ServiceRequestType, Integer> maxMap = new HashMap<>();

    public ServiceMap() {
        for (ServiceRequestType type : ServiceRequestType.values()) {
            myMap.put(type,new HashMap<String,ServiceRequest>());
            //maxMap.put(type,0);
        }
    }
    /**
     * function: addRequest()
     * @param type type of service request
     * @param request the service request that needs to be added
     */
    public void addRequest(ServiceRequestType type, ServiceRequest request) {
        //adds a hashmap to key if there is no linked list already there
        myMap.computeIfAbsent(type, k -> new HashMap<String,ServiceRequest>());
        myMap.get(type).put(request.getRequestID(), request);
        //maxMap.put(type,maxMap.get(type)+1);
    }

    /**
     * function: getServiceRequestsForType()
     * @param type type of service requests
     * @return a linked list of service requests
     */
    public LinkedList<ServiceRequest> getServiceRequestsForType(ServiceRequestType type) {
        LinkedList<ServiceRequest> temp = new LinkedList<ServiceRequest>();
        for (ServiceRequest value : myMap.get(type).values()) {
            temp.add(value);
        }
        return temp;
    }

    /**
     * function: delRequest()
     * @param type type of service request
     * @param ID the service request that needs to be deleted
     */
    public void delRequest(ServiceRequestType type, String ID){
        if (containsRequest(type,ID)) {
            myMap.get(type).remove(ID);
        }
    }

    /**
     * function: getRequest()
     * @param type type of request
     * @param requestID string to represent Request
     */
    public ServiceRequest getRequest(ServiceRequestType type, String requestID){
        return myMap.get(type).get(requestID);
    }

    public boolean containsRequest(ServiceRequestType type, String requestID){
        return myMap.get(type).containsKey(requestID);
    }

    public int getSize(ServiceRequestType type){
        return myMap.get(type).size();
    }
}
