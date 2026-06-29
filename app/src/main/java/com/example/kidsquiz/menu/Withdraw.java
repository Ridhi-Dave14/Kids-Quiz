package com.example.kidsquiz.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kidsquiz.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Withdraw extends AppCompatActivity {

    private DatabaseReference database;
    Button saveData;
    EditText name, email, number, upiid, amount;
    private int sum = 0;
    private AdView adView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_withdraw);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        upiid = findViewById(R.id.upiId);
        amount = findViewById(R.id.amount);
        saveData = findViewById(R.id.saveData);

        saveData.setOnClickListener(view -> {
            if (name.getText().toString().isEmpty() ||
                    email.getText().toString().isEmpty() ||
                    number.getText().toString().isEmpty() ||
                    upiid.getText().toString().isEmpty() ||
                    amount.getText().toString().isEmpty()) {
                Toast.makeText(Withdraw.this, "Enter every Value..", Toast.LENGTH_SHORT).show();
            } else {
                //code for sending data to google drive
                String url = "https://script.google.com/macros/s/AKfycbwlXF72SJ2ihNqEH_tBzjgUhgbD7VCynQeGeR44ORUvQvNRWyBEXKLSR8VHH5AWiNjI/exec";
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, url,
                        response -> {
                            Toast.makeText(Withdraw.this, response, Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            number.setText("");
                            upiid.setText("");
                            amount.setText("");
                        },
                        error -> Toast.makeText(Withdraw.this, error.toString(), Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("contact", number.getText().toString());
                        params.put("upiid", upiid.getText().toString());
                        params.put("amount", amount.getText().toString());
                        return params;
                    }
                };
                Volley.newRequestQueue(Withdraw.this).add(stringRequest);
            }

            String enteredPrice = amount.getText().toString();

            //get previous score from database
            database = FirebaseDatabase.getInstance().getReference("Score");
            database.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .child("withdraw")
                    .get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            String dbPrice = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                            if (Integer.parseInt(enteredPrice) <= Integer.parseInt(dbPrice)) {
                                sum = Integer.parseInt(dbPrice) - Integer.parseInt(enteredPrice);

                                //code to add data in database

                                database.child("Score")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("withdraw").setValue(sum)
                                        .addOnCompleteListener(task -> {
                                            database.child("Score")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("score").setValue(sum);
                                            // startActivity(new Intent(this, FirstActivity.class));
                                        });
                            } else {
                                Toast.makeText(Withdraw.this, "Not sufficient score..", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
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