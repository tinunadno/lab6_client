package org.lab6.mainClasses;

import org.lab6.storedClasses.LabWork;

import java.io.Serializable;

public class SendedCommand implements Serializable {
    private String commandName;
    private boolean argumentExists;
    private String argument;
    private boolean needParsedInstance;
    private LabWork parsedInstance;
    public SendedCommand(String commandName, boolean argumentExists, String argument, boolean needParsedInstance, LabWork parsedInstance){
        this.commandName=commandName;
        this.argumentExists=argumentExists;
        this.argument=argument;
        this.needParsedInstance=needParsedInstance;
        this.parsedInstance=parsedInstance;
    }
    public String getCommandName(){return commandName;}
    public boolean isArgumentExists(){return argumentExists;}
    public String getArgument(){return argument;}
    public boolean isNeedParsedInstance(){return needParsedInstance;}
    public LabWork getParsedInstance(){return parsedInstance;}
}

