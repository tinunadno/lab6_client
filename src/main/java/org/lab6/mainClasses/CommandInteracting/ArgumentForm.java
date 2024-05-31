package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import javax.swing.*;
import java.awt.*;

public class ArgumentForm {
    private String commandName;
    public ArgumentForm(String commandName){
        this.commandName=commandName;
        initComponents();
    }
    private void initComponents(){
        JFrame argumentFrame=new JFrame(commandName);
        argumentFrame.setLayout(new GridLayout(1,2));
        argumentFrame.setSize(new Dimension(300, 100));
        argumentFrame.setResizable(false);
        argumentFrame.setLocationRelativeTo(null);

        JTextField argTextField=new JTextField();
        argumentFrame.add(argTextField);

        JButton button=new JButton("insert argument");
        button.addActionListener(e->{
            SendedCommand sendedCommand=new SendedCommand(commandName, true, argTextField.getText(), false, null);
            Client_UDP_Transmitter.sendObject(sendedCommand);
            try{
                Message message=(Message) Client_UDP_Transmitter.getObject();
                JOptionPane.showMessageDialog(null, message.getMessage(), "server response", JOptionPane.INFORMATION_MESSAGE);
            }catch (NullPointerException e1){
                System.out.println("connection timed out, failed to get anwser from server");
            }
            argumentFrame.dispose();
        });
        argumentFrame.add(button);
        argumentFrame.setVisible(true);
    }
}
