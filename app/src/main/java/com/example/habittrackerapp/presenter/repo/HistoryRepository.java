package com.example.habittrackerapp.presenter.repo;


import androidx.lifecycle.LiveData;

import com.example.habittrackerapp.data.history.HistoryDao;
import com.example.habittrackerapp.data.history.HistoryHabit;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HistoryRepository {
    private final HistoryDao historyDao;
    private final ExecutorService executorService;

    @Inject
    public HistoryRepository(HistoryDao historyDao) {
        this.historyDao = historyDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<HistoryHabit>> getAllHistory() {
        return historyDao.getAllHabits();
    }

    public void insert(HistoryHabit historyHabit) {executorService.execute(() -> historyDao.insert(historyHabit));}


    public LiveData<List<String>> getAllHistoryDates() {return historyDao.getAllHistoryDates();}

}


