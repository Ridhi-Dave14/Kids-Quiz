package com.example.kidsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignIn extends AppCompatActivity {

    TextInputLayout email, Password;
    Button loginBtn;
    TextView SignUp, forgetBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        SignUp = findViewById(R.id.SignUp);
        loginBtn = findViewById(R.id.loginBtn);
        forgetBtn = findViewById(R.id.forgetBtn);

        forgetBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, ForgotPassword.class);
            startActivity(intent);
        });

        SignUp.setOnClickListener(view1 -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
            String Email = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(Password.getEditText()).getText().toString().trim();

            if (TextUtils.isEmpty(Email)) {
                email.setError("Email required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Password.setError("Password required");
                return;
            }

            fAuth.signInWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        } else {
                            Toast.makeText(SignIn.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}

