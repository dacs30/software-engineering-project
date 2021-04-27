package edu.wpi.MochaManticores.Services;

public class ReligiousRequest extends ServiceRequest{
    private String reasonVisit;
    private String location;
    private String typeSacredPerson;


    public ReligiousRequest(String employee, String RequestID, boolean completed, String reasonVisit,
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
