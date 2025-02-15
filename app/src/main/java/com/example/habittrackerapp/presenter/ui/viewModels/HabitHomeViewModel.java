package com.example.habittrackerapp.presenter.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.data.history.HistoryHabit;
import com.example.habittrackerapp.presenter.repo.HabitRepository;
import com.example.habittrackerapp.presenter.repo.HistoryRepository;

import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class HabitHomeViewModel extends ViewModel {
    private final HabitRepository habitRepository;
    private final HistoryRepository historyRepository;
    private final LiveData<List<Habit>> allHabits;

    @Inject
    public HabitHomeViewModel(HabitRepository habitRepository, HistoryRepository historyRepository) {
        this.habitRepository = habitRepository;
        this.historyRepository= historyRepository;
        this.allHabits = habitRepository.getAllHabits();
    }
    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }
    public  void delete(Habit habit) {
         habitRepository.deleteHabitById(habit);
    }

    public void addHabitToHistory(String name, String description, byte[] image , String data){
        HistoryHabit newHabit = new HistoryHabit(name, description, image, data);
        historyRepository.insert(newHabit);
    }
    public void insert(Habit habit) {
        habitRepository.resetHabit(habit);
    }



}
