package org.lab6.mainClasses.CommandInteracting;

import org.lab6.mainClasses.UDPInteraction.Client_UDP_Transmitter;
import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.storedClasses.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class LabWorkParser {
    private LabWork lw;
    public void parseLabWork(String commandName, String argument){
        UserInterfaceForm.setWait(false);
            JFrame parseFrame = new JFrame("LabWork parse");
            parseFrame.setLayout(new GridLayout(20, 2));

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("LabWork fields"));

            parseFrame.add(new JLabel("price<double>"));
            JTextField priceField = new JTextField();
            parseFrame.add(priceField);

            parseFrame.add(new JLabel("LabWork name"));
            JTextField labWorkNameTextField = new JTextField();
            parseFrame.add(labWorkNameTextField);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("Coordinates fields"));

            parseFrame.add(new JLabel("    x<Float>"));
            JTextField coordinatesX = new JTextField();
            parseFrame.add(coordinatesX);

            parseFrame.add(new JLabel("    y<int>"));
            JTextField coordinatesY = new JTextField();
            parseFrame.add(coordinatesY);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel(""));

            parseFrame.add(new JLabel("minimal point<double>"));
            JTextField minPointField = new JTextField();
            parseFrame.add(minPointField);

            parseFrame.add(new JLabel("description"));
            JTextField descField = new JTextField();
            parseFrame.add(descField);

            parseFrame.add(new JLabel("tuned in works<int>"));
            JTextField tunedInWorksField = new JTextField();
            parseFrame.add(tunedInWorksField);

            parseFrame.add(new JLabel("Difficulty(Only {VERY_HARD:1; INSANE:2; TERRIBLE:3} allowed)"));
            JTextField difficultyField = new JTextField();
            parseFrame.add(difficultyField);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("Person fields"));

            parseFrame.add(new JLabel("    name"));
            JTextField personNameField = new JTextField();
            parseFrame.add(personNameField);

            parseFrame.add(new JLabel("    passport ID (len>=9)"));
            JTextField personPassportField = new JTextField();
            parseFrame.add(personPassportField);

            parseFrame.add(new JLabel("    eye color(Only {RED:1; BLUE:2; ORANGE:3; WHITE:4} allowed)"));
            JTextField personEyeColorField = new JTextField();
            parseFrame.add(personEyeColorField);

            parseFrame.add(new JLabel(""));
            parseFrame.add(new JLabel("    Location fields"));

            parseFrame.add(new JLabel("        x<float>"));
            JTextField locationX = new JTextField();
            parseFrame.add(locationX);

            parseFrame.add(new JLabel("        y<float>"));
            JTextField locationY = new JTextField();
            parseFrame.add(locationY);

            parseFrame.add(new JLabel("        name"));
            JTextField locationNameField = new JTextField();
            parseFrame.add(locationNameField);

            JButton parseButton = new JButton("convert to LabWork");
            parseButton.addActionListener(e -> {
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
                if (difficulty == null) errorMessage += "difficulty must be in {VERY_HARD:1; INSANE:2; TERRIBLE:3}\n";
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
                    lw = labWork;
                    System.out.println(lw);
                    if(argument==null){
                        SendedCommand sendedCommand = new SendedCommand(commandName, false, "", true, lw);
                        System.out.println(sendedCommand);
                        Client_UDP_Transmitter.sendObject(sendedCommand);
                        Message message=(Message) Client_UDP_Transmitter.getObject();
                        JOptionPane.showMessageDialog(null, message.getMessage(), "register success", JOptionPane.INFORMATION_MESSAGE);

                    }else{
                        SendedCommand sendedCommand = new SendedCommand(commandName, true, argument, true, lw);
                        Client_UDP_Transmitter.sendObject(sendedCommand);
                        Message message=(Message) Client_UDP_Transmitter.getObject();
                        JOptionPane.showMessageDialog(null, message.getMessage(), "register success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    parseFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, errorMessage, "parse fail", JOptionPane.INFORMATION_MESSAGE);
                }

            });
            parseFrame.add(parseButton);

            parseFrame.setVisible(true);
    }

    private static<T> T tryParseNumber(String value, Function<String, T> parser){
            try {
                return parser.apply(value);
            }catch(NumberFormatException e){

            }
        return null;
    }


}
