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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    TextInputLayout email, Password, confirmPassword;
    TextView signInText;
    Button RegisterBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email = findViewById(R.id.email);
        Password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.confirmPassword);
        RegisterBtn = findViewById(R.id.RegisterBtn);
        signInText = findViewById(R.id.signInText);

        fAuth = FirebaseAuth.getInstance();

        signInText.setOnClickListener(view1 -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        });

        RegisterBtn.setOnClickListener(view -> {
            String Email = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(Password.getEditText()).getText().toString().trim();
            String confirmPass = Objects.requireNonNull(confirmPassword.getEditText()).getText().toString().trim();

            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)) {
                Toast.makeText(SignUp.this,"Enter all details",Toast.LENGTH_SHORT).show();
            }
            if (!password.equals(confirmPass)) {
                confirmPassword.setError("Password does not match");
                return;
            }

            fAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "User created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, HomePage.class);
                    startActivity(intent);
                    savescore(Email,"0");
                    savedata(Email,password);
                } else {
                    Toast.makeText(SignUp.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    //fun for save user data in database
    private void savedata(String email, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("Email",email);
        user.put("Password", password);
        db.collection("User")
                .document("Data")
                .collection("UserData")
                .document(email)
                .set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(SignUp.this, "done", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(SignUp.this, "not done", Toast.LENGTH_SHORT).show());

    }

    //fun for save score in database
    private void savescore(String email, String score) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("Score", score);
        db.collection("User")
                .document("Data")
                .collection("Score")
                .document(email)
                .set(user)
                .addOnSuccessListener(aVoid -> Toast.makeText(SignUp.this, "done", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(SignUp.this, "not done", Toast.LENGTH_SHORT).show());
    }

}

