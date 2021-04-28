package edu.wpi.MochaManticores.Services;

public class LaundryRequest extends ServiceRequest {
    private String name;
    private String soilLevel;
    private boolean delicates;
    private String washCycleTemperature;
    private String dryCycleTemperature;
    private int dryCycleNumber;


    public LaundryRequest(String RequestID,String employee, boolean completed, String patientName, String soilLevel,
                          boolean delicates, String washCycleTemperature, String dryCycleTemperature, int dryCycleNumber) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.Laundry);
        }
        this.name = patientName;
        this.soilLevel = soilLevel;
        this.delicates = delicates;
        this.washCycleTemperature = washCycleTemperature;
        this.dryCycleTemperature = dryCycleTemperature;
        this.dryCycleNumber = dryCycleNumber;
    }

    public String getName() {
        return name;
    }

    public String getSoilLevel() {
        return soilLevel;
    }

    public boolean isDelicates() {
        return delicates;
    }

    public String getWashCycleTemperature() {
        return washCycleTemperature;
    }

    public String getDryCycleTemperature() {
        return dryCycleTemperature;
    }

    public int getDryCycleNumber() {
        return dryCycleNumber;
    }
}
