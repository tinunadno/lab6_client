package org.lab6.storedClasses;

import java.io.Serializable;

public class LabWork implements Comparable<LabWork>, Serializable {
    private long id;
    private int userID;
    private String userName;
    private double price;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private double minimalPoint;
    private String description;
    private Integer tunedInWorks;
    private Difficulty difficulty;
    private Person author;

    public LabWork(int id,int userID, String userName,double price,String name, Coordinates coordinates, String creationDate, double minimalPoint, String description, Integer tunedInWorks, Difficulty difficulty, Person author) {
        if (name.equals(null) || name.equals("")) throw new NullPointerException("name cant be null");
        if (coordinates == null) throw new NullPointerException("Coordinates cant be null");
        if (description.equals(null)) throw new NullPointerException("Description cant be null");
        if (tunedInWorks == null) throw new NullPointerException("tunedInWorks cant be null");
        if (difficulty == null) throw new NullPointerException("difficulty cant be null");
        if (minimalPoint <= 0.0) throw new WrongValueException("minimalPoint must be > 0");
        else {
            this.id=id;
            this.userID=userID;
            this.userName=userName;
            this.price=price;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate=creationDate;
            this.minimalPoint = minimalPoint;
            this.description = description;
            this.tunedInWorks = tunedInWorks;
            this.difficulty = difficulty;
            this.author = author;
        }
    }

    public void setID(int id){this.id=id;}
    public void setUserName(String name){
        this.userName=name;
    }
    public void setUserID(int userID){this.userID=userID;}
    public void setCreationDate(String creationDate){this.creationDate=creationDate;}
    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserID(){return userID;}
    public double getPrice(){return price;}

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public double getMinimalPoint() {
        return minimalPoint;
    }

    public String getDescription() {
        return description;
    }

    public int getTunedInWorks() {
        return (int) tunedInWorks;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Person getAuthor() {
        return author;
    }



    @Override
    public int compareTo(LabWork lw){
        return (int) (this.getMinimalPoint()-lw.getMinimalPoint());
    }

    public String toString() {
        return "[id:" + id + ",\n userID: "+userID+"\n userName: "+userName+"\n price: "+price+",\n name:" + name + ",\ncoordinates:" + coordinates.toString() + ", \ncreationDate:" + creationDate.toString() + ", \nminimalPoint:" + minimalPoint + ", \ndescription:" + description + ", \ntunedInWorks:" + tunedInWorks + ", \ndifficulty:" + difficulty + ", \nauthor:" + author.toString() + "]";
    }

    public String getFieldsAsTuple(){
        return"("+userID+",'"+userName+"',"+price+",'"+name+"',%1,"+minimalPoint+",'"+description+"',"+tunedInWorks+",'"+difficulty.toString()+"',%2)";
    }

    public String toJson() {
        return "{\n\"name\": \"" + getName() + "\",\n" + coordinates.toJson() + ",\n\"minimalPoint\": " + getMinimalPoint() + ",\n\"description\": \"" + getDescription() + "\",\n\"tunedInWorks\": " + getTunedInWorks() + ",\n\"Difficulty\": \"" + getDifficulty() + "\",\n" + getAuthor().toJson() + "\n}";
    }
}