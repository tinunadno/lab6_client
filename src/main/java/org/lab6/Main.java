package org.lab6;

import org.lab6.mainClasses.*;


import java.net.InetAddress;

public class Main {

    private static int port=6453;
    private static int serverPort=6454;
    private static InetAddress address=null;
    public static void main(String[] args) {
        try {
            address = InetAddress.getLocalHost();
        }catch(Exception e){
            System.out.println("cannot find host");
        }
        ServerListInitializer.init();
        try{Thread.sleep(500);}catch(Exception e){}
        SendedCommand sendedCommand=new SendedCommand("synchronize", false, "", false, null);
        UDP_transmitter.send(getPort(), address, sendedCommand);
        CommandListSynchronizer.synchronizeCommandListWithClient();
        ClientCommandsMonitor.startMonitoring();
    }
    public static int getPort(){return port;}
    public static int getServerPort(){return serverPort;}
    public static InetAddress getAdress(){return address;}

}