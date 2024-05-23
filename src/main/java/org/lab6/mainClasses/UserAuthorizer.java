package org.lab6.mainClasses;

import org.lab6.Main;

import java.util.Scanner;

public class UserAuthorizer {
    public static void authorize(){
        Scanner in=new Scanner(System.in);
        System.out.print("do you want to authorize or register(a/r):");
        String option=in.next();
        while(!option.equals("r") && !option.equals("a")){
            System.out.println("you have to select, dow you want to authorize or register(a/r):");
            option=in.next();
        }
        if(option.equals("r")){
            System.out.print("insert new user name:");
            SendedCommand commandName=new SendedCommand("authorize_name_r", true, in.next(), false, null);
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), commandName);
            System.out.println(((Message)UDP_transmitter.get(Main.getServerPort())).getMessage());
            System.out.print("insert new user password:");
            SendedCommand commandPassword=new SendedCommand("authorize_password_r", true, in.next(), false, null);
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), commandPassword);
            System.out.println(((Message)UDP_transmitter.get(Main.getServerPort())).getMessage());
        }
        tryToSendUserName("insert user name:");
        tryToSendUserPassword("insert password:");
        System.out.println("successfully authorized!");
    }
    private static void tryToSendUserName(String message){
        System.out.print(message);
        Scanner in=new Scanner(System.in);
        String response="";
        while (true){
            SendedCommand commandPassword=new SendedCommand("authorize_name_a", true, in.next(), false, null);
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), commandPassword);
            response=((Message)UDP_transmitter.get(Main.getServerPort())).getMessage();
            if(response.equals("successfully authorized user name\n"))break;
            else System.out.println(response);
        }

    }
    private static void tryToSendUserPassword(String message){
        System.out.print(message);
        Scanner in=new Scanner(System.in);
        String response="";
        while (true){
            SendedCommand commandPassword=new SendedCommand("authorize_password_a", true, in.next(), false, null);
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), commandPassword);
            response=((Message)UDP_transmitter.get(Main.getServerPort())).getMessage();
            if(response.equals("successfully authorized!\n"))break;
            else System.out.println(response);
        }

    }
}
