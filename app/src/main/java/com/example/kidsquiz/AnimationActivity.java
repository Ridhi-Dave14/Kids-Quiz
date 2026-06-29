package com.example.kidsquiz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AnimationActivity extends AppCompatActivity {

    private DatabaseReference database;
    Button start;
    TextView animationChance;
    LottieAnimationView ltAnimation;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        start = findViewById(R.id.start);
        animationChance = findViewById(R.id. animationChance);


        display();

        start.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                database = FirebaseDatabase.getInstance().getReference("Score");
                database.child(user.getUid())
                        .child("chance")
                        .get()
                        .addOnSuccessListener(dataSnapshot -> {
                            if (dataSnapshot.exists()) {
                                int dbChance = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                                if (dbChance == 0) {
                                    start.setEnabled(false);
                                    Toast.makeText(AnimationActivity.this, "First play quiz...", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    ltAnimation.playAnimation();
                                    getScore();
                                    int chance = dbChance - 1;
                                    // code to add data in database
                                    FirebaseDatabase.getInstance().getReference("Score")
                                            .child(user.getUid())
                                            .child("chance")
                                            .setValue(chance)
                                            .addOnSuccessListener(aVoid -> {});
                                }
                            }
                        });
            }
        });

    }

    // get previous chance from database
    private void getScore() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference("Score");
            database.child(user.getUid())
                    .child("score")
                    .get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            int dbScore = Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            int sum = dbScore + 10;
                            setScore(sum);
                        }
                    });
        }

    }

    // display chance
    private void display() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference("Score");
            database.child(user.getUid())
                    .child("chance")
                    .get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            animationChance.setText(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                        }
                    });
        }
    }

    // function to store +10 score
    private void setScore(int sum) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // code to add data in database
            FirebaseDatabase.getInstance().getReference("Score")
                    .child(user.getUid()).child("withdraw")
                    .setValue(sum)
                    .addOnCompleteListener(task -> FirebaseDatabase.getInstance().getReference("Score")
                            .child(user.getUid()).child("score")
                            .setValue(sum));
        }
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }



}