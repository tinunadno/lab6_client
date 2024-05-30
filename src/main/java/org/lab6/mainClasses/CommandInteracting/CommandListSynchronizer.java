package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import java.util.ArrayList;

public class CommandListSynchronizer {
    public static void synchronizeCommandListWithClient(){
        try {
            SendedCommand sendedCommand=new SendedCommand("synchronize", false, "", false, null);
            Client_UDP_Transmitter.sendObject(sendedCommand);
            CommandReg.setCommandNames((ArrayList<ArrayList<String>>) Client_UDP_Transmitter.getObject());
        }catch(NullPointerException e){
            System.out.println("failed to synchronize command list with server");
        }catch(ClassCastException e){
            System.out.println("unexpected object");
        }
    }
}
