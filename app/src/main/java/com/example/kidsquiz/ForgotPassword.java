package com.example.kidsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    Button forgotbtn;
    TextInputLayout email;
    TextView SignIn;
    private String Email;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.email);
        forgotbtn = findViewById(R.id.forgotbtn);
        SignIn = findViewById(R.id.SignIn);

        fAuth = FirebaseAuth.getInstance();

        SignIn.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotPassword.this, SignIn.class);
            startActivity(intent);
            finish();
        });

        forgotbtn.setOnClickListener(view -> {
            Email = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
            if (!TextUtils.isEmpty(Email)) {
                forgotPassword();
            } else {
                email.setError("Email required");
            }
        });

    }

    private void forgotPassword() {
        forgotbtn.setVisibility(View.INVISIBLE);


        fAuth.sendPasswordResetEmail(Email).addOnSuccessListener(unused -> {
                    Toast.makeText(ForgotPassword.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword.this, SignIn.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ForgotPassword.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    forgotbtn.setVisibility(View.VISIBLE);
                });
    }
}