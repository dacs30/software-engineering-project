package edu.wpi.MochaManticores.Services;

public class ReligiousRequest extends ServiceRequest{
    private final String reasonVisit;
    private final String location;
    private final String typeSacredPerson;


    public ReligiousRequest(String RequestID, String employee, boolean completed, String reasonVisit,
                            String location, String typeSacredPerson) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.ReligiousRequest);
        }
        this.reasonVisit = reasonVisit;
        this.location = location;
        this.typeSacredPerson = typeSacredPerson;
    }

    public String getReasonVisit() {
        return reasonVisit;
    }

    public String getLocation() {
        return location;
    }

    public String getTypeSacredPerson() {
        return typeSacredPerson;
    }
}
