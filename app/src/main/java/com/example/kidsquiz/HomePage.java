package com.example.kidsquiz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kidsquiz.menu.AboutUs;
import com.example.kidsquiz.menu.LogOutt;
import com.example.kidsquiz.menu.Rating;
import com.example.kidsquiz.menu.Share;
import com.example.kidsquiz.menu.Withdraw;

public class HomePage extends AppCompatActivity {

    TextView score;
    ImageView drawMenu;
    LinearLayout add, sub, mul, div, anime, spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        score = findViewById(R.id.score);
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        anime = findViewById(R.id.anime);
        spin = findViewById(R.id.spin);
        drawMenu = findViewById(R.id.drawMenu);

        drawMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        add.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, AdditionActivity.class);
            startActivity(intent);
            Toast.makeText(HomePage.this, "Loading Addition Activity", Toast.LENGTH_SHORT).show();
        });

        sub.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, SubtractionActivity.class);
            startActivity(intent);
            Toast.makeText(HomePage.this, "Loading Subtraction Activity", Toast.LENGTH_SHORT).show();
        });

        mul.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, MultiplyActivity.class);
            startActivity(intent);
            Toast.makeText(HomePage.this, "Loading Multiplication Activity", Toast.LENGTH_SHORT).show();
        });

        div.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, DivideActivity.class);
            startActivity(intent);
            Toast.makeText(HomePage.this, "Loading Division Activity", Toast.LENGTH_SHORT).show();
        });

        anime.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, AnimationActivity.class);
            startActivity(intent);
            Toast.makeText(HomePage.this, "Loading Animation Activity", Toast.LENGTH_SHORT).show();
        });

        spin.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, SpinActivity.class);
            startActivity(intent);
            Toast.makeText(HomePage.this, "Loading Spin Activity", Toast.LENGTH_SHORT).show();
        });

    }


    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.menu);

        LinearLayout aboutUs = (LinearLayout) dialog.findViewById(R.id.aboutUs);
        LinearLayout share = (LinearLayout) dialog.findViewById(R.id.share);
        LinearLayout withdrawal = (LinearLayout) dialog.findViewById(R.id.withdraw);
        LinearLayout rating = (LinearLayout) dialog.findViewById(R.id.rating);
        LinearLayout Logout = (LinearLayout) dialog.findViewById(R.id.logout);

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, AboutUs.class);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Share.class);
                startActivity(intent);
            }
        });

        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Withdraw.class);
                startActivity(intent);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Rating.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, LogOutt.class);
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}