/*
 * Created by JFormDesigner on Thu May 30 15:03:47 MSK 2024
 */

package org.lab6.mainClasses.Authorize;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.lab6.mainClasses.CommandInteracting.CommandListSynchronizer;
import org.lab6.mainClasses.CommandInteracting.UserInterfaceForm;
import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import java.awt.*;

/**
 * @author User
 */
public class UserAuthorizeForm{
    private JFrame frame;
    private String userName;
    private String password;

    public UserAuthorizeForm() {
        initComponents();
    }

    private void initComponents() {
        userName="";
        password="";
        frame=new JFrame("Authorization");
        frame.setLayout(new GridLayout(3,2));
        //frame settings
        frame.setSize(new Dimension(300, 100));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //adding name Textfield and label
        frame.add(new JLabel("UserName:"));
        JTextField nameTextField=new JTextField();
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {warn();}
            public void removeUpdate(DocumentEvent e) {warn();}
            public void insertUpdate(DocumentEvent e) {warn();}
            public void warn() {userName=nameTextField.getText();}
        });
        frame.add(nameTextField);
        //adding password Textfield and label
        frame.add(new JLabel("password:"));
        JTextField passwordTextField=new JTextField();
        passwordTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {warn();}
            public void removeUpdate(DocumentEvent e) {warn();}
            public void insertUpdate(DocumentEvent e) {warn();}
            public void warn() {password=passwordTextField.getText();}
        });
        frame.add(passwordTextField);
        //adding buttons
        JButton authorizeButton=new JButton("authorize");
        authorizeButton.addActionListener(e->{authorizeUser();});
        frame.add(authorizeButton);
        JButton registerButton=new JButton("register");
        registerButton.addActionListener(e->{registerNewUser();});
        frame.add(registerButton);
        frame.setVisible(true);
    }
    private void registerNewUser(){
        SendedCommand commandName=new SendedCommand("authorize_name_r", true, userName, false, null);
        Client_UDP_Transmitter.sendObject(commandName);
        if((((Message) Client_UDP_Transmitter.getObject()).getMessage()).equals("successfully added new user name\n")) {
            SendedCommand commandPassword = new SendedCommand("authorize_password_r", true, password, false, null);
            Client_UDP_Transmitter.sendObject(commandPassword);
            if (!(((Message) Client_UDP_Transmitter.getObject()).getMessage()).equals("successfully inserted new user password\n"))
                JOptionPane.showMessageDialog(null, "user with this name already exists", "register error", JOptionPane.INFORMATION_MESSAGE);
            else{
                JOptionPane.showMessageDialog(null, "successfully registered new user, you can authorize now", "register success", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "user with this name already exists", "register error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void authorizeUser(){
        SendedCommand commandName=new SendedCommand("authorize_name_a", true, userName, false, null);
        Client_UDP_Transmitter.sendObject(commandName);
        String msg=((Message) Client_UDP_Transmitter.getObject()).getMessage();
        System.out.println(msg);
        if(msg.equals("successfully authorized user name\n")) {
            SendedCommand commandPassword = new SendedCommand("authorize_password_a", true, password, false, null);
            Client_UDP_Transmitter.sendObject(commandPassword);
            if (!(((Message) Client_UDP_Transmitter.getObject()).getMessage()).equals("successfully authorized!\n"))
                JOptionPane.showMessageDialog(null, "wrong password", "register error", JOptionPane.INFORMATION_MESSAGE);
            else{
                JOptionPane.showMessageDialog(null, "successfully authorized", "authorize success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                CommandListSynchronizer.synchronizeCommandListWithClient();
                //ClientCommandsMonitor.startMonitoring();
                new UserInterfaceForm();
            }
        }else{
            JOptionPane.showMessageDialog(null, "can't find user with this name", "authorize error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
