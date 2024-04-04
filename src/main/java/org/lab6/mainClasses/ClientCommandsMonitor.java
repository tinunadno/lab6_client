package org.lab6.mainClasses;

import org.lab6.Main;

import java.util.Scanner;

public class ClientCommandsMonitor {
    private static Scanner sc;
    public static void startMonitoring(){
        sc=new Scanner(System.in);
        while(true){
            CommandReg.invoke(sc.nextLine().strip());
            try{
                Message message=UDP_transmitter.get(Main.getServerPort());
                System.out.println(message.getMessage());
            }catch (NullPointerException e1){
                System.out.println("connection timed out, failed to get anwser from server");
            }
        }
    }
}
