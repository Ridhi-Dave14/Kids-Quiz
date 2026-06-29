package com.example.kidsquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class AdditionActivity extends AppCompatActivity {

    TextView question, option1, option2, option3, option4, nextBtn, Qno, totalQ, showscore;
    private ProgressBar progressBar;
    //int currentQ = 0;
    //int correct;
    private int score = 0;
    int position = 0;
    int allQue = 10 ;
    int i=1;
    //int progress = (currentQ * 100) / allQue;
    String listSize;
    String positionNo;
    int correctAns;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.nextBtn);
        Qno = findViewById(R.id.Qno);
        totalQ = findViewById(R.id.totalQ);
        showscore = findViewById(R.id.showscore);
        progressBar = findViewById(R.id.progressBar);

        LoadQuestion();
        EnableOption();
        ClearOption();

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        nextBtn.setOnClickListener(view -> {

            progressBar.setProgress(i++);
            position++;
            LoadQuestion();
            EnableOption();
            ClearOption();
            checkNext();
        });


    }

    private void EnableOption() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
        nextBtn.setEnabled(false);
    }

    private void ClearOption() {
        option1.setBackgroundResource(R.drawable.sub_bg);
        option1.setTextColor(getApplicationContext().getColor(R.color.black));

        option2.setBackgroundResource(R.drawable.sub_bg);
        option2.setTextColor(getApplicationContext().getColor(R.color.black));

        option3.setBackgroundResource(R.drawable.sub_bg);
        option3.setTextColor(getApplicationContext().getColor(R.color.black));

        option4.setBackgroundResource(R.drawable.sub_bg);
        option4.setTextColor(getApplicationContext().getColor(R.color.black));

        nextBtn.setBackgroundResource(R.drawable.disable_btn);
    }

    private void checkNext() {
        if (position == 10) {
            Intent intent = new Intent(AdditionActivity.this, ResultActivity.class);
            intent.putExtra("score",String.valueOf(score));
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    private void LoadQuestion() {
        option1.setBackgroundResource(R.drawable.sub_bg);
        option2.setBackgroundResource(R.drawable.sub_bg);
        option3.setBackgroundResource(R.drawable.sub_bg);
        option4.setBackgroundResource(R.drawable.sub_bg);


        Random random = new Random();

        int num1 = random.nextInt(30) + 1;
        int num2 = random.nextInt(30) + 1;

        correctAns = num1 + num2;
        question.setText(num1 + " + " + num2 + " = ? ");

        setUp();
        generateOptions(correctAns);

    }

    @SuppressLint("SetTextI18n")
    private void setUp() {
        allQue = 10;
        listSize = String.valueOf(allQue);
        totalQ.setText("/"+listSize);

        if (position != allQue) {
            positionNo = String.valueOf(position+1);
            Qno.setText(positionNo);
        } else {
            positionNo = String.valueOf(position);
            Qno.setText(positionNo);
        }


    }

    private void OptionCheck(int correct) {

        option1.setOnClickListener(v->{
            if (option1.getText().equals(""+correct)){
                score++;
                showscore.setText(String.valueOf(score));
                option1.setBackgroundResource(R.drawable.correct_bg);
                option1.setTextColor(getApplicationContext().getColor(R.color.white));
            } else {
                option1.setBackgroundResource(R.drawable.wrong_bg);
                option1.setTextColor(getApplicationContext().getColor(R.color.white));
            }
            DisableOption();
            nextBtn.setBackgroundResource(R.drawable.item_bg);
        });

        option2.setOnClickListener(v->{
            if (option2.getText().equals(""+correct)){
                score++;
                showscore.setText(String.valueOf(score));
                option2.setBackgroundResource(R.drawable.correct_bg);
                option2.setTextColor(getApplicationContext().getColor(R.color.white));
            } else {
                option2.setBackgroundResource(R.drawable.wrong_bg);
                option2.setTextColor(getApplicationContext().getColor(R.color.white));
            }
            DisableOption();
            nextBtn.setBackgroundResource(R.drawable.item_bg);
        });

        option3.setOnClickListener(v->{
            if (option3.getText().equals(""+correct)){
                score++;
                showscore.setText(String.valueOf(score));
                option3.setBackgroundResource(R.drawable.correct_bg);
                option3.setTextColor(getApplicationContext().getColor(R.color.white));
            } else {
                option3.setBackgroundResource(R.drawable.wrong_bg);
                option3.setTextColor(getApplicationContext().getColor(R.color.white));
            }
            DisableOption();
            nextBtn.setBackgroundResource(R.drawable.item_bg);
        });

        option4.setOnClickListener(v->{
            if (option4.getText().equals(""+correct)){
                score++;
                showscore.setText(String.valueOf(score));
                option4.setBackgroundResource(R.drawable.correct_bg);
                option4.setTextColor(getApplicationContext().getColor(R.color.white));
            } else {
                option4.setBackgroundResource(R.drawable.wrong_bg);
                option4.setTextColor(getApplicationContext().getColor(R.color.white));
            }
            DisableOption();
            nextBtn.setBackgroundResource(R.drawable.item_bg);
        });
    }

    private void DisableOption() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
        nextBtn.setEnabled(true);
    }


    private void generateOptions(int correct) {
        Random random = new Random();

        int correctOption = random.nextInt(3) + 1;
        int btn1, btn2, btn3, btn4;

        do {
            btn1 = random.nextInt(100)-correct;
        } while (btn1 == correct);

        do {
            btn2 = correct + random.nextInt(100);
        } while (btn2 == correct || btn2 == btn1);

        do {
            btn3 = random.nextInt(100)-correct;
        } while (btn3 == correct || btn3 == btn1 || btn3 == btn2);

        do {
            btn4 = correct + random.nextInt(100);
        } while (btn4 == correct || btn4 == btn1 || btn4 == btn2 || btn4 == btn3);

        switch (correctOption) {

            case 1:
                option1.setText(String.valueOf(correct));
                option2.setText(String.valueOf(btn1));
                option3.setText(String.valueOf(btn2));
                option4.setText(String.valueOf(btn3));
                break;

            case 2:
                option1.setText(String.valueOf(btn1));
                option2.setText(String.valueOf(correct));
                option3.setText(String.valueOf(btn2));
                option4.setText(String.valueOf(btn3));
                break;

            case 3:
                option1.setText(String.valueOf(btn1));
                option2.setText(String.valueOf(btn2));
                option3.setText(String.valueOf(correct));
                option4.setText(String.valueOf(btn3));

            case 4:
                option1.setText(String.valueOf(btn1));
                option2.setText(String.valueOf(btn2));
                option3.setText(String.valueOf(btn3));
                option4.setText(String.valueOf(correct));
        }
        OptionCheck(correct);

    }

    //for ad
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