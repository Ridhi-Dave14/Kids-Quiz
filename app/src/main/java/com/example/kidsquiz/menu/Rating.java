package com.example.kidsquiz.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kidsquiz.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Rating extends AppCompatActivity {

    TextView msg;
    Button submitbtn;
    RatingBar ratingBar;
    private AdView adView;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        msg = findViewById(R.id.msg);
        submitbtn = findViewById(R.id.submitBtn);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener((rBar, fl, b) -> {
            msg.setText(String.valueOf(fl));
            switch ((int) rBar.getRating()) {
                case 1:
                    msg.setText("Very Bad");
                    break;
                case 2:
                    msg.setText("Bad");
                    break;
                case 3:
                    msg.setText("Good");
                    break;
                case 4:
                    msg.setText("Great");
                    break;
                case 5:
                    msg.setText("Awesome");
                    break;
                default:
                    msg.setText(" ");
                    break;
            }
        });
        submitbtn.setOnClickListener(v -> {
            String message = String.valueOf(ratingBar.getRating());
            Toast.makeText(Rating.this, "Rating is: " + message, Toast.LENGTH_SHORT).show();
        });
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