package com.example.habittrackerapp.di;


import android.content.Context;

import androidx.room.Room;
import com.example.habittrackerapp.data.habit.HabitDao;
import com.example.habittrackerapp.data.habit.HabitDatabase;
import com.example.habittrackerapp.data.history.HistoryDao;
import com.example.habittrackerapp.data.history.HistoryDataBase;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static HistoryDataBase provideHistoryDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, HistoryDataBase.class, "history_habit_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static HistoryDao provideHistoryHabitDao(HistoryDataBase historyDataBase) {
        return historyDataBase.historyDao();
    }


    @Provides
    @Singleton
    public static HabitDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, HabitDatabase.class, "habit_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static HabitDao provideHabitDao(HabitDatabase database) {
        return database.habitDao();
    }
}
