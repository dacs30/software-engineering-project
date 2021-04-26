package edu.wpi.MochaManticores.Services;


public class FloralDelivery extends ServiceRequest{
    private String roomNumber;
    private String deliveryChoice;
    private String typeOfFlowers;
    private String vaseOptions;
    private String personalizedNote;

    public FloralDelivery(boolean employee, boolean completed, int row, String roomNumber, String deliveryChoice,
                          String typeOfFlowers, String vaseOptions, String personalizedNote) {
        super(employee, completed, row);
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

    public String[] getFields() {
        return new String[]{
                String.valueOf(ServiceMap.FloralDelivery),
                roomNumber,
                deliveryChoice,
                typeOfFlowers,
                vaseOptions,
                personalizedNote
        };
    }
}
