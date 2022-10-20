package com.example.fosty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileSettings extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseUser user;
    private TextInputEditText fullname, username, email;

    private String userId;
    private ImageView backButton;
    private TextView usernameHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_settings);

        fullname = (TextInputEditText) findViewById(R.id.fullNameAccountEdit);
        username = (TextInputEditText) findViewById(R.id.usernameAccountEdit);
        email = (TextInputEditText) findViewById(R.id.emailAccountEdit);
        usernameHeader = (TextView) findViewById(R.id.usernameHeader);

        backButton = (ImageView) findViewById(R.id.backIconSettings);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){

                    fullname.setText(userProfile.fullName);
                    username.setText(userProfile.username);
                    email.setText(userProfile.email);
                    usernameHeader.setText(userProfile.username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileSettings.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });



    }




    @Override
    public void onBackPressed() {
        finish();
    }
}