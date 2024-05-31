package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.storedClasses.LabWork;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;

public class UserInterfaceForm {
    private static UserInterfaceForm uif;
    private String userName;
    private ArrayList<String> commandName;
    private static ArrayList<LabWork> currentCollection;
    private JFrame mainFrame;
    private String sortingParameter;
    private String filterPattern;
    private String filterParameter;
    private HashMap<String, Color> usersColor=new HashMap<>();
    private final String[] header={"id","userID", "userName", "price", "name", "minimalPoint", "description", "tunedInWorks", "difficulty", "author"};
    JTable table;
    public UserInterfaceForm(String userName){
        uif=this;
        sortingParameter="id";
        filterParameter="id";
        filterPattern="";
        initCommands();
        updateLabWorkList();
        initComponents();
        this.userName=userName;
    }
    private void initComponents(){
        mainFrame=new JFrame("Yura's lab base");
        //frame settings
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH;
        JPanel buttonPanel=displayCommands();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 1;
        mainFrame.add(buttonPanel, gbc);


        JPanel listPanel=displayCollection();
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        mainFrame.add(listPanel, gbc);

        mainFrame.pack();
        mainFrame.setVisible(true);


    }
    private JPanel displayCommands(){
        JPanel commandPanel=new JPanel();
        commandPanel.setLayout(new GridLayout(commandName.size()+2, 1));
        commandPanel.add(new JLabel("commands"));
        for(String command:commandName){
            JButton button=new JButton(command.replace("_", " "));
            button.addActionListener(e->{
                CommandReg.invoke(button.getText().replace(" ", "_"));
            });
            commandPanel.add(button);
        }
        JButton updateButton=new JButton("update list");
        updateButton.addActionListener(e->{
            updateCollection();
        });
        commandPanel.add(updateButton);
        return commandPanel;
    }
    private JPanel displayCollection(){
        String[][] labWorks=new String[currentCollection.size()][10];
        for(int i=0;i<currentCollection.size();i++){
            labWorks[i]=currentCollection.get(i).getFields();
        }
        DefaultTableModel dtm=new DefaultTableModel(labWorks, header);
        table=new JTable(dtm);
        table.setFillsViewportHeight(true);

        ListSelectionModel selectModel= table.getSelectionModel();
        selectModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    long res = Long.parseLong((String) (table.getModel().getValueAt(table.getSelectedRow(), 0)));
                    LabWork lwInstance = currentCollection.stream().filter(lw -> (lw.getID() == res)).findFirst().orElse(null);
                    new LabWorkEditForm(lwInstance, lwInstance.getUserName().equals(userName));
            }
        });

        JScrollPane js=new JScrollPane(table);
        JPanel listPanel=new JPanel();
        listPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        //
        listPanel.add(displayFilters(), gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        listPanel.add(js, gbc);

        return listPanel;
    }
    private JPanel displayFilters(){
        JPanel ret=new JPanel();
        ret.setLayout(new GridLayout(1,5));
        JComboBox comboBox=new JComboBox(header);
        comboBox.addActionListener(e->{
            sortingParameter=String.valueOf(comboBox.getSelectedItem());
            updateTable();
        });
        ret.add(new JLabel("sort by:"));
        ret.add(comboBox);
        ret.add(new JLabel("filter by:"));
        JTextField filterQuery=new JTextField();
        filterQuery.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {warn();}
            public void removeUpdate(DocumentEvent e) {warn();}
            public void insertUpdate(DocumentEvent e) {warn();}
            public void warn() {filterPattern=filterQuery.getText();
                updateTable();
            }
        });
        ret.add(filterQuery);
        JComboBox filterComboBox=new JComboBox(header);
        filterComboBox.addActionListener(e->{
            filterParameter=String.valueOf(filterComboBox.getSelectedItem());
            updateTable();
        });
        ret.add(filterComboBox);
        JButton dropToDefault=new JButton("Default filters");
        dropToDefault.addActionListener(e->{
            comboBox.setSelectedItem("id");
            filterComboBox.setSelectedItem("id");
            filterQuery.setText("");
            updateTable();
        });
        ret.add(dropToDefault);
        return ret;

    }

    private void initCommands(){
        SendedCommand command=new SendedCommand("get_user_available_commands", false, null, false, null);
        Client_UDP_Transmitter.sendObject(command);
        commandName=((ArrayList<String>) Client_UDP_Transmitter.getObject());
    }

    private void updateLabWorkList(){
        SendedCommand command=new SendedCommand("get_lab_work_list", false, null, false, null);
        Client_UDP_Transmitter.sendObject(command);
        currentCollection=((ArrayList<LabWork>) Client_UDP_Transmitter.getObject());
    }
   private void updateTable(){
        ArrayList<LabWork> filteredList=new ArrayList<LabWork>(currentCollection.stream().filter(lw->lw.getParameterByName(filterParameter).indexOf(filterPattern)!=-1).toList());
        Collections.sort(filteredList, Comparator.naturalOrder());
        filteredList.sort((lw, lw1)->{
            String s1=lw.getParameterByName(sortingParameter);
            String s2=lw1.getParameterByName(sortingParameter);
            return s1.compareTo(s2);
        });
        String[][] labWorks=new String[currentCollection.size()][10];
        for(int i=0;i<filteredList.size();i++){
            labWorks[i]=filteredList.get(i).getFields();
        }

        DefaultTableModel dtm=new DefaultTableModel(labWorks, header);
        dtm.setRowCount(filteredList.size());
        try {
            table.setModel(dtm);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("asd");
        }

    }
    public static void updateCollection(){
        SendedCommand sendedCommand=new SendedCommand("get_lab_work_list", false, null, false, null);
        Client_UDP_Transmitter.sendObject(sendedCommand);
        ArrayList<LabWork> acceptedList=(ArrayList<LabWork>) (Client_UDP_Transmitter.getObject());
        currentCollection=acceptedList;
        uif.updateTable();
    }
}
