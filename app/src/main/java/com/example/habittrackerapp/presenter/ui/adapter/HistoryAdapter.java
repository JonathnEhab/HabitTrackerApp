package com.example.habittrackerapp.presenter.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.habittrackerapp.R;
import com.example.habittrackerapp.data.history.HistoryHabit;
import com.example.habittrackerapp.databinding.ItemDataBinding;
import com.example.habittrackerapp.databinding.ItemHistoryBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DATE = 0;
    private static final int TYPE_HISTORY = 1;

    private final List<Object> items = new ArrayList<>();

    public HistoryAdapter(List<HistoryHabit> historyList) {
        setData(historyList);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof String) {
            return TYPE_DATE;
        } else {
            return TYPE_HISTORY;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATE) {
            ItemDataBinding binding = ItemDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new DateViewHolder(binding);
        } else {
            ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HistoryViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).bind((String) items.get(position));
        } else {
            ((HistoryViewHolder) holder).bind((HistoryHabit) items.get(position));
        }
        setAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<HistoryHabit> historyList) {
        items.clear();
        LinkedHashMap<String, List<HistoryHabit>> groupedData = new LinkedHashMap<>();


        for (HistoryHabit habit : historyList) {
            groupedData.putIfAbsent(habit.getDate(), new ArrayList<>());
            groupedData.get(habit.getDate()).add(habit);
        }


        List<String> sortedDates = new ArrayList<>(groupedData.keySet());
        sortedDates.sort((date1, date2) -> date2.compareTo(date1));

        for (String date : sortedDates) {
            items.add(date);
            items.addAll(groupedData.get(date));
        }

        notifyDataSetChanged();
    }


    static class DateViewHolder extends RecyclerView.ViewHolder {
        private final ItemDataBinding binding;

        DateViewHolder(ItemDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String date) {
            binding.dataThisDay.setText(date);
        }
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding binding;

        HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HistoryHabit habit) {

            binding.tvHabitName.setText(habit.getName());
            binding.tvHabitDescription.setText(habit.getDescription());
            if (habit.getImage() != null && habit.getImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(habit.getImage(), 0, habit.getImage().length);
                Glide.with(binding.ivHabitIcon.getContext())
                        .load(bitmap)
                        .placeholder(R.drawable.read)
                        .error(R.drawable.read)
                        .into(binding.ivHabitIcon);
            } else {
                Glide.with(binding.ivHabitIcon.getContext())
                        .load(R.drawable.read)
                        .into(binding.ivHabitIcon);
            }
        }
    }
    private void setAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_in_animation);
        view.startAnimation(animation);
    }


}
