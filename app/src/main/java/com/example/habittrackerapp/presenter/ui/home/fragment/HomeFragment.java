package com.example.habittrackerapp.presenter.ui.home.fragment;


import static com.example.habittrackerapp.util.Utils.vibrate;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.databinding.FragmentHomeBinding;
import com.example.habittrackerapp.presenter.ui.adapter.HabitAdapter;
import com.example.habittrackerapp.presenter.ui.viewModels.HabitHomeViewModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    HabitHomeViewModel habitViewModel;
    private HabitAdapter habitAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();

        data();
        binding.goToHistory.setOnClickListener(v -> navToHistoryHabit());
        binding.addNewHabitBtn.setOnClickListener(v -> navToNewHabit());
        binding.addNewHabitView.setOnClickListener(v -> navToNewHabit());
        habitViewModel = new ViewModelProvider(this).get(HabitHomeViewModel.class);
        habitViewModel.getAllHabits().observe(getViewLifecycleOwner(), habits -> {
            if (habits != null && !habits.isEmpty()) {
                showHabitList(habits);
            } else {
                showEmptyView();
            }
        });
    }

    private void navToNewHabit() {
        Navigation.findNavController(requireView())
                .navigate(HomeFragmentDirections.actionHomeFragmentToNewHabitFragment());
    }
    private void navToHistoryHabit() {
        Navigation.findNavController(requireView())
                .navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment());
    }

    private void setupRecyclerView() {
        habitAdapter = new HabitAdapter(this::completeHabit);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(habitAdapter);

    }

    private void showHabitList(List<Habit> habits) {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.addNewHabitView.setVisibility(View.GONE);
        habitAdapter.submitList(habits);
    }
    private void showEmptyView() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.addNewHabitView.setVisibility(View.VISIBLE);
    }
    private void data(){
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
        binding.dataThisDay.setText(currentDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
    private void completeHabit(Habit habit) {
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        habitViewModel.addHabitToHistory(
                habit.getName(),
                habit.getDescription(),
                habit.getImage(),
                today
        );

        habitViewModel.delete(habit);
        vibrate(requireActivity(),20);

    }




}