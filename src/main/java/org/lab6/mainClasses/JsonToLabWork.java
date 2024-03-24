package org.lab6.mainClasses;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.lab6.storedClasses.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

@SuppressWarnings({"unchecked"})
public class JsonToLabWork {
	/**
	 * convets json file to ArrayList of LabWork objects
	 * @param way
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ArrayList<LabWork> getLabWork(String way) throws FileNotFoundException{
		//getting json file in String
		String jsonString;
		try {
			jsonString = readFile(way);
		}catch(FileNotFoundException ex){
			throw ex;
		}
		//parsing String to jsonObject
		JSONParser parser=new JSONParser();
		JSONObject jsonFile=new JSONObject();
		try{
			jsonFile=(JSONObject)(parser.parse(jsonString));
		}catch(ParseException e){
			throw new RuntimeException("can't currently parse current json file");
		}
		//converting json to labwork arraylist
		try{
			JSONArray arr = (JSONArray) jsonFile.get("LabWorks");
			Iterator i = arr.iterator();
			ArrayList<LabWork> labWorks=new ArrayList();
			while(i.hasNext()){
				JSONObject temp=(JSONObject)i.next();
				try {
					LabWork lw = convertJsonToLabWork(temp);
					labWorks.add(lw);
				}catch(NullPointerException e){
					System.out.println("This LabWork instance has incorrect structure:\n"+temp.toString().replaceAll(",", "\n"));
				}
			}
			return labWorks;
		}catch(NullPointerException e){
			Person.clearPassportBase();
			throw new NullPointerException("incorrect file structure, can't convert it to LabWork instance");
		}

	}

	/**
	 * Converting jsonObject to LabWork
	 * @param lwJSON
	 * @return
	 */
	private static LabWork convertJsonToLabWork(JSONObject lwJSON){
		JSONObject temp=(JSONObject)(lwJSON.get("Coordinates"));
		//creating coordinates
		float x=((float)(double)(temp.get("x")));
		int y=((int)(long)(temp.get("y")));
		Coordinates coordinates = new Coordinates(x, y);
		//creating person
		temp=(JSONObject)(lwJSON.get("Person"));
		String name=(String)(temp.get("name"));
		String passportID=(String)(temp.get("passportID"));
		String tmpColor=(String)(temp.get("eyeColor"));
		Color eyeColor=Color.parse(tmpColor);
		temp=(JSONObject)(temp.get("Location"));
		Location location=new Location((float)(double)(temp.get("x")), (float)(double)(temp.get("y")), (String)(temp.get("name")));
		Person person=new Person(name, passportID, eyeColor, location);
		//creating LabWork
		name=(String)(lwJSON.get("name"));
		float minimalPoint=(float)(double)(lwJSON.get("minimalPoint"));
		String description=(String)(lwJSON.get("description"));
		int tunedInWorks=(int)(long)(lwJSON.get("tunedInWorks"));
		String difTemp=(String)(lwJSON.get("Difficulty"));
		Difficulty difficulty=Difficulty.parse(difTemp);
		LabWork labWork=new LabWork(name, coordinates, minimalPoint, description, tunedInWorks, difficulty, person);
		return labWork;
		
		
		
		
	}

	/**
	 *
	 * @param way
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String readFile(String way) throws FileNotFoundException{
		try {
            File file = new File(way);

            Scanner input = new Scanner(file);
			
			String jsonFile="";

            while (input.hasNextLine()) {
                String line = input.nextLine();
                jsonFile+=line+"\n";
            }
            input.close();
			
			return jsonFile;
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("can't access current path");
        }
	}
}