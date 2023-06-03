package com.example.mindful.service.profile;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mindful.R;
import com.example.mindful.service.calender.CalenderViewFragment;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileViewFragment extends Fragment {

    final private String TAG = "ProfileViewFragment";

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_CODE_PERMISSION = 2;

    ImageView userProfileImage;
    Button editProfileButton;

    public ProfileViewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileViewFragment newInstance(String param1, String param2) {
        ProfileViewFragment fragment = new ProfileViewFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_view, container, false);

        // Check if the app has permission to read external storage
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission from the user
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            // Permission already granted, proceed with accessing the image
            //pickImage();
        }

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with accessing the image
                pickImage();
            } else {
                // Permission denied, handle accordingly (e.g., show an error message)
            }
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            // Save the image URI or perform further operations
            // Set the image to the ImageView
            userProfileImage.setImageURI(imageUri);

            // Save the image URI to SharedPreferences
            SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("profilePictureUri", imageUri.toString());
            editor.apply();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitWidgets();

        FragmentManager fragmentManager = getChildFragmentManager();
        CalenderViewFragment calenderViewFragment = new CalenderViewFragment();

        fragmentManager.beginTransaction()
                .add(R.id.profile_calender_fragment, calenderViewFragment)
                .commit();

        //change profile picture activity launcher
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            // Check if the data comes from the camera intent
                            if (data == null || data.getData() == null) {
                                // Image captured using the camera
                                // Retrieve the captured image from extras
                                Bundle extras = result.getData().getExtras();
                                Bitmap imageBitmap = (Bitmap) extras.get("data");

                                // Set the image to the ImageView
                                userProfileImage.setImageBitmap(imageBitmap);

                                // Convert the image bitmap to a Uri
                                Uri imageUri = getImageUri(requireContext(), imageBitmap);

                                // Save the image URI to SharedPreferences
                                SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("profilePictureUri", imageUri.toString());
                                editor.apply();
                            } else {
                                // Image selected from the gallery
                                // Get the image Uri
                                Uri imageUri = data.getData();

                                // Set the image to the ImageView
                                userProfileImage.setImageURI(imageUri);
                                // Save the image URI to SharedPreferences
                                SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("profilePictureUri", imageUri.toString());
                                editor.apply();
                            }


                        }
                    }

                    private Uri getImageUri(Context requireContext, Bitmap imageBitmap) {
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(requireContext.getContentResolver(), imageBitmap, "Title", null);
                        return Uri.parse(path);
                    }
                });

        //edit profile button listener
        //Currently just allows user to change and set profile picture from camera or photo gallery
        //TODO make seperate button for changing profile picture and changing other profile info such as name etc.d
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a chooser intent to select between camera and gallery
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);

                // Create the camera intent for taking a picture
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Create the gallery intent for selecting from gallery
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Set the title for the chooser dialog
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Select Image");

                // Add the camera intent as an extra
                chooserIntent.putExtra(Intent.EXTRA_INTENT, cameraIntent);

                // Create an array of acceptable intents for the chooser
                Intent[] intentsArray = {galleryIntent};

                // Add the acceptable intents to the chooser
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentsArray);

                // Launch the chooser dialog
                launcher.launch(chooserIntent);
            }
        });

        // Retrieve the saved image URI from SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String profilePictureUriString = preferences.getString("profilePictureUri", null);

        // Check if a profile picture has been set
        if (profilePictureUriString != null) {
            Uri profilePictureUri = Uri.parse(profilePictureUriString);
            // Set the image to the ImageView
            userProfileImage.setImageURI(profilePictureUri);
        }
        Log.d(TAG, "onViewCreated");
    }


    private void InitWidgets() {

        userProfileImage = requireView().findViewById(R.id.userProfileImage);
        editProfileButton = requireView().findViewById(R.id.editProfileButton);

    }
}