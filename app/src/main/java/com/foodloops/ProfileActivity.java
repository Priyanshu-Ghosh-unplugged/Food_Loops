package com.foodloops;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.foodloops.R;
import com.foodloops.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.auth.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    @SuppressLint("RestrictedApi")
    private User userProfile;

    // UI components
    private ImageView backButton;
    private ImageView profileImage;
    private TextView userName;
    private TextView userBio;
    private TextView fullName;
    private TextView email;
    private TextView phone;
    private TextView gender;
    private TextView address;
    private Button updateAccountButton;
    private Button deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        // Check if user is logged in
        if (currentUser == null) {
            // Redirect to login
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize UI components
        initializeUI();
        setupBottomNavigation();

        // Load user profile data
        loadUserProfile();
    }

    private void initializeUI() {
        // Find views
        backButton = findViewById(R.id.back_button);
        profileImage = findViewById(R.id.profile_image);
        userName = findViewById(R.id.user_name);
        userBio = findViewById(R.id.user_bio);
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        updateAccountButton = findViewById(R.id.update_account_button);
        deleteAccountButton = findViewById(R.id.delete_account_button);

        // Set click listeners
        backButton.setOnClickListener(v -> finish());

        updateAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        //deleteAccountButton.setOnClickListener(v -> showDeleteAccountConfirmation());
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_categories) {
                // Handle categories navigation
                return true;
            } else if (itemId == R.id.nav_favorites) {
                // Handle favorites navigation
                return true;
            } else if (itemId == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }

    private void loadUserProfile() {
        // Show loading indicator
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        DocumentReference userRef = db.collection("users").document(currentUser.getUid());
        userRef.get().addOnCompleteListener(task -> {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);

            if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                userProfile = task.getResult().toObject(User.class);
                //displayUserProfile();
            } else {
                Toast.makeText(this, "Error loading profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void displayUserProfile() {
//        if (userProfile == null) return;
//
//        // Set user name and bio
//        userName.setText(userProfile.getName());
//        userBio.setText("Food enthusiast & Home chef"); // This could be a field in the User class
//
//        // Load profile image
//        if (userProfile.getProfileImageUrl() != null && !userProfile.getProfileImageUrl().isEmpty()) {
//            Glide.with(this)
//                    .load(userProfile.getProfileImageUrl())
//                    .placeholder(R.drawable.default_profile)
//                    .circleCrop()
//                    .into(profileImage);
//        } else {
//            profileImage.setImageResource(R.drawable.default_profile);
//        }
//
//        // Set user details
//        fullName.setText(userProfile.getName());
//        email.setText(userProfile.getEmail());
//        phone.setText(userProfile.getPhoneNumber());
//        gender.setText(userProfile.getGender());
//        address.setText(userProfile.getAddress());
//    }
//
//    private void showDeleteAccountConfirmation() {
//        new AlertDialog.Builder(this)
//                .setTitle("Delete Account")
//                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
//                .setPositiveButton("Delete", (dialog, which) -> deleteAccount())
//                .setNegativeButton("Cancel", null)
//                .show();
//    }

    private void deleteAccount() {
        // Show loading indicator
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        // First delete user data from Firestore
        db.collection("users").document(currentUser.getUid())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Then delete the Firebase Auth account
                    currentUser.delete()
                            .addOnSuccessListener(aVoid1 -> {
                                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                                Toast.makeText(ProfileActivity.this,
                                        "Account deleted successfully",
                                        Toast.LENGTH_SHORT).show();

                                // Redirect to login activity
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                                Toast.makeText(ProfileActivity.this,
                                        "Failed to delete account: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this,
                            "Failed to delete account data: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}
