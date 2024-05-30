package org.lab6.mainClasses.UDPInteraction;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    private String message;
    private UUID token;
    public Message(String message){
        this.message=message;
    }
    public Message(String message, UUID token){
        this.message=message;
        this.token=token;
    }
    public String getMessage(){
        return message;
    }
    public UUID getToken(){return this.token;}
}
