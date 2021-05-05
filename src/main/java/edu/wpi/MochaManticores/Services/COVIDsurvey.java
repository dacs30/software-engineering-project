package edu.wpi.MochaManticores.Services;

public class COVIDsurvey extends ServiceRequest{
    private String name;
    private String DOB;
    private boolean sick;
    private boolean vaxx;
    private boolean travel;
    private boolean test;
    private boolean contact;
    private String Symptoms;
    private boolean admit;

    public COVIDsurvey(String RequestID, String employee, boolean completed,
                       String name, String DOB, boolean sick, boolean vaxx,
                       boolean travel, boolean test, boolean contact,
                       String Symptoms, boolean admit){
        super(employee, completed, RequestID);

        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.COVID);
        }

        this.name = name;
        this.DOB = DOB;
        this.sick = sick;
        this.vaxx = vaxx;
        this.travel = travel;
        this.test = test;
        this.contact = contact;
        this.Symptoms = Symptoms;
        this.admit = admit;
    }


    public static String SymptomsString(boolean cough, boolean fatigue,
            boolean headache, boolean congestion, boolean sore_throat, boolean muscle_ache,
            boolean breath, boolean nausea, boolean lost_taste, boolean palpitations,
            boolean fever, boolean diarrhea){
        // gross code because lazy, @ me (peter)
        String sympt = "";
        if(cough){
            sympt += "cough, ";
        }
        if(fatigue){
            sympt += "fatigue, ";
        }
        if(headache){
            sympt += "headache, ";
        }
        if(congestion){
            sympt += "congestion, ";
        }
        if(sore_throat){
            sympt += "sore throat, ";
        }
        if(muscle_ache){
            sympt += "muscle ache, ";
        }
        if(breath){
            sympt += "breathing, ";
        }
        if(nausea){
            sympt += "nausea, ";
        }
        if(lost_taste){
            sympt += "loss of taste, ";
        }
        if(palpitations){
            sympt += "palpitations, ";
        }
        if(fever){
            sympt += "fever, ";
        }
        if(diarrhea){
            sympt += "diarrhea, ";
        }
        return sympt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public boolean isSick() {
        return sick;
    }

    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public boolean isVaxx() {
        return vaxx;
    }

    public void setVaxx(boolean vaxx) {
        this.vaxx = vaxx;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public boolean isContact() {
        return contact;
    }

    public void setContact(boolean contact) {
        this.contact = contact;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

    public boolean isAdmit() {
        return admit;
    }

    public void setAdmit(boolean admit) {
        this.admit = admit;
    }

}
