package com.example.kidsquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SpinActivity extends AppCompatActivity {

    Button spin;
    ImageView wheel;
    TextView spinChance;
    private DatabaseReference database;
    private CountDownTimer timer;
    private final String[] itemTitles = new String[]{"100", "Try Again", "500", "Try Again", "200", "Try Again"};
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        wheel = findViewById(R.id.wheel);
        spinChance = findViewById(R.id.spinChance);

        display();


        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    database = FirebaseDatabase.getInstance().getReference("Score");
                    database.child(user.getUid())
                            .child("chance")
                            .get()
                            .addOnSuccessListener(dataSnapshot -> {
                                if (dataSnapshot.exists()) {
                                    int dbChance = Integer.parseInt(dataSnapshot.getValue().toString());

                                    spin.setEnabled(false);
                                    int spin = new Random().nextInt(6);
                                    float degrees = 60f * spin;

                                    timer = new CountDownTimer(6000, 60) {
                                        float rotation = 0f;

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            rotation += 5f;
                                            if (rotation >= degrees) {
                                                rotation = degrees;
                                                timer.cancel();
                                                showResult(itemTitles[spin]);
                                            }
                                            wheel.setRotation(rotation);
                                        }


                                        @Override
                                        public void onFinish() {
                                            // Not yet implemented
                                        }
                                    }.start();

                                    int chance = dbChance - 1;
                                    // code to add data in database
                                    FirebaseDatabase.getInstance().getReference("Score")
                                            .child(user.getUid())
                                            .child("chance")
                                            .setValue(chance)
                                            .addOnSuccessListener(aVoid -> {
                                            });
                                }
                            });
                }
            }

            private void showResult(String itemTitle) {
                store(itemTitle);
                spin.setEnabled(true);
            }


        });
    }


    private void display() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference("Score");
            database.child(user.getUid())
                    .child("chance")
                    .get()
                    .addOnSuccessListener(dataSnapshot -> spinChance.setText(dataSnapshot.getValue().toString()));
        }
    }


    // function for get and store score in database
    private void store(String itemTitle) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference("Score");
            database.child(user.getUid())
                    .child("score")
                    .get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            Object dbPrice = dataSnapshot.getValue();
                            if (dbPrice != null) {
                                if (itemTitle.equals("Try Again")) {
                                    Toast.makeText(SpinActivity.this, "No change in score..", Toast.LENGTH_SHORT).show();
                                } else {
                                    int sum = Integer.parseInt(dbPrice.toString()) + Integer.parseInt(itemTitle);
                                    // code to add data in database
                                    FirebaseDatabase.getInstance().getReference("Score")
                                            .child(user.getUid())
                                            .child("score")
                                            .setValue(sum)
                                            .addOnCompleteListener(task -> FirebaseDatabase.getInstance().getReference("Score")
                                                    .child(user.getUid())
                                                    .child("withdraw")
                                                    .setValue(sum)
                                                    .addOnSuccessListener(aVoid -> Toast.makeText(SpinActivity.this, "done..", Toast.LENGTH_SHORT).show()));
                                }
                            }
                        }
                    });
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