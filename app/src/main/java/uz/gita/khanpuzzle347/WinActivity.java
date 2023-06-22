package uz.gita.khanpuzzle347;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

import uz.gita.khanpuzzle347.modules.Settings;

public class WinActivity extends AppCompatActivity {
    Button home;
    TextView score;
    TextView record;
    Settings settings;
    String scoreText;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        settings = Settings.getInstance();
        scoreText = settings.getCurrentScore();

        settings = Settings.getInstance();

        home = findViewById(R.id.backHome);
        score  =findViewById(R.id.scoreWin);
        record = findViewById(R.id.recordWin);

        score.setText("SIZ " + scoreText + " Urinishda yutdingiz");
        record.setText("Eng yaxshi narija:  " + settings.getFirstScore());
        home.setOnClickListener(view -> {
            settings.startTouch();
            finish();
        });
    }
}