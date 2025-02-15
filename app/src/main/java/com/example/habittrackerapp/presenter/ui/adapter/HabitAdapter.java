package com.example.habittrackerapp.presenter.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.habittrackerapp.R;
import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.databinding.ItemHabitBinding;
import com.example.habittrackerapp.presenter.ui.home.fragment.HomeFragmentDirections;

public class HabitAdapter extends ListAdapter<Habit, HabitAdapter.HabitViewHolder> {
    private final OnHabitCompleteListener completeListener;

    public interface OnHabitCompleteListener {
        void onHabitComplete(Habit habit);
    }

    public HabitAdapter(OnHabitCompleteListener completeListener) {
        super(DIFF_CALLBACK);
        this.completeListener = completeListener;

    }
    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitBinding binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HabitViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = getItem(position);
        holder.bind(habit);
        setAnimation(holder.itemView);
    }

    class HabitViewHolder extends RecyclerView.ViewHolder {
        private final ItemHabitBinding binding;

        HabitViewHolder(ItemHabitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Habit habit) {
            binding.tvHabitName.setText(habit.getName());
            binding.tvHabitDescription.setText(habit.getDescription());
             binding.editHabit.setOnClickListener(v -> {
                 NavDirections action = HomeFragmentDirections
                         .actionHomeFragmentToUpdateHabitFragment(habit.getId());
                Navigation.findNavController(v).navigate(action);
            });


            binding.done.setOnClickListener(v -> {
                if (completeListener != null) {
                    completeListener.onHabitComplete(habit);
                }
            });

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

    private static final DiffUtil.ItemCallback<Habit> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Habit oldItem, @NonNull Habit newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Habit oldItem, @NonNull Habit newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    java.util.Arrays.equals(oldItem.getImage(), newItem.getImage());
        }
    };
    private void setAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_in_animation);
        view.startAnimation(animation);
    }

}






