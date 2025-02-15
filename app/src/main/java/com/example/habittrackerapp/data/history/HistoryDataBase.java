package com.example.habittrackerapp.data.history;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryHabit.class}, version = 3, exportSchema = false)
public abstract  class HistoryDataBase  extends RoomDatabase {
    public abstract HistoryDao historyDao();
}
