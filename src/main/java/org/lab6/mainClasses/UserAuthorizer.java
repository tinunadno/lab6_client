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
        UDP_transmitter.send(Main.getPort(), Main.getAdress(), option);
        if(option.equals("r")){
            System.out.print("insert new user name:");
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), (new Message(in.next())));

            System.out.print("insert new user password:");
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), (new Message(in.next())));
            System.out.println(((Message)UDP_transmitter.get(Main.getServerPort())).getMessage());
        }
        tryToSend("insert user name:");
        tryToSend("insert password:");
        System.out.println("successfully authorized!");
    }
    private static void tryToSend(String message){
        System.out.print(message);
        Scanner in=new Scanner(System.in);
        String response="";
        while (true){
            String userData=in.next();
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), new Message(userData));
            response=((Message)UDP_transmitter.get(Main.getServerPort())).getMessage();
            if(response.equals("SUCCESS"))break;
            else System.out.println(response);
        }

    }
}
