package com.example.habittrackerapp.presenter.ui.home.fragment;

import static com.example.habittrackerapp.util.Utils.showToast;
import static com.example.habittrackerapp.util.Utils.vibrate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.habittrackerapp.data.habit.Habit;
import com.example.habittrackerapp.databinding.FragmentUpdateHabitBinding;
import com.example.habittrackerapp.presenter.ui.viewModels.UpdateHabitViewModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class UpdateHabitFragment extends Fragment {

    private UpdateHabitViewModel updateHabitViewModel;
    private FragmentUpdateHabitBinding binding;
    private int habitId;
    private Habit currentHabit;
    private byte[] updatedImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateHabitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateHabitViewModel = new ViewModelProvider(this).get(UpdateHabitViewModel.class);

        habitId = UpdateHabitFragmentArgs.fromBundle(getArguments()).getHabitId();

        updateHabitViewModel.getHabitById(habitId).observe(getViewLifecycleOwner(), habit -> {
            if (habit != null) {
                currentHabit = habit;
                displayHabitData(habit);
            }
        });

        binding.ivHabitIcon.setOnClickListener(v -> pickImage());
        binding.btnSaveHabit.setOnClickListener(v -> updateHabit());
    }

    private void displayHabitData(Habit habit) {
        binding.etHabitName.setText(habit.getName());
        binding.etHabitDescription.setText(habit.getDescription());


        updatedImage = habit.getImage();

        if (habit.getImage() != null) {
            Glide.with(this).load(habit.getImage()).into(binding.ivHabitIcon);
        }
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        try {
                            updatedImage = convertUriToByteArray(imageUri);
                            Glide.with(this).load(imageUri).into(binding.ivHabitIcon);
                        } catch (IOException e) {
                            e.printStackTrace();
                            showToast(requireActivity(),"في مشكله في تحميل الصوره");

                        }
                    }
                }
            });

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private byte[] convertUriToByteArray(Uri uri) throws IOException {
        InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }




    private void updateHabit() {
        String updatedName = binding.etHabitName.getText().toString().trim();
        String updatedDescription = binding.etHabitDescription.getText().toString().trim();

        if (updatedName.isEmpty() || updatedDescription.isEmpty() ) {
            showToast(requireActivity(),"املي كل المطلوب متسبش حاجه فاضيه");
            return;
        }

        currentHabit.setName(updatedName);
        currentHabit.setDescription(updatedDescription);
        currentHabit.setImage(updatedImage);
        updateHabitViewModel.updateHabit(currentHabit);

        Navigation.findNavController(requireView())
                .navigate(UpdateHabitFragmentDirections.actionUpdateHabitFragmentToHomeFragment());
        vibrate(requireActivity(),50);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
