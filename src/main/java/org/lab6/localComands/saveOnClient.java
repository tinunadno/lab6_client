package org.lab6.localComands;

import org.lab6.Main;
import org.lab6.mainClasses.CommandReg;
import org.lab6.mainClasses.Message;
import org.lab6.mainClasses.UDP_transmitter;

import java.io.FileWriter;

public class saveOnClient extends Command implements CommandWithArgument{
    private String argument;
    public void setArgument(String argument){this.argument=argument;}
    @Override
    public void execute(){
        CommandReg.invoke("get_list_as_json");
        Message message= UDP_transmitter.get(Main.getPort());
        String jsonString=message.getMessage();
        try (var fw = new FileWriter(argument.replaceAll("\"", ""))) {
            fw.write(jsonString);
            System.out.println("successfully saved current LabWork List on client");
        } catch (Exception e) {
            System.out.println("bad file name");
        }
    }
    @Override
    public String getComment(){return "save_on_client%сохраняет LabWork List на машину клиента по заданному пути";}
}