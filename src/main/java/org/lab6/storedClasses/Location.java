package org.lab6.storedClasses;

import java.io.Serializable;

public class Location implements Serializable {
	private Float x;
    private float y;
    private String name;
	@SuppressWarnings({"removal"})
	public Location(float x, float y, String name){
		this.x=new Float(x);
		this.y=y;
		this.name=name;
		
		if(this.x==null)
			throw new NullPointerException("x can't be null");
	}
	
	public float getX(){return x;}
	public float getY(){return y;}
	public String getName(){return name;}
	
	public String toString(){return "[x:"+x.toString()+", y:"+y+", name:"+name+"]";}
	public String toJson(){
		return "\"Location\": {\n\"x\": "+getX()+",\n\"y\": "+getY()+",\n\"name\": \""+getName()+"\"\n}";
	}
}