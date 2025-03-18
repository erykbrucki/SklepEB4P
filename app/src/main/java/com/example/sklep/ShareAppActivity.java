package com.example.sklep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ShareAppActivity extends AppCompatActivity {
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> shareApp());
    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Polecam tę aplikację! Pobierz ją teraz.");
        startActivity(Intent.createChooser(shareIntent, "Udostępnij za pomocą"));
    }
}
