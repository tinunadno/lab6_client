package org.lab6.storedClasses;

import java.io.Serializable;

public class Person implements Serializable {
	private static String[] passportsIds={};
	private String name;
	private String passportID;
	private Color eyeColor;
	private Location location;

	public Person(String name, String passportID, Color eyeColor, Location location){
		if(name==null || name.equals(""))throw new NullPointerException("name can't be null");
		if(passportID.length()>36 || passportID.length()<9)throw new WrongValueException("passportID length mnust be >=9 and <36");
		if(!chekckPassport(passportID)){
			addPassport(passportID);
			this.name=name;
			this.passportID=passportID;
			this.eyeColor=eyeColor;
			this.location=location;
		}
		else throw new UniqeValueCollisionException("Person with this passport already exists");
	}

	private static boolean chekckPassport(String passport){
		for(String pasp: passportsIds){
			if(pasp.equals(passport))return true;
		}
		return false;
	}

	private static void addPassport(String pasp){
		String[] temp=new String[passportsIds.length+1];
		for(int i=0;i<passportsIds.length;i++)
			temp[i]=passportsIds[i];
		temp[passportsIds.length]=pasp;
		passportsIds=temp;
	}

	public static void clearPassportBase(){
		String[] tmp={};
		passportsIds=tmp;
	}

	public void setLocation(Location location){this.location=location;}

	public static String getPassportIds(){
		String output="[";
		for(String pasp:passportsIds)
			output+=(pasp+" ");
		return output+"]";
	}
	public String getName(){return name;}
	public String getPassportId(){return passportID;}
	public Color geteyeColor(){return eyeColor;}
	public Location getLocation(){return location;}

	public String getFieldsAsTuple() {
		return "('"+name+"','"+passportID+"','"+eyeColor.toString()+"',%1)";
	}
	public String toString(){return "[name:"+name+", \n\tpassportID:"+passportID+", \n\teyeColor:"+eyeColor.toString()+", \n\tlocation:"+location.toString()+"]";}
	public String toJson(){
		return "\"Person\": {\n\t\"name\": \""+getName()+"\",\n\t\"passportID\": \""+getPassportId()+"\",\n\t\"eyeColor\": \""+geteyeColor()+"\",\n\t"+location.toJson().replace("\t", "\t\t")+"\n}";
	}
}