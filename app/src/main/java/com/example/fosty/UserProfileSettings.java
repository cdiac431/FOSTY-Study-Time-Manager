package com.example.fosty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class UserProfileSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_settings);

        TextInputEditText textInputEditText = (TextInputEditText) findViewById(R.id.fullNameAccountEdit);
        textInputEditText.setText("Cristian Diac");

        TextInputEditText textInputEditText2 = (TextInputEditText) findViewById(R.id.usernameAccountEdit);
        textInputEditText2.setText("cdiac431");
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}