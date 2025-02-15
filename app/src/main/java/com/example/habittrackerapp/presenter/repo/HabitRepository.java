package com.example.habittrackerapp.presenter.repo;

import androidx.lifecycle.LiveData;

import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.data.habit.HabitDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HabitRepository {
    private final HabitDao habitDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Inject
    public HabitRepository(HabitDao habitDao) {this.habitDao = habitDao;}
    public void insert(Habit habit) {executorService.execute(() -> habitDao.insert(habit));}

    public void update(Habit habit) {executorService.execute(() -> habitDao.update(habit));}

    public LiveData<List<Habit>> getAllHabits() {
        return habitDao.getAllHabits();
    }

    public LiveData<Habit> getHabitById(int id) {
        return habitDao.getHabitById(id);
    }

    public void deleteHabitById(Habit habit){ executorService.execute(() -> habitDao.deleteHabit(habit));}
    public void resetHabit(Habit habit) {executorService.execute(() -> habitDao.reSet(habit));}
}
