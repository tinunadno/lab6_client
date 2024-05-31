package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.storedClasses.LabWork;

import javax.swing.*;
import java.awt.*;

public class BuyForm {
    private LabWork lw;
    public BuyForm(LabWork lwInstance){
        lw=lwInstance;
        initComponents();
    }
    private void initComponents(){
        JFrame buyFrame=new JFrame("buyLabWork"){
            @Override
            public void dispose() {
                super.dispose();
            }
        };
        buyFrame.setSize(new Dimension(250, 100));
        buyFrame.setResizable(false);
        buyFrame.setLocationRelativeTo(null);

        buyFrame.setLayout(new GridLayout(2,1));
        SendedCommand sendedCommand=new SendedCommand("get_user_info", false, null, false, null);
        Client_UDP_Transmitter.sendObject(sendedCommand);
        String userInfo=((Message)(Client_UDP_Transmitter.getObject())).getMessage();
        double userWallet=Double.parseDouble(userInfo.substring(userInfo.indexOf("wallet: ")+8, userInfo.length()-2));
        buyFrame.add(new JLabel("your wallet: "+userWallet+"| LabWork price: "+lw.getPrice()));
        JButton buyButton=new JButton("buy");
        buyButton.addActionListener(e->{
            SendedCommand sendedCommand1=new SendedCommand("buy_lab_work", true, lw.getID()+"", false, null);
            Client_UDP_Transmitter.sendObject(sendedCommand1);
            String serverResponse=((Message)Client_UDP_Transmitter.getObject()).getMessage();
            JOptionPane.showMessageDialog(null, serverResponse,
                    "server response", JOptionPane.INFORMATION_MESSAGE);
            buyFrame.dispose();
        });
        buyFrame.add(buyButton);
        buyFrame.setVisible(true);
    }
}
