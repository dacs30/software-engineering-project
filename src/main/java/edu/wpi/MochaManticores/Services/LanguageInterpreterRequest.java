package edu.wpi.MochaManticores.Services;

public class LanguageInterpreterRequest extends ServiceRequest{
    private String room;
    private String languageOne;
    private String languageTwo;


    public LanguageInterpreterRequest(String RequestID, String employee, boolean completed, String room,
                                      String languageOne, String languageTwo) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.LanguageInterperter);
        }
        this.room = room;
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
    }

    public String getRoom() {
        return room;
    }

    public String getLanguageOne() {
        return languageOne;
    }

    public String getLanguageTwo() {
        return languageTwo;
    }
}
