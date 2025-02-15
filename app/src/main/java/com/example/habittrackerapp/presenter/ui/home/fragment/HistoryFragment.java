package com.example.habittrackerapp.presenter.ui.home.fragment;

import static com.example.habittrackerapp.util.Utils.showToast;
import static com.example.habittrackerapp.util.Utils.vibrate;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habittrackerapp.R;
import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.data.history.HistoryHabit;
import com.example.habittrackerapp.databinding.FragmentHistoryBinding;
import com.example.habittrackerapp.presenter.ui.adapter.HistoryAdapter;
import com.example.habittrackerapp.presenter.ui.viewModels.HabitHomeViewModel;
import com.example.habittrackerapp.presenter.ui.viewModels.HistoryHabitViewModel;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.color.MaterialColors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private HistoryHabitViewModel historyHabitViewModel;
    private HistoryAdapter historyAdapter;
    HabitHomeViewModel habitHomeViewModel ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyHabitViewModel = new ViewModelProvider(this).get(HistoryHabitViewModel.class);
        habitHomeViewModel=  new ViewModelProvider(this).get(HabitHomeViewModel.class);
        historyHabitViewModel.getAllHabits().observe(getViewLifecycleOwner(), historyHabits -> {
            if (historyHabits != null) {
                historyAdapter.setData(historyHabits);
            }
        });
        historyHabitViewModel.getAllDates().observe(getViewLifecycleOwner(), dates -> {
            if (dates != null) {

                Map<String, Integer> frequencyMap = new LinkedHashMap<>();

                for (String date : dates) {
                    frequencyMap.put(date, frequencyMap.getOrDefault(date, 0) + 1);
                }
                String todayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                if (!frequencyMap.containsKey(todayDate)) {
                    frequencyMap.put(todayDate, 0);
                }
                List<String> values = new ArrayList<>(frequencyMap.keySet());
                List<Integer> yAxisValues = new ArrayList<>(frequencyMap.values());

                Log.d("HistoryDates", "Dates: " + values);
                Log.d("ChartData", "Frequencies: " + yAxisValues);

                setupChart(values, yAxisValues);
            } else {
                Log.e("HistoryFragment", "Received null dates from ViewModel!");
            }
        });
        binding.resetHabits.setOnClickListener(v -> {resetHabits();});
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        historyAdapter = new HistoryAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.historyRecycler.setLayoutManager(layoutManager);
        binding.historyRecycler.setAdapter(historyAdapter);
    }

    private void setupChart(List<String> values, List<Integer> yAxisValues) {
        int textColor = MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorOnSurface);

        Description description = new Description();
        description.setPosition(200f, 20f);
        description.setText("عدد انجازاتك يا وحش");
        description.setTextColor(textColor);
        binding.lineChart.setDescription(description);
        binding.lineChart.getAxisRight().setDrawLabels(false);

        XAxis xAxis = binding.lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(values));
        xAxis.setLabelCount(values.size());
        xAxis.setGranularity(1f);
        xAxis.setTextSize(8);
        xAxis.setTextColor(textColor);
        xAxis.setAxisLineColor(Color.GREEN);

        YAxis yAxis = binding.lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setTextSize(8);
        yAxis.setAxisMaximum(Collections.max(yAxisValues) + 1);
        yAxis.setLabelCount(5);
        yAxis.setTextColor(textColor);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(-1, 0));

        for (int i = 0; i < values.size(); i++) {
            entries.add(new Entry(i, yAxisValues.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "انجازاتي اليوميه");
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setValueTextSize(8);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setValueTextColors(Collections.singletonList(textColor));
        lineDataSet.setFillAlpha(150);
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setColor(Color.GREEN);

        LineData lineData = new LineData(lineDataSet);
        binding.lineChart.setData(lineData);
        binding.lineChart.invalidate();
    }



    private void resetHabits() {
        historyHabitViewModel.getAllHabits().observe(getViewLifecycleOwner(), historyHabits -> {
            if (historyHabits != null) {
                habitHomeViewModel.getAllHabits().observe(getViewLifecycleOwner(), existingHabits -> {
                    insertUniqueHabits(historyHabits, existingHabits);
                });
            }
        });
    }
    private void insertUniqueHabits(List<HistoryHabit> historyHabits, List<Habit> existingHabits) {

        Set<String> existingHabitNames = new HashSet<>();
        if (existingHabits != null) {
            for (Habit habit : existingHabits) {
                existingHabitNames.add(habit.getName());
            }
        }
        Set<String> uniqueHistoryHabits = new HashSet<>();
        for (HistoryHabit historyHabit : historyHabits) {
            if (!existingHabitNames.contains(historyHabit.getName()) &&
                    !uniqueHistoryHabits.contains(historyHabit.getName())) {

                uniqueHistoryHabits.add(historyHabit.getName());

                Habit newHabit = new Habit(
                        historyHabit.getName(),
                        historyHabit.getDescription(),
                        historyHabit.getImage()
                );

                habitHomeViewModel.insert(newHabit);
            }
        }
        vibrate(requireActivity(),100);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

