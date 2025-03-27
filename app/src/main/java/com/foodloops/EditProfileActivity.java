package com.foodloops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends Activity {

    private CircleImageView profileImage;
    private ImageView cameraIcon;
    private TextView fullName, email, phone, gender, address;
    private Button updateAccount, deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        profileImage = findViewById(R.id.profile_image);
        cameraIcon = findViewById(R.id.camera_icon);
        fullName = findViewById(R.id.text_full_name);
        email = findViewById(R.id.text_email);
        phone = findViewById(R.id.text_phone);
        gender = findViewById(R.id.text_gender);
        address = findViewById(R.id.text_address);
        updateAccount = findViewById(R.id.btn_update_account);
        deleteAccount = findViewById(R.id.btn_delete_account);

        // Set user details (hardcoded for now, should be fetched dynamically)
        fullName.setText("User");
        email.setText("user@example.com");
        phone.setText("phone no");
        gender.setText("Gender");
        address.setText("Address");

        // Handle profile picture update
        cameraIcon.setOnClickListener(v -> Toast.makeText(EditProfileActivity.this, "Change profile picture", Toast.LENGTH_SHORT).show());

        // Handle update button click
        updateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Handle delete button click
        deleteAccount.setOnClickListener(v -> Toast.makeText(EditProfileActivity.this, "Account deletion not implemented", Toast.LENGTH_SHORT).show());
    }
}
