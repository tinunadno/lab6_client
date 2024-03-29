package org.lab6.mainClasses;

import org.jetbrains.annotations.NotNull;
import org.lab6.Main;
import org.lab6.localComands.Command;
import org.lab6.localComands.CommandWithArgument;
import org.lab6.localComands.exit;
import org.lab6.localComands.saveOnClient;

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
    public static void invoke(@NotNull String command){
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
                if (commandNames.get(2).contains(command))
                    sendedCommand = new SendedCommand(command, true, commandArgument, true, LabWorkParser.parseLabWorkFromConsole());
                else
                    sendedCommand = new SendedCommand(command, true, commandArgument, false, null);
                UDP_transmitter.send(Main.getPort(), Main.getAdress(), sendedCommand);
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
                if (commandNames.get(2).contains(command))
                    sendedCommand = new SendedCommand(command, false, "", true, LabWorkParser.parseLabWorkFromConsole());
                else
                    sendedCommand = new SendedCommand(command, false, "", false, null);
                UDP_transmitter.send(Main.getPort(), Main.getAdress(), sendedCommand);
                }catch(NullPointerException e1){
                    System.out.println("connection timed out, failed to get anwser from server");
                }
            }
        }
    }

}
