package org.lab6.mainClasses;

import org.lab6.Main;

import java.util.Scanner;

public class ClientCommandsMonitor {
    private static Scanner sc;
    public static void startMonitoring(){
        sc=new Scanner(System.in);
        while(true){
            CommandReg.invoke(sc.nextLine().strip());
        }
    }
}
