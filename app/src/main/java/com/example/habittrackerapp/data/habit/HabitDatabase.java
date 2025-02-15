package com.example.habittrackerapp.data.habit;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Habit.class}, version = 2, exportSchema = false)
public abstract class HabitDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
}
