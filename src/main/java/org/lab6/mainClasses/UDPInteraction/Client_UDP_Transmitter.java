package org.lab6.mainClasses.UDPInteraction;

import org.lab6.mainClasses.CommandInteracting.LabWorkEditForm;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client_UDP_Transmitter {
    private static Client_UDP_Transmitter transmitter;

    public static void sendObject(Object obj) {
        transmitter.send(obj);
    }

    public static Object getObject() {
        return transmitter.get();
    }

    private InetSocketAddress serverAddress;
    private ByteBuffer serverAcceptBuffer;
    private DatagramChannel channel;

    public Client_UDP_Transmitter(InetAddress serverAdress, int serverPort) {
        if (transmitter == null) {
            serverAddress = new InetSocketAddress(serverAdress, serverPort);
            serverAcceptBuffer = ByteBuffer.allocate(10000);
            try {
                channel = DatagramChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            transmitter = this;
        }
    }

    public void send(Object sendingObject) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(sendingObject);
            oos.close();
            ByteBuffer buffer = ByteBuffer.wrap(bos.toByteArray());
            channel.send(buffer, serverAddress);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get() {
        try {
            serverAddress = (InetSocketAddress) channel.receive(serverAcceptBuffer);
            serverAcceptBuffer.flip();
            int limits = serverAcceptBuffer.limit();
            byte bytes[] = new byte[limits];
            serverAcceptBuffer.get(bytes, 0, limits);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            Object ret = in.readObject();
            serverAcceptBuffer.clear();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearChannel() {
        try {
            transmitter.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
