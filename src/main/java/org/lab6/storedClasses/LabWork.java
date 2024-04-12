package org.lab6.storedClasses;

import java.io.Serializable;

public class LabWork implements Comparable<LabWork>, Serializable {
    private static long[] ids = {0};
    private long id;
    private int userID;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private double minimalPoint;
    private String description;
    private Integer tunedInWorks;
    private Difficulty difficulty;
    private Person author;

    public LabWork(int id,int userID,String name, Coordinates coordinates, String creationDate, double minimalPoint, String description, Integer tunedInWorks, Difficulty difficulty, Person author) {
        if (name.equals(null) || name.equals("")) throw new NullPointerException("name cant be null");
        if (coordinates == null) throw new NullPointerException("Coordinates cant be null");
        if (description.equals(null)) throw new NullPointerException("Description cant be null");
        if (tunedInWorks == null) throw new NullPointerException("tunedInWorks cant be null");
        if (difficulty == null) throw new NullPointerException("difficulty cant be null");
        if (minimalPoint <= 0.0) throw new WrongValueException("minimalPoint must be > 0");
        else {
            this.id=id;
            this.userID=userID;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate=creationDate;
            this.minimalPoint = minimalPoint;
            this.description = description;
            this.tunedInWorks = tunedInWorks;
            this.difficulty = difficulty;
            this.author = author;
            this.id = ids.length;
        }
    }

    public void setID(int id){this.id=id;}
    public void setUserID(int userID){this.userID=userID;}
    public void setCreationDate(String creationDate){this.creationDate=creationDate;}
    public long getID() {
        return id;
    }
    public int getUserID(){return userID;}

    public String getName() {
        return name;
    }

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
        return "[id:" + id + ", name:" + name + ",\ncoordinates:" + coordinates.toString() + ", \ncreationDate:" + creationDate.toString() + ", \nminimalPoint:" + minimalPoint + ", \ndescription:" + description + ", \ntunedInWorks:" + tunedInWorks + ", \ndifficulty:" + difficulty + ", \nauthor:" + author.toString() + "]";
    }

    public String getFieldsAsTuple(){
        return"("+userID+",'"+name+"',%1,"+minimalPoint+",'"+description+"',"+tunedInWorks+",'"+difficulty.toString()+"',%2)";
    }

    public String toJson() {
        return "{\n\"name\": \"" + getName() + "\",\n" + coordinates.toJson() + ",\n\"minimalPoint\": " + getMinimalPoint() + ",\n\"description\": \"" + getDescription() + "\",\n\"tunedInWorks\": " + getTunedInWorks() + ",\n\"Difficulty\": \"" + getDifficulty() + "\",\n" + getAuthor().toJson() + "\n}";
    }
}