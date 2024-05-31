package org.lab6.mainClasses;

import org.lab6.mainClasses.CommandInteracting.UserInterfaceForm;
import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.storedClasses.LabWork;

import java.util.ArrayList;

public class ListUpdateManager extends Thread{
    @Override
    public void run(){
        SendedCommand sendedCommand=new SendedCommand("get_lab_work_list", true, "", false, null);
        while(true){
            Client_UDP_Transmitter.sendObject(sendedCommand);
            ArrayList<LabWork> acceptedList=(ArrayList<LabWork>) ( Client_UDP_Transmitter.getObject());
            if(acceptedList!=null) {
                UserInterfaceForm.updateCollection(acceptedList);
            }else{
            }
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){

            }
        }
    }
}
