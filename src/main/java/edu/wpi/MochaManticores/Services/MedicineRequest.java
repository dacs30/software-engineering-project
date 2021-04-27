package edu.wpi.MochaManticores.Services;

public class MedicineRequest extends ServiceRequest{
    private String typeMedicine;
    private String currentFeeling;
    private String allergies;
    private String patientRoom;

    public MedicineRequest(String employee, String RequestID, boolean completed, String typeMedicine,
                           String currentFeeling, String allergies, String patientRoom) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.Medicine);
        }
        this.typeMedicine = typeMedicine;
        this.currentFeeling = currentFeeling;
        this.allergies = allergies;
        this.patientRoom = patientRoom;
    }

    public String getTypeMedicine() {
        return typeMedicine;
    }

    public String getCurrentFeeling() {
        return currentFeeling;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getPatientRoom() {
        return patientRoom;
    }
}
