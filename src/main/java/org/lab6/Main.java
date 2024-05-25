package org.lab6;

import org.lab6.mainClasses.*;


import java.net.InetAddress;
import java.util.UUID;

public class Main {

    private static int port=17938;
    private static InetAddress address=null;
    private static UUID userToken;
    public static void main(String[] args) {

        try {
            //address = InetAddress.getLocalHost();
            address=InetAddress.getByName("192.168.10.80");
        }catch(Exception e){
            System.out.println("cannot find host");
        }
        new Client_UDP_Transmitter(address, port);

        Client_UDP_Transmitter.sendObject("getUserToken");
        Main.setUserToken(((Message)(Client_UDP_Transmitter.getObject())).getToken());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    SendedCommand disconnectCommand=new SendedCommand("disconnect",false,"",false,null);
                    Client_UDP_Transmitter.sendObject(disconnectCommand);
                    Client_UDP_Transmitter.clearChannel();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });


        UserAuthorizer.authorize();
        try{Thread.sleep(500);}
        catch(InterruptedException e){}
        SendedCommand sendedCommand=new SendedCommand("synchronize", false, "", false, null);
        Client_UDP_Transmitter.sendObject(sendedCommand);
        CommandListSynchronizer.synchronizeCommandListWithClient();
        ClientCommandsMonitor.startMonitoring();
    }
    public static UUID getUserToken(){return userToken;}
    public static void setUserToken(UUID token){userToken=token;}
}