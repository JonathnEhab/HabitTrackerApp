package com.example.habittrackerapp.data.habit;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitDao {

    @Insert
    void insert(Habit habit);

    @Update
    void update(Habit habit);

    @Query("SELECT * FROM habits ORDER BY habit_name ASC")
    LiveData<List<Habit>> getAllHabits();

    @Query("SELECT * FROM habits WHERE id = :habitId LIMIT 1")
    LiveData<Habit> getHabitById(int habitId);


    @Delete
    void deleteHabit(Habit habit);

    @Insert
    void reSet(Habit habit);




}
