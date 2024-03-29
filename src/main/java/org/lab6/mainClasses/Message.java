package org.lab6.mainClasses;

import org.lab6.Main;

import java.io.Serializable;

public class Message implements Serializable {
    private static Message currentMessage;
    private String message;
    static{
        currentMessage=new Message();
    }
    public Message(){
        message="";
    }
    public static void update(){
        currentMessage=new Message();
    }
    public void append_object(String word){
        this.message+=word;
    }
    public static void append(String word){
        currentMessage.append_object(word);
    }
    public void sentMessage_object(){
        UDP_transmitter.send(Main.getPort(), Main.getAdress(), this);

    }
    public static void sentMessage(){
        currentMessage.sentMessage_object();
        Message.update();
    }
    public static boolean isEmpty(){
        return currentMessage.getMessage().equals("");
    }

    public String getMessage(){return message;}
}
