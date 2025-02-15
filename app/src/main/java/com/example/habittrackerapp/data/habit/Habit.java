package com.example.habittrackerapp.data.habit;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habits")
public class Habit {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "habit_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "habit_description")
    private String description;

    @ColumnInfo(name = "habit_image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(name = "habit_date")
    private String date;


    public Habit(@NonNull String name, @NonNull String description, byte[] image) {
        this.name = name;
        this.description = description;
        this.image = image;

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getName() { return name; }
    public void setName(@NonNull String name) { this.name = name; }

    @NonNull
    public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }









}
