package com.example.habittrackerapp.di;

import androidx.lifecycle.LiveData;
import com.example.habittrackerapp.data.habit.HabitDao;
import com.example.habittrackerapp.data.history.HistoryDao;
import com.example.habittrackerapp.data.history.HistoryHabit;
import com.example.habittrackerapp.presenter.repo.HabitRepository;
import com.example.habittrackerapp.presenter.repo.HistoryRepository;

import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {
    @Provides
    @Singleton
    public static HabitRepository provideHabitRepository(HabitDao habitDao) {
        return new HabitRepository(habitDao);
    }

    @Provides
    @Singleton
    public static HistoryRepository provideHistoryHabitRepository(HistoryDao historyDao) {
        return new HistoryRepository(historyDao);

    }

    @Provides
    @Singleton
    public static LiveData<List<HistoryHabit>> provideHistoryHabits(HistoryRepository historyRepository) {
        return historyRepository.getAllHistory();
    }


    @Provides
    @Singleton
    public static LiveData<List<String>> provideHistoryDates(HistoryRepository historyRepository) {
        return historyRepository.getAllHistoryDates();
    }
}
