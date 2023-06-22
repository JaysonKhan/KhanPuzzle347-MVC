package uz.gita.khanpuzzle347;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import uz.gita.khanpuzzle347.modules.Settings;

public class HomeActivity extends AppCompatActivity {
    private Button start;
    private Button chempiones;
    private ImageView musicButton;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        settings = Settings.getInstance();

        settings.setMusic(this);
        loadView();

    }
    private void loadView() {
        start = findViewById(R.id.startGame);
        chempiones = findViewById(R.id.fearless);
        musicButton = findViewById(R.id.volumeButton);
        setMusicStatus();

        musicButton.setOnClickListener(view -> {
            settings.startTouch();
            if (settings.isMusicOn()){
                musicButton.setImageResource(R.drawable.volumeoff);
                settings.pauseMusic();
            }else{
                musicButton.setImageResource(R.drawable.volume);
                settings.startMusic();
            }
        });


        findViewById(R.id.infoButton).setOnClickListener(view -> {
            settings.startTouch();
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });;
        start.setOnClickListener(view -> {
            settings.startTouch();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });
        chempiones.setOnClickListener(view -> {
            settings.startTouch();
            startActivity(new Intent(HomeActivity.this, RecordActivity.class));
        });

    }

    private void setMusicStatus() {
        if(settings.isMusicOn()){
            musicButton.setImageResource(R.drawable.volume);
            settings.startMusic();
        } else {
            musicButton.setImageResource(R.drawable.volumeoff);
        }
    }

    private void setMusicStatus(boolean status) {
        if(status){
            musicButton.setImageResource(R.drawable.volume);
            settings.startMusic();
        } else {
            musicButton.setImageResource(R.drawable.volumeoff);
        }
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean musicStatus = savedInstanceState.getBoolean("MUSIC_STATUS", true);
        setMusicStatus(musicStatus);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("MUSIC_STATUS", settings.isMusicOn());
    }

    @Override
    protected void onPause() {
        settings.pauseMusicO();
        super.onPause();
    }

    @Override
    protected void onStart() {
        if (settings.isMusicOn()){
            settings.startMusic();
            musicButton.setImageResource(R.drawable.volume);
        }else{
            musicButton.setImageResource(R.drawable.volumeoff);
        }
        super.onStart();
    }
    @Override
    protected void onDestroy() {
        settings.releaseMusic();
        super.onDestroy();
    }
}