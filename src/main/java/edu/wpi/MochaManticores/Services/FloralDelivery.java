package edu.wpi.MochaManticores.Services;


public class FloralDelivery extends ServiceRequest{
    private final String roomNumber;
    private final String deliveryChoice;
    private final String typeOfFlowers;
    private final String vaseOptions;
    private final String personalizedNote;

    public FloralDelivery(String RequestID, String employee, boolean completed, String roomNumber, String deliveryChoice,
                          String typeOfFlowers, String vaseOptions, String personalizedNote) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.FloralDelivery);
        }
        this.roomNumber = roomNumber;
        this.deliveryChoice = deliveryChoice;
        this.typeOfFlowers = typeOfFlowers;
        this.vaseOptions = vaseOptions;
        this.personalizedNote = personalizedNote;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getDeliveryChoice() {
        return deliveryChoice;
    }

    public String getTypeOfFlowers() {
        return typeOfFlowers;
    }

    public String getVaseOptions() {
        return vaseOptions;
    }

    public String getPersonalizedNote() {
        return personalizedNote;
    }

}
