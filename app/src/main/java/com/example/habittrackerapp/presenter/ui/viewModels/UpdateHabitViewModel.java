package com.example.habittrackerapp.presenter.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.presenter.repo.HabitRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UpdateHabitViewModel  extends ViewModel {
    private final HabitRepository habitRepository;
    @Inject
    public UpdateHabitViewModel(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }
    public LiveData<Habit> getHabitById(int id) {
        return habitRepository.getHabitById(id);
    }
    public void updateHabit(Habit habit) {
        habitRepository.update(habit);
    }
}
