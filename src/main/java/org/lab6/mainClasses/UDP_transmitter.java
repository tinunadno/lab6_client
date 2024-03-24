package org.lab6.mainClasses;

import org.lab6.Main;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_transmitter {
    public static void send(int port, InetAddress host, Object object){
        try {
            DatagramSocket ds = new DatagramSocket();

            //Serializing Object
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.close();
            byte[] objBytes = bos.toByteArray();

            //sending serialized object info
            byte[] len={(byte)(objBytes.length-(int)(objBytes.length/128)*128), (byte)(objBytes.length/128)};
            DatagramPacket dp=new DatagramPacket(len, 2, host, port);
            ds.send(dp);

            //sending object
            dp=new DatagramPacket(objBytes, objBytes.length, host, port);
            ds.send(dp);
            ds.close();
            Main.incPort();
        }catch(Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
    }

    public static<T> T get(int port){
        try {
            DatagramSocket ds = new DatagramSocket(port);
            //getting object info
            byte[] len=new byte[2];
            DatagramPacket dp = new DatagramPacket(len, 2);
            ds.receive(dp);

            //getting object
            byte[] arr = new byte[len[1]*128+len[0]];
            dp = new DatagramPacket(arr, len[1]*128+len[0]);
            ds.receive(dp);
            ds.close();
            //deserializing object
            ByteArrayInputStream bis = new ByteArrayInputStream(arr);
            ObjectInput in = new ObjectInputStream(bis);
            Main.incPort();
            return (T)in.readObject();

        }catch(Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
        return null;
    }
}
