package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.storedClasses.LabWork;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LabWorkEditForm extends LabWorkParser{
    private boolean isMyLabWork;
    public LabWorkEditForm(LabWork lwInstance, boolean isMyLabWork){
        super(null,  lwInstance, isMyLabWork);
        this.isMyLabWork=isMyLabWork;
        initComponents();
    }
    private void initComponents(){
        if(isMyLabWork) {
            JButton updateButton = new JButton("update");
            updateButton.addActionListener(e -> {
                LabWork labWork = parseLabWorkFromTExtFields();
                if (lw != null) {
                    SendedCommand sendedCommand = new SendedCommand("update", true, lw.getID() + "", true, labWork);
                    Client_UDP_Transmitter.sendObject(sendedCommand);
                    Message message = (Message) Client_UDP_Transmitter.getObject();
                    JOptionPane.showMessageDialog(null, message.getMessage(), "register success", JOptionPane.INFORMATION_MESSAGE);
                    parseFrame.dispose();
                }
            });
            JButton removeButton = new JButton("remove");
            removeButton.addActionListener(e -> {
                SendedCommand sendedCommand = new SendedCommand("remove_by_id", true, lw.getID() + "", false, null);
                Client_UDP_Transmitter.sendObject(sendedCommand);
                Message message = (Message) Client_UDP_Transmitter.getObject();
                JOptionPane.showMessageDialog(null, message.getMessage(), "register success", JOptionPane.INFORMATION_MESSAGE);
                parseFrame.dispose();
            });
            parseFrame.add(updateButton);
            parseFrame.add(removeButton);
            parseFrame.setVisible(true);
        }else{
            new BuyForm(lw);
        }
    }
}
