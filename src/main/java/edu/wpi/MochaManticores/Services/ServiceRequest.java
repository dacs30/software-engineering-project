package edu.wpi.MochaManticores.Services;

import edu.wpi.MochaManticores.database.Employee;

import java.io.*;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public abstract class ServiceRequest {
    private boolean employee;
    private boolean completed;
    private static String Services_csv_path = "data/services.csv";
    private static final String CSVdelim = ",";
    public int rowNum;

    public ServiceRequest(boolean employee, boolean completed, int rowNum) {
        this.employee = employee;
        this.completed = completed;
        this.rowNum = rowNum;
    }

    public static void loadFromCSV(Connection connection){
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

    public static void addRequest(ServiceRequest request) {
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

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String[] getFields() {
        return new String[1];
    }
}
