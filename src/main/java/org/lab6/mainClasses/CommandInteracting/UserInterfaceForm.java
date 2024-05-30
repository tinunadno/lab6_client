package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserInterfaceForm {
    private static boolean wait=true;
    public static void setWait(boolean value){wait=value;}
    private ArrayList<String> commandName;
    private ArrayList<String> commandExecutableName;
    public UserInterfaceForm(){
        initCommands();
        initComponents();
    }
    JFrame mainFrame;
    private void initComponents(){
        mainFrame=new JFrame("Yura's lab base");
        //frame settings
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setLayout(new GridLayout(1,2));



        mainFrame.add(displayCommands());
        mainFrame.setVisible(true);

    }
    private JPanel displayCommands(){
        JPanel commandPanel=new JPanel();
        commandPanel.setLayout(new GridLayout(commandName.size()+1, 1));
        commandPanel.add(new JLabel("commands"));
        for(String command:commandName){
            JButton button=new JButton(command.replace("_", " "));
            button.addActionListener(e->{
                CommandReg.invoke(button.getText().replace(" ", "_"));
                try{
                    if(wait) {
                        Message message=(Message) Client_UDP_Transmitter.getObject();
                        JOptionPane.showMessageDialog(null, message.getMessage(), "register success", JOptionPane.INFORMATION_MESSAGE);
                    }else
                        wait=true;
                }catch (NullPointerException e1){
                    System.out.println("connection timed out, failed to get anwser from server");
                }
            });
            commandPanel.add(button);
        }
        return commandPanel;
    }

    private void initCommands(){
        SendedCommand command=new SendedCommand("get_user_available_commands", false, null, false, null);
        Client_UDP_Transmitter.sendObject(command);
        commandName=((ArrayList<String>) Client_UDP_Transmitter.getObject());
    }
}
