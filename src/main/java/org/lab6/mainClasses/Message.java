package org.lab6.mainClasses;

import org.lab6.Main;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    public Message(String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
}
