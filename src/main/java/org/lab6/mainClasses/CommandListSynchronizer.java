package org.lab6.mainClasses;

import org.lab6.Main;

public class CommandListSynchronizer {
    public static void synchronizeCommandListWithClient(){
        CommandReg.setCommandNames(UDP_transmitter.get(Main.getPort()));
    }
}
