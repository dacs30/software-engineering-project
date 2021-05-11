package edu.wpi.MochaManticores;

import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.messaging.messageClient;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

  public static boolean headless_run = false;

  // added comments so I can commit
  public static void main(String[] args) {
    if(args.length > 0 && args[0].equals("headless")){
      System.out.println("APPLICATION IS RUNNING HEADLESS");
      headless();

    }else{
      App.launch(App.class, args);
    }
  }

  public static void headless(){
    messageClient client = new messageClient();

    DatabaseManager.startup();
    client.startServer();

    Scanner scanner = new Scanner(System.in);

    try {
      while (true) {
        System.out.println("Type [stop] to close server safely");
        String line = scanner.nextLine();

        if (!line.isEmpty() && line.equals("stop")) {
          System.out.println("Shutting Down");
          DatabaseManager.shutdown();
          client.shutdown();
          return;
        }
      }
    }catch (Exception e){
      System.out.println("some exeption");
    }

  }
}
