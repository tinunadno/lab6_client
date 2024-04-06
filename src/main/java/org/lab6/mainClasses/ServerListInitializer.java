package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.LabWork;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerListInitializer {
    private static Scanner sc;
    public static void init(){
        sc=new Scanner(System.in);
        System.out.print("boot from client or server (c/s)");
        String clientChose=sc.next();
        if(clientChose.charAt(0)=='c'){
            getFileFromWay();
            System.out.println("successfully initialized from client device");
        }else if(clientChose.charAt(0)=='s'){
            Message message=new Message("server_boot");
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), message);
            message=UDP_transmitter.get(Main.getServerPort());
            try {
                System.out.println(message.getMessage());
            }catch(NullPointerException e){
                System.out.println("connection timed out, failed to connect to server");
            }
        }
    }
    private static void getFileFromWay(){
        boolean flag=false;
        sc.nextLine();
        while(!flag){
            System.out.print("insert way to boot file, insert \"null\" to create new collection:");
            flag=tryToGetWay();
        }

    }

    private static boolean tryToGetWay(){
        try{
            String message=(sc.nextLine()).replace("\"", "").strip();
            if(message.equals("null")) {
                ArrayList<LabWork> labWorks=new ArrayList();
                UDP_transmitter.send(Main.getPort(), Main.getAdress(),labWorks);
            }
            else {
                ArrayList<LabWork> temp = JsonToLabWork.getLabWork(message);
                UDP_transmitter.send(Main.getPort(),Main.getAdress(),temp);
            }
        }catch(FileNotFoundException e){
            System.out.println("bad file name");
            return false;
        }
        return true;
    }
}
