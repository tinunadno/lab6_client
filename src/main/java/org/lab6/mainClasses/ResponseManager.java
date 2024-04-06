package org.lab6.mainClasses;

import org.lab6.Main;

public class ResponseManager {
    private static String responses="";
    public static void append(String Message){
        responses+=Message+"\n";
    }
    public static void sendMessage(){
        if(!responses.equals("")) {
            Message response = new Message(responses);
            UDP_transmitter.send(Main.getPort(), Main.getAdress(), response);
        }
        responses="";
    }
}
