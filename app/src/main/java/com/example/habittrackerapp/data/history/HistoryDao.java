package com.example.habittrackerapp.data.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(HistoryHabit historyHabit);
    @Query("SELECT * FROM history_habit ORDER BY habit_name ASC")
    LiveData<List<HistoryHabit>> getAllHabits();
    @Query("SELECT habit_date FROM history_habit ORDER BY habit_date ASC")
    LiveData<List<String>> getAllHistoryDates();


}
