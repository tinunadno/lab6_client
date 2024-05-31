package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.storedClasses.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Function;

public class LabWorkParser {
    protected LabWork lw;
    protected JFrame parseFrame;
    private JTextField priceField;
    private JTextField labWorkNameTextField;
    private JTextField coordinatesX;
    private JTextField coordinatesY;
    private JTextField minPointField;
    private JTextField descField;
    private JTextField tunedInWorksField;
    private JTextField difficultyField;
    private JTextField personNameField;
    private JTextField personPassportField;
    private JTextField personEyeColorField;
    private JTextField locationX;
    private JTextField locationY;
    private JTextField locationNameField;

    public LabWorkParser(String commandName, LabWork lwInstance, boolean isMyLabWork) {
        lw = lwInstance;
        if (commandName == null && !isMyLabWork) {
            JOptionPane.showMessageDialog(null, "you aren't owner! If you want to edit this instance, you have to buy it",
                    "server response", JOptionPane.INFORMATION_MESSAGE);
        } else {
            parseFrame = new JFrame("LabWork parse");
            parseFrame.setLayout(new GridLayout(20, 2));

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("LabWork fields"));

            parseFrame.add(new JLabel("price<double>"));
            priceField = new JTextField();
            if (lw != null) priceField.setText(lw.getPrice() + "");
            parseFrame.add(priceField);

            parseFrame.add(new JLabel("LabWork name"));
            labWorkNameTextField = new JTextField();
            if (lw != null) labWorkNameTextField.setText(lw.getName());
            parseFrame.add(labWorkNameTextField);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("Coordinates fields"));

            parseFrame.add(new JLabel("    x<Float>"));
            coordinatesX = new JTextField();
            if (lw != null) coordinatesX.setText(lw.getCoordinates().getX() + "");
            parseFrame.add(coordinatesX);

