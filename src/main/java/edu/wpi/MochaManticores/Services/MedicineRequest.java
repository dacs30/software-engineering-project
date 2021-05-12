package edu.wpi.MochaManticores.Services;

public class MedicineRequest extends ServiceRequest{
    private final String typeMedicine;
    private final String currentFeeling;
    private final String allergies;
    private final String patientRoom;

    public MedicineRequest(String RequestID, String employee, boolean completed, String typeMedicine,
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
