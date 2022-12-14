package com.example.fosty;

import static androidx.navigation.Navigation.findNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button registerButton, loginButton;
    private TextInputEditText emailText, passwordText;
    private TextView resetPassword;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        bundle = new Bundle();


        mAuth = FirebaseAuth.getInstance();

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        emailText = (TextInputEditText) findViewById(R.id.emailInputLogin);
        passwordText = (TextInputEditText) findViewById(R.id.passwordInputLogin);

        resetPassword = (TextView) findViewById(R.id.resetPasswordView);
        resetPassword.setOnClickListener(this);

    }







    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.loginButton:
                userLogin();
                //startActivity(new Intent(MainActivity.this, ProfileUser.class));
                break;
            case R.id.resetPasswordView:
                startActivity(new Intent(this, ResetPassword.class));
                break;
            }
        }

    private void userLogin() {

      /*  String sEmail = emailText.getText().toString().trim();
        String sPassword = passwordText.getText().toString().trim();

        if (sEmail.isEmpty()){
            emailText.setError("Email is required!");
            emailText.requestFocus();
            return;
        }

        if (sPassword.isEmpty()){
            passwordText.setError("Password is required!");
            passwordText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            emailText.setError("Please provide valid email!");
            emailText.requestFocus();
            return;
        }

        if (sPassword.length() < 8){
            passwordText.setError("Min password length is 6 characters!");
            passwordText.requestFocus();
            return;
        }*/

        mAuth.signInWithEmailAndPassword("cdiac431@gmail.com","cc19812001").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    /*bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Cristian2");
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/

                    bundle.putString("username", "cdiac431");
                    bundle.putString("password", "12345");
                    mFirebaseAnalytics.logEvent("login_succesfully", bundle);
                    System.out.println(bundle+"HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(MainActivity.this, ProfileUser.class));

                    if (user.isEmailVerified()){


                    } else {
                        Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
                        userFirebase.sendEmailVerification();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login! Plese check out your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
