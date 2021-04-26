package edu.wpi.MochaManticores.Services;

import edu.wpi.MochaManticores.database.Employee;

import java.awt.image.ImageProducer;
import java.io.*;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public abstract class ServiceRequest {
    private String employee;
    private boolean completed;
    private static String Services_csv_path = "data/services.csv";
    private static final String CSVdelim = ",";

    public boolean getCompleted(){
        return completed;
    }

    public void setCompleted(boolean b){
        completed = b;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public String RequestID;

    public ServiceRequest(String employee, boolean completed, String RequestID) {
        this.employee = employee;
        this.completed = completed;
        this.RequestID = RequestID;
    }




    public static void loadFromCSV(){
        //loads database
        try{
            //ALL EMPLOYEE SERVICE REQUESTS AND HAVE NOT BEEN COMPLETED
            BufferedReader reader = new BufferedReader(new FileReader(Services_csv_path));
            String line = reader.readLine();
            int rowNum = 0;

            while (line != null) {
                String[] row = line.split(CSVdelim);

                //check if it is a type of service
                boolean isService = false;
                for (ServiceRequestType serviceRequestType : ServiceRequestType.values()) {
                    if (row[0].equals(serviceRequestType.name())) {
                        isService = true;
                        break;
                    }
                }

                if (isService) {
                    switch (row[0]) {
                        case "Emergency":
                            EmergencyRequest emergencyRequest = new EmergencyRequest(true, false, rowNum,
                                    Integer.parseInt(row[1]), row[2], Boolean.parseBoolean(row[3]));

                            Servicemap.add()
                                        generate ID
                                        add to map (map based on service name and val -> is an object of service request)
                                        add to DB  (put in db w/ primary key ID, at table "NAME" with info from object)

                            ServiceMap.addToType(ServiceMap.Emergency, emergencyRequest);
                            break;
                        case "ExternalTransportation":
                            ExternalTransportation externalTransportation = new ExternalTransportation(true, false, rowNum,
                                    row[1], row[2], row[3], row[4]);
                            ServiceMap.addToType(ServiceMap.ExternalTransportation, externalTransportation);
                            break;
                        case "FloralDelivery":
                            FloralDelivery floralDelivery = new FloralDelivery(true, false, rowNum,
                                    row[1], row[2], row[3], row[4], row[5]);
                            ServiceMap.addToType(ServiceMap.FloralDelivery, floralDelivery);
                            break;
                        case "FoodDelivery":
                            FoodDelivery foodDelivery = new FoodDelivery(true, false, rowNum, row[1],
                                    row[2], row[3]);
                            ServiceMap.addToType(ServiceMap.FoodDelivery, foodDelivery);
                            break;
                        case "SanitationServices":
                            SanitationServices sanitationServices = new SanitationServices(true, false, rowNum,
                                    row[1], row[2], row[3], row[4], row[5]);
                            ServiceMap.addToType(ServiceMap.SanitationServices, sanitationServices);
                            break;
                        case "InternalTransportation":
                            InternalTransportation internalTransportation = new InternalTransportation(row[1],
                                    Integer.parseInt(row[2]), row[3], row[4], true, false, rowNum);
                            ServiceMap.addToType(ServiceMap.InternalTransportation, internalTransportation);
                            break;
                        default:
                            System.out.println("Non-Valid Service Request Type");
                    }
                line = reader.readLine();
                rowNum++;
            }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void delRequestFromCSV(ServiceRequest request) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        LinkedList<String[]> values = new LinkedList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Services_csv_path));
            String line = reader.readLine();

            while (line != null){
                String[] row = line.split(CSVdelim);

                values.add(row);
                line = reader.readLine();
            }

            values.remove(request.rowNum);

            PrintWriter pw = new PrintWriter(new File(Services_csv_path));
            for(String[] row: values) {
                //writing to csv file
                for (String s : row) {
                    sb.append(s);
                    sb.append(",");
                }
                sb.append("\n");
            }
            pw.write(sb.toString());
            pw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void addRequest(ServiceRequest request, ServiceRequestType type) {
        ServiceMap.addToType(type,request);
        StringBuilder sb = new StringBuilder();
        LinkedList<String[]> values = new LinkedList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Services_csv_path));
            String line = reader.readLine();

            while (line != null){
                String[] row = line.split(CSVdelim);

                values.add(row);
                line = reader.readLine();
            }

            int rowNum = values.size();
            request.setRowNum(rowNum);
            values.add(request.getFields());

            PrintWriter pw = new PrintWriter(new File(Services_csv_path));
            for(String[] row: values) {
                //writing to csv file
                for (String s : row) {
                    sb.append(s);
                    sb.append(",");
                }
                sb.append("\n");
            }
            pw.write(sb.toString());
            pw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
