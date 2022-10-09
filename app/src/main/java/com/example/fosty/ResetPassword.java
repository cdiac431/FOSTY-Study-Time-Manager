package com.example.fosty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener  {

    private ImageView imageBack;
    private Button resetPasswordButton;
    private TextInputEditText emailResetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        imageBack = (ImageView) findViewById(R.id.backIconReset);
        imageBack.setOnClickListener(this);

        resetPasswordButton = (Button) findViewById(R.id.resetPasswordButton);
        resetPasswordButton.setOnClickListener(this);

        emailResetPassword = (TextInputEditText) findViewById(R.id.resetPasswordInput);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resetPasswordButton:
                resetPassword();
                break;

            case R.id.backIconReset:
                finish();
                break;
        }
    }

    private void resetPassword(){
        String stringEmailReset = emailResetPassword.getText().toString().trim();

        if (stringEmailReset.isEmpty()){
            emailResetPassword.setError("Email is required!");
            emailResetPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(stringEmailReset).matches()){
            emailResetPassword.setError("Please provide valid email!");
            emailResetPassword.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(stringEmailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    emailResetPassword.getText().clear();
                    Toast.makeText(ResetPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(ResetPassword.this, "Try again, something wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}