package org.lab6.mainClasses;

import org.lab6.Main;

public class CommandListSynchronizer {
    public static void synchronizeCommandListWithClient(){
        try {
            CommandReg.setCommandNames(UDP_transmitter.get(Main.getServerPort()));
        }catch(NullPointerException e){
            System.out.println("failed to synchronize command list with server");
        }
    }
}
