package org.lab6.storedClasses;

import java.io.Serial;
import java.io.Serializable;

public class LabWork implements Comparable<LabWork>, Serializable {
    private static long[] ids = {0};
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private double minimalPoint;
    private String description;
    private Integer tunedInWorks;
    private Difficulty difficulty;
    private Person author;

    public LabWork(String name, Coordinates coordinates, double minimalPoint, String description, Integer tunedInWorks, Difficulty difficulty, Person author) {
        if (name.equals(null) || name.equals("")) throw new NullPointerException("name cant be null");
        if (coordinates == null) throw new NullPointerException("Coordinates cant be null");
        if (description.equals(null)) throw new NullPointerException("Description cant be null");
        if (tunedInWorks == null) throw new NullPointerException("tunedInWorks cant be null");
        if (difficulty == null) throw new NullPointerException("difficulty cant be null");
        if (minimalPoint <= 0.0) throw new WrongValueException("minimalPoint must be > 0");
        if (!checkID(id)) throw new UniqeValueCollisionException("LabWork with same id already exists");
        else {
            this.name = name;
            this.coordinates = coordinates;
            this.minimalPoint = minimalPoint;
            this.description = description;
            this.tunedInWorks = tunedInWorks;
            this.difficulty = difficulty;
            this.author = author;
            this.id = ids.length;
            addId(id);
            creationDate = java.time.LocalDate.now();
        }
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public java.time.LocalDate getCreationDate() {
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

    private static boolean checkID(long ID) {
        for (long value : ids) {
            if (value == ID) return true;
        }
        return false;
    }

    private static void addId(long ID) {
        long[] temp = new long[ids.length + 1];
        for (int i = 0; i < ids.length; i++)
            temp[i] = ids[i];
        temp[ids.length] = ID;
        ids = temp;
    }

    @Override
    public int compareTo(LabWork lw){
        return (int) (this.getMinimalPoint()-lw.getMinimalPoint());
    }

    public String toString() {
        return "[id:" + id + ", name:" + name + ",\ncoordinates:" + coordinates.toString() + ", \ncreationDate:" + creationDate.toString() + ", \nminimalPoint:" + minimalPoint + ", \ndescription:" + description + ", \ntunedInWorks:" + tunedInWorks + ", \ndifficulty:" + difficulty + ", \nauthor:" + author.toString() + "]";
    }

    public String toJson() {
        return "{\n\"name\": \"" + getName() + "\",\n" + coordinates.toJson() + ",\n\"minimalPoint\": " + getMinimalPoint() + ",\n\"description\": \"" + getDescription() + "\",\n\"tunedInWorks\": " + getTunedInWorks() + ",\n\"Difficulty\": \"" + getDifficulty() + "\",\n" + getAuthor().toJson() + "\n}";
    }
}