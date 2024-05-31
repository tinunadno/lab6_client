package org.lab6.mainClasses.CommandInteracting;
import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandReg {
    private static ArrayList<ArrayList<String>> commandNames;
    public static void setCommandNames(ArrayList<ArrayList<String>> CommandNames){commandNames=CommandNames;}

    public static void printCommands(){
        for(ArrayList<String> arrayList:commandNames){
            System.out.println();
            for(String value:arrayList)
                System.out.println(value);
        }
    }
    public static void invoke(String command){
        if(commandNames.get(1).contains(command) && commandNames.get(2).contains(command)){
            //command with no argument, but with parsed instance
            new LabWorkParser(command,  null, true);
        }
        else if(commandNames.get(0).contains(command) && commandNames.get(2).contains(command)){
            //command with argument and parsed instance
            //???
        }
        else if(commandNames.get(0).contains(command)){
            //command with argument
            new ArgumentForm(command);
        }
        else if(commandNames.get(1).contains(command)){
            //command with no argument
            SendedCommand sendedCommand=new SendedCommand(command, false, "", false, null);
            Client_UDP_Transmitter.sendObject(sendedCommand);
            try{
                Message message=(Message) Client_UDP_Transmitter.getObject();
                JOptionPane.showMessageDialog(null, message.getMessage(), "server response", JOptionPane.INFORMATION_MESSAGE);
            }catch (NullPointerException e1){
                System.out.println("connection timed out, failed to get anwser from server");
            }
        }
    }

}
//(new LabWorkParser()).parseLabWork(command, null);