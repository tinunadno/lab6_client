package org.lab6.mainClasses;

import org.lab6.storedClasses.*;

import java.util.Scanner;
import java.util.function.Function;

public class LabWorkParser {
    private static Scanner in;
    private static boolean isFromFile=false;

    /**
     * if commands are in file, console scanner changing to file scanner, created in command class
     * @param scan
     */
    public static void setScanner(Scanner scan){
        in=scan;
        isFromFile=true;
    }

    /**
     * parsing LabWork object from console one field at time
     * @return
     */
    public static LabWork parseLabWorkFromConsole(){
        try{
            if(in==null)in=new Scanner(System.in);

            System.out.println("insert LabWork class fields");
            System.out.print("name<String>:");
            String name=in.nextLine();

            System.out.println("Coordinates:");
            float x=tryParseNumber("\tx<float>:", Float::parseFloat);
            int y=tryParseNumber("\ty<int>:", Integer::parseInt);
            Coordinates coordinates=new Coordinates(x, y);

            float minimalPoint=tryParseNumber("minimalPoint<float>:", Float::parseFloat);

            System.out.print("description<String>:");
            String description=(in.nextLine());

            int tunedInWorks=tryParseNumber("tunedInWorks<int>:", Integer::parseInt);

            System.out.print("Difficulty(Only {VERY_HARD; INSANE; TERRIBLE} allowed):");
            Difficulty difficulty=tryparseEnum(Difficulty.class);

            System.out.println("Person:");
            System.out.print("\tname<String>:");
            String personName=(in.nextLine());
            System.out.print("\tpassportId<String>:");
            String passportId=(in.nextLine());
            while(passportId.length()<9){
                System.out.print("passport id length must be >= 9\n\tpassportId<String>:");
                passportId=(in.nextLine());
            }
            System.out.print("\teyeColor(Only {RED; BLUE; ORANGE; WHITE} allowed):");
            Color eyeColor=tryparseEnum(Color.class);
            System.out.println("\tLocation:");
            float locationX=tryParseNumber("\t\tx<float>:", Float::parseFloat);
            float locationY=tryParseNumber("\t\ty<float>:", Float::parseFloat);
            System.out.print("\t\tname<String>:");
            String locationName=(in.nextLine());
            Location location=new Location(locationX, locationY, locationName);
            Person person = new Person(personName, passportId, eyeColor, location);

            LabWork labwork=new LabWork(-1,-1,name, coordinates,"crDate", minimalPoint, description, tunedInWorks, difficulty, person);
            System.out.println();
            isFromFile=false;
            if(isFromFile){
                in=null;
            }
            return labwork;
        }catch(NumberFormatException e){System.out.println("oops, you made a mistake, try again"); }
        return null;
    }

    /**
     * reading value from console, until it gets float
     * @param msg
     * @return
     */
    private static<T> T tryParseNumber(String msg, Function<String, T> parser){
        boolean flag=false;
        while(!flag){
            System.out.print(msg);
            try {
                return parser.apply(in.nextLine());
            }catch(NumberFormatException e){
                System.out.println("bad Number format");
                if(isFromFile)
                    flag=true;
            }
        }
        return parser.apply("0");
    }

    /**
     * reading value from console, until it gets Enum name
     * @param enumClass
     * @return
     * @param <T>
     */
    static <T extends Enum<T>> T tryparseEnum(Class<T> enumClass){
        if(isFromFile)return parseEnum(enumClass, "1");
        while(true){
            T parsedEnum=parseEnum(enumClass, in.nextLine());
            if(parsedEnum==null)
                System.out.println("bad enum format");
            else return parsedEnum;
        }
    }

    /**
     * parsing enum
     * @param enumClass
     * @param col
     * @return
     * @param <T>
     */
    static <T extends Enum<T>> T parseEnum(Class<T> enumClass, String col) {
        int k=0;
        for (T constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(col) || constant.name().equals(col) || col.equals((++k+""))) {
                return constant;
            }
        }
        return null;
    }
}
