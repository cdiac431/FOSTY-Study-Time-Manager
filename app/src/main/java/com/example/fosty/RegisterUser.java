package com.example.fosty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener  {

    private Button registerUser;
    private TextInputEditText fullNameText, usernameText, emailText, passwordText;

    private FirebaseAuth mAuth;

    View theInflatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        LayoutInflater inflater = LayoutInflater.from(RegisterUser.this);
        theInflatedView = inflater.inflate(R.layout.fragment_study_panel, null);
        theInflatedView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));

        registerUser = (Button) findViewById(R.id.registerNewUser);
        registerUser.setOnClickListener(this);

        fullNameText = (TextInputEditText) findViewById(R.id.fullNameInput);
        usernameText = (TextInputEditText) findViewById(R.id.usernameInput);
        emailText = (TextInputEditText) findViewById(R.id.emailInput);
        passwordText = (TextInputEditText) findViewById(R.id.passwordInput);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerNewUser:
                registerUser();
                break;
        }
    }

    private void registerUser(){

        String sFullName = fullNameText.getText().toString().trim();
        String sUsername = usernameText.getText().toString().trim();
        String sEmail = emailText.getText().toString().trim();
        String sPassword = passwordText.getText().toString().trim();

        if (sFullName.isEmpty()){
            fullNameText.setError("Full Name is required!");
            fullNameText.requestFocus();
            return;
        }

        if (sUsername.isEmpty()){
            usernameText.setError("Username is required!");
            usernameText.requestFocus();
            return;
        }

        if (sEmail.isEmpty()){
            emailText.setError("Email is required!");
            emailText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            emailText.setError("Please provide valid email!");
            emailText.requestFocus();
            return;
        }

        if (sPassword.isEmpty()){
            passwordText.setError("Password is required!");
            passwordText.requestFocus();
            return;
        }

        if (sPassword.length() < 8){
            passwordText.setError("Min password length should be at least 8 caracters!");
            passwordText.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(sFullName, sUsername, sEmail, sPassword);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User has ben registered succesfully!", Toast.LENGTH_LONG).show();

                                                setContentView(theInflatedView);

                                            } else{
                                                Toast.makeText(RegisterUser.this, "Failed to register user! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(RegisterUser.this, "Failed to register user! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}