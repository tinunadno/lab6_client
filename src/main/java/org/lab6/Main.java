package org.lab6;

import org.lab6.mainClasses.*;


import java.net.InetAddress;

public class Main {

    private static int port=17937;
    private static int serverPort=17938;
    private static InetAddress address=null;
    public static void main(String[] args) {
        try {
            //address = InetAddress.getLocalHost();
            address=InetAddress.getByName("192.168.10.80");
        }catch(Exception e){
            System.out.println("cannot find host");
        }


        UDP_transmitter.send(port, address, "init");
        String[] ports=(((Message)UDP_transmitter.get(serverPort)).getMessage()).split("%");
        port=Integer.parseInt(ports[0]);
        serverPort=Integer.parseInt(ports[1]);
        System.out.println(port);
        System.out.println(serverPort);
        UserAuthorizer.authorize();
        try{Thread.sleep(500);}
        catch(InterruptedException e){}
        SendedCommand sendedCommand=new SendedCommand("synchronize", false, "", false, null);
        UDP_transmitter.send(getPort(), address, sendedCommand);
        CommandListSynchronizer.synchronizeCommandListWithClient();
        ClientCommandsMonitor.startMonitoring();
    }
    public static int getPort(){return port;}
    public static int getServerPort(){return serverPort;}
    public static InetAddress getAdress(){return address;}

}