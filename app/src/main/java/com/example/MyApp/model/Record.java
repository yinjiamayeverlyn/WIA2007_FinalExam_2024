package com.example.MyApp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Record {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int mathStars; // Field to represent the number of stars obtained in Mathematics
    private int engStars;
    // Constructor
    public Record(int mathStars, int engStars) {
        this.mathStars = mathStars;
        this.engStars = engStars;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter method for mathStars
    public int getMathStars() {
        return mathStars;
    }

    // Getter method for engStars
    public int getEngStars() {
        return engStars;
    }

    // Setter methods (if needed)
    public void setMathStars(int mathStars) {
        this.mathStars = mathStars;
    }

    public void setEngStars(int engStars) {
        this.engStars = engStars;
    }
}
