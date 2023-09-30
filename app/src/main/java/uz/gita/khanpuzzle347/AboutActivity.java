package uz.gita.khanpuzzle347;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uz.gita.khanpuzzle347.modules.Settings;

public class AboutActivity extends AppCompatActivity {
    View telegram,instagram, pinterest;
    Settings settings;
    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        settings = Settings.getInstance();


        wireUpWidgets();
    }

    private void wireUpWidgets() {
        telegram = findViewById(R.id.telegram);
        instagram = findViewById(R.id.instagram);
        pinterest = findViewById(R.id.pinterest);
        home = findViewById(R.id.buttonHomeInAbout);

        telegram.setOnClickListener(view -> {
            settings.startTouch();
            Uri uri = Uri.parse("https://t.me/J_khan347");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        instagram.setOnClickListener(view -> {
            settings.startTouch();
            Uri uri = Uri.parse("https://www.instagram.com/gita.uzofficial/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        pinterest.setOnClickListener(view -> {
            settings.startTouch();
            Uri uri = Uri.parse("https://www.pinterest.com/betta347/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        home.setOnClickListener(view -> {
            settings.startTouch();
            finish();
        });

    }
    @Override
    protected void onResume() {
        if (settings.isMusicOn())
            settings.startMusic();
        super.onResume();
    }
}