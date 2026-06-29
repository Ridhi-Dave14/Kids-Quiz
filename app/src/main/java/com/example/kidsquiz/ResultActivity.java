package com.example.kidsquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    int correct, allQue;
    TextView score, Correct, incorrect;
    Button exploreBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String loggedEmail;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        score = findViewById(R.id.score);
        Correct = findViewById(R.id.Correct);
        incorrect = findViewById(R.id.incorrect);
        exploreBtn = findViewById(R.id.exploreBtn);

        String correctScore =String.valueOf(correct);
        String wrongScore =String.valueOf(allQue - correct);
        String totalScore =String.valueOf(allQue);

        Correct.setText(correctScore+" Correct");
        incorrect.setText(wrongScore+" Incorrect");
        score.setText("You got the "+correctScore+" out of "+totalScore);

        exploreBtn.setOnClickListener(v -> {
            store(correctScore);
            Intent intent = new Intent(ResultActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        });

    }

/*
//fun for get current score
    public void curscore(String newscore){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loggedEmail = mAuth.getCurrentUser().getEmail().toString();
        db.collection("User")
                .document("Data")
                .collection("Score")
                .document(loggedEmail)
                .get()
                .addOnCompleteListener(task -> {
                    StringBuffer result = new StringBuffer();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        result.append(document.getData().get("score"));
                        store(result.toString(),newscore);
                    }
                });
    }*/


    //fun for set score in database
    public void store(String score){

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        int sum = (Integer.parseInt(score)) * 5 ;
        Map<String, String> map = new HashMap<>();
        map.put("score", String.valueOf(sum));
        loggedEmail = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        db.collection("User")
                .document("Data")
                .collection("Score")
                .document(loggedEmail)
                .set(map)
                .addOnSuccessListener(unused -> {
                });
    }


}