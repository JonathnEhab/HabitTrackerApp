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
import com.example.habittrackerapp.R;
import com.example.habittrackerapp.databinding.FragmentNewHabitBinding;
import com.example.habittrackerapp.presenter.ui.viewModels.NewHabitViewModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewHabitFragment extends Fragment {

    private NewHabitViewModel newHabitViewModel;
    private FragmentNewHabitBinding binding;
    private byte[] selectedImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewHabitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newHabitViewModel = new ViewModelProvider(this).get(NewHabitViewModel.class);

        binding.ivHabitImage.setOnClickListener(v -> pickImage());

        binding.btnSaveHabit.setOnClickListener(v -> saveHabit());
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        try {
                            selectedImage = convertUriToByteArray(imageUri);
                            Glide.with(this).load(imageUri).into(binding.ivHabitImage);
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


    private void saveHabit() {
        String name = binding.etHabitName.getText().toString().trim();
        String description = binding.etHabitDescription.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() ) {
            showToast(requireActivity(),"املي كل المطلوب متسبش حاجه فاضيه");
            return;
        }


        newHabitViewModel.addNewHabit(name, description, selectedImage);


        binding.etHabitName.setText("");
        binding.etHabitDescription.setText("");
        binding.ivHabitImage.setImageResource(R.drawable.read);
        selectedImage = null;

        Navigation.findNavController(requireView())
                .navigate(NewHabitFragmentDirections.actionNewHabitFragmentToHomeFragment());
        vibrate(requireActivity(),50);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
