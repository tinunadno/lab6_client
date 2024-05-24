package org.lab6.mainClasses;

import org.lab6.Main;

import java.util.ArrayList;

public class CommandListSynchronizer {
    public static void synchronizeCommandListWithClient(){
        try {
            CommandReg.setCommandNames((ArrayList<ArrayList<String>>) Client_UDP_Transmitter.getObject());
        }catch(NullPointerException e){
            System.out.println("failed to synchronize command list with server");
        }catch(ClassCastException e){
            System.out.println("unexpected object");
        }
    }
}
