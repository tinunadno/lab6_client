package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;

import java.util.Scanner;

public class ClientCommandsMonitor {
    private static Scanner sc;
    public static void startMonitoring(){
        sc=new Scanner(System.in);
        while(true){
            CommandReg.invoke(sc.nextLine().strip());
            try{
                Message message=(Message) Client_UDP_Transmitter.getObject();
                System.out.println(message.getMessage());
            }catch (NullPointerException e1){
                System.out.println("connection timed out, failed to get anwser from server");
            }
        }}
}