            parseFrame.add(new JLabel("    y<int>"));
            coordinatesY = new JTextField();
            if (lw != null) coordinatesY.setText(lw.getCoordinates().getY() + "");
            parseFrame.add(coordinatesY);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel(""));

            parseFrame.add(new JLabel("minimal point<double>"));
            minPointField = new JTextField();
            if (lw != null) minPointField.setText(lw.getMinimalPoint() + "");
            parseFrame.add(minPointField);

            parseFrame.add(new JLabel("description"));
            descField = new JTextField();
            if (lw != null) descField.setText(lw.getDescription());
            parseFrame.add(descField);

            parseFrame.add(new JLabel("tuned in works<int>"));
            tunedInWorksField = new JTextField();
            if (lw != null) tunedInWorksField.setText(lw.getTunedInWorks() + "");
            parseFrame.add(tunedInWorksField);

            parseFrame.add(new JLabel("Difficulty(Only {VERY_HARD:1; INSANE:2; TERRIBLE:3} allowed)"));
            difficultyField = new JTextField();
            if (lw != null) difficultyField.setText(lw.getDifficulty().toString());
            parseFrame.add(difficultyField);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("Person fields"));

            parseFrame.add(new JLabel("    name"));
            personNameField = new JTextField();
            if (lw != null) personNameField.setText(lw.getAuthor().getName());
            parseFrame.add(personNameField);

            parseFrame.add(new JLabel("    passport ID (len>=9)"));
            personPassportField = new JTextField();
            if (lw != null) personPassportField.setText(lw.getAuthor().getPassportId());
            parseFrame.add(personPassportField);

            parseFrame.add(new JLabel("    eye color(Only {RED:1; BLUE:2; ORANGE:3; WHITE:4} allowed)"));
            personEyeColorField = new JTextField();
            if (lw != null) personEyeColorField.setText(lw.getAuthor().geteyeColor().toString());
            parseFrame.add(personEyeColorField);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("    Location fields"));

            parseFrame.add(new JLabel("        x<float>"));
            locationX = new JTextField();
            if (lw != null) locationX.setText(lw.getAuthor().getLocation().getX() + "");
            parseFrame.add(locationX);

            parseFrame.add(new JLabel("        y<float>"));
            locationY = new JTextField();
            if (lw != null) locationY.setText(lw.getAuthor().getLocation().getY() + "");
            parseFrame.add(locationY);

            parseFrame.add(new JLabel("        name"));
            locationNameField = new JTextField();
            if (lw != null) locationNameField.setText(lw.getAuthor().getLocation().getName());
            parseFrame.add(locationNameField);
            if (lw == null) {
                JButton parseButton = new JButton("convert to LabWork");
                parseButton.addActionListener(e -> {

                    LabWork lw=parseLabWorkFromTExtFields();
                    if (lw!=null) {
                        SendedCommand sendedCommand = new SendedCommand(commandName, false, "", true, lw);
                        Client_UDP_Transmitter.sendObject(sendedCommand);
                        Message message = (Message) Client_UDP_Transmitter.getObject();
                        JOptionPane.showMessageDialog(null, message.getMessage(), "register success", JOptionPane.INFORMATION_MESSAGE);
                        parseFrame.dispose();
                    }

                });
                parseFrame.add(parseButton);
                parseFrame.setVisible(true);
            }
        }
    }

    protected LabWork parseLabWorkFromTExtFields() {
        Double price = tryParseNumber(priceField.getText(), Double::parseDouble);
        String lwName = labWorkNameTextField.getText();
        Float cordsX = tryParseNumber(coordinatesX.getText(), Float::parseFloat);
        Integer cordsY = tryParseNumber(coordinatesY.getText(), Integer::parseInt);
        Double minimalPoint = tryParseNumber(minPointField.getText(), Double::parseDouble);
        String description = descField.getText();
        Integer tunedInWork = tryParseNumber(tunedInWorksField.getText(), Integer::parseInt);
        Difficulty difficulty = Difficulty.parse(difficultyField.getText());
        String personName = personNameField.getText();
        String personPassport = personPassportField.getText();
        org.lab6.storedClasses.Color personEyeColor = org.lab6.storedClasses.Color.parse(personEyeColorField.getText());
        Float locX = tryParseNumber(locationX.getText(), Float::parseFloat);
        Float locY = tryParseNumber(locationY.getText(), Float::parseFloat);
        String locName = locationNameField.getText();

        String errorMessage = "";
        if (price == null) errorMessage += "price must be a double value\n";
        if (cordsX == null) errorMessage += "Coordinates:x must be a float value\n";
        if (cordsY == null) errorMessage += "Coordinates:y must be an integer value\n";
        if (minimalPoint == null) errorMessage += "minimal point must be a double value\n";
        if (tunedInWork == null) errorMessage += "tuned in works must be an integer value\n";
        if (difficulty == null)
            errorMessage += "difficulty must be in {VERY_HARD:1; INSANE:2; TERRIBLE:3}\n";
        if (personPassport.length() < 9) errorMessage += "person passport ID length must be >=9\n";
        if (personEyeColor == null)
            errorMessage += "person eye color must be in {RED:1; BLUE:2; ORANGE:3; WHITE:4}\n";
        if (locX == null) errorMessage += "Person:Location:X must be a float value\n";
        if (locY == null) errorMessage += "Person:Location:Y must be a float value\n";
        if (errorMessage.equals("")) {
            Location location = new Location(locX, locY, locName);
            Person person = new Person(personName, personPassport, personEyeColor, location);
            Coordinates coordinates = new Coordinates(cordsX, cordsY);
            LabWork labWork = new LabWork(-1, -1, null, price, lwName, coordinates, "crDate", minimalPoint, description, tunedInWork, difficulty, person);
            return labWork;
        }else{
                JOptionPane.showMessageDialog(null, errorMessage, "parse fail", JOptionPane.INFORMATION_MESSAGE);
            }
        return null;
    }

    private static <T> T tryParseNumber(String value, Function<String, T> parser) {
        try {
            return parser.apply(value);
        } catch (NumberFormatException e) {

        }
        return null;
    }


}
