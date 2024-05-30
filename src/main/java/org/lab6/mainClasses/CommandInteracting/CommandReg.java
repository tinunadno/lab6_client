package org.lab6.mainClasses.CommandInteracting;
import org.lab6.localComands.Command;
import org.lab6.localComands.CommandWithArgument;
import org.lab6.localComands.exit;
import org.lab6.localComands.saveOnClient;
import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandReg {
    private static ArrayList<ArrayList<String>> commandNames;
    private static Map<String, Command> commands=new HashMap<>();
    static{
        commands.put("exit", new exit());
        commands.put("save_on_client", new saveOnClient());
    }
    public static void setCommandNames(ArrayList<ArrayList<String>> CommandNames){commandNames=CommandNames;}

    public static void printCommands(){
        for(ArrayList<String> arrayList:commandNames){
            for(String value:arrayList)
                System.out.println(value);
        }
    }
    public static void invoke(String command){
        if(command.length()-(command.replace(" ", "").length())!=0){
            //separating command name and its argument
            String commandArgument=command.substring(command.indexOf(' ')+1, command.length());
            command=command.substring(0, command.indexOf(' '));
            try{
                ((CommandWithArgument)(commands.get(command))).setArgument(commandArgument);
                commands.get(command).execute();
            }catch(NullPointerException e) {
                try {
                SendedCommand sendedCommand = null;
                if (commandNames.get(2).contains(command)) {
                    (new LabWorkParser()).parseLabWork(command, commandArgument);
                }
                else {
                    sendedCommand = new SendedCommand(command, true, commandArgument, false, null);
                    Client_UDP_Transmitter.sendObject(sendedCommand);
                }
                }catch (NullPointerException e1){
                    System.out.println("connection timed out, failed to get anwser from server");
                }
            }
        }
        else{
            try{
                commands.get(command).execute();
            }catch (NullPointerException e) {
                try {
                SendedCommand sendedCommand;
                if (commandNames.get(2).contains(command)) {
                    (new LabWorkParser()).parseLabWork(command, null);
                }
                else {
                    sendedCommand = new SendedCommand(command, false, "", false, null);
                    Client_UDP_Transmitter.sendObject(sendedCommand);
                }
                }catch(NullPointerException e1){
                    System.out.println("connection timed out, failed to get anwser from server");
                }
            }
        }
    }

}
