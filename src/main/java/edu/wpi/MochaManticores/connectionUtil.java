package edu.wpi.MochaManticores;
/*
important information for server connection and management
 */
public class connectionUtil {
    public static boolean local = false;
    //LOCAL
    private static String host="localhost";
    private static int port=8001;
    public static String JDBC_SERVER = "jdbc:derby://localhost:1527/Mdatabase;create=true";


    //REMOTE
    public static String JDBC_REMOTE_SERVER = "jdbc:derby://129.213.114.11:1527/Mdatabase;create=true";
    private static String remoteHost = "129.213.114.11";
    public static int dbPort = 1527;
    private static int remotePort = 8100;


    public static String getHost(){
        if(connectionUtil.local){
            return host;
        }
        return remoteHost;
    }

    public static int getPort(){
        if(connectionUtil.local){
            return port;
        }
        return remotePort;
    }




}
