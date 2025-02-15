package com.example.habittrackerapp.presenter.ui.viewModels;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.habittrackerapp.data.history.HistoryHabit;
import com.example.habittrackerapp.presenter.repo.HistoryRepository;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HistoryHabitViewModel extends ViewModel {
    private final HistoryRepository historyRepository;
    private final LiveData<List<HistoryHabit>> allHabits;

    @Inject
    public HistoryHabitViewModel(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
        this.allHabits = historyRepository.getAllHistory();
    }

    public LiveData<List<HistoryHabit>> getAllHabits() {
        return allHabits;
    }


    public LiveData<List<String>> getAllDates() {
        return historyRepository.getAllHistoryDates();
    }

}

