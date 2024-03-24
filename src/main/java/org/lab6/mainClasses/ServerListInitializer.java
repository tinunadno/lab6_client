package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.storedClasses.LabWork;

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
        }else if(clientChose.charAt(0)=='s'){
            new Message("server_boot");
        }
        System.out.println("Initialized succesfully!");
    }
    private static void getFileFromWay(){
        System.out.print("insert way to boot file, insert \"null\" to create new collection:");
        boolean flag=false;
        while(!flag){
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
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
