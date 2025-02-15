package com.example.habittrackerapp.presenter.ui.viewModels;

import androidx.lifecycle.ViewModel;
import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.presenter.repo.HabitRepository;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewHabitViewModel extends ViewModel {
    private final HabitRepository habitRepository;

    @Inject
    public NewHabitViewModel(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public void addNewHabit(String name, String description, byte[] image ) {
        Habit newHabit = new Habit(name, description, image);
        habitRepository.insert(newHabit);
    }

}
