package com.example.habittrackerapp.data.history;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_habit")
public class HistoryHabit {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "habit_name")
    private String name;

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @NonNull
    @ColumnInfo(name = "habit_description")
    private String description;

    @ColumnInfo(name = "habit_date")
    private String date;

    @ColumnInfo(name = "habit_image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;
    @ColumnInfo(name = "habit_value", defaultValue = "0")
    private int value;
    public HistoryHabit() {}
    public HistoryHabit(@NonNull String name, @NonNull String description, byte[] image,String date) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.date=date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public byte[] getImage() { return image; }

}
