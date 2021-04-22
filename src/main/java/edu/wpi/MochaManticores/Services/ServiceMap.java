package edu.wpi.MochaManticores.Services;

import java.util.LinkedList;

public class ServiceMap {
    private static final LinkedList<IServiceRequest> serviceRequests = new LinkedList<>();

    /**
     * getServices()
     * @return a LinkedList of service requests
     */
    public static LinkedList<IServiceRequest> getServices() {
        return serviceRequests;
    }

    /**
     * function: delServiceRequest()
     * @param serviceRequest a serviceRequest that has been completed
     */
    public static void delServiceRequest(IServiceRequest serviceRequest) {
        serviceRequests.remove(serviceRequest);
    }
}
