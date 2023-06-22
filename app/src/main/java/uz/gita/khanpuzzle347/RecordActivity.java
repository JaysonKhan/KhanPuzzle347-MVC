package uz.gita.khanpuzzle347;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uz.gita.khanpuzzle347.modules.Settings;

public class RecordActivity extends AppCompatActivity {
    Settings settings;
    SharedPreferences sharedPreferences;
    private Button home;

    TextView attemptFirst, timeFirst, attemptSecond, timeSecond, attemptTHird, timeThird, winFirstTime;
    LinearLayout linearLayout1,linearLayout2,linearLayout3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        settings = Settings.getInstance();


        loadWidgets();
        loadView();
    }

    @SuppressLint("SetTextI18n")
    private void loadView() {
        if (sharedPreferences.getInt("rek0", Integer.MAX_VALUE)==Integer.MAX_VALUE){
            linearLayout1.setVisibility(View.INVISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
            linearLayout3.setVisibility(View.INVISIBLE);
            winFirstTime.setVisibility(View.VISIBLE);
            return;
        }else{
            winFirstTime.setVisibility(View.GONE);
        }
        attemptFirst.setText(sharedPreferences.getInt("rek0", Integer.MAX_VALUE) + "");
        timeFirst.setText(sharedPreferences.getString("time0", "00:00"));
        if (sharedPreferences.getInt("rek1", Integer.MAX_VALUE)==Integer.MAX_VALUE){
            linearLayout2.setVisibility(View.INVISIBLE);
            linearLayout3.setVisibility(View.INVISIBLE);
            return;
        }
        attemptSecond.setText(sharedPreferences.getInt("rek1", Integer.MAX_VALUE)+"");
        timeSecond.setText(sharedPreferences.getString("time1", "00:00"));
        if (sharedPreferences.getInt("rek2", Integer.MAX_VALUE)==Integer.MAX_VALUE){
            linearLayout3.setVisibility(View.INVISIBLE);
            return;
        }
        attemptTHird.setText(sharedPreferences.getInt("rek2", Integer.MAX_VALUE)+"");
        timeThird.setText(sharedPreferences.getString("time2", "00:00"));
    }

    private void loadWidgets() {
        home = findViewById(R.id.buttonHome);
        sharedPreferences = settings.getSharedPreferences();

        attemptFirst = findViewById(R.id.atemptFirst);
        attemptSecond = findViewById(R.id.atemptSecond);
        attemptTHird = findViewById(R.id.atemptThird);

        timeFirst = findViewById(R.id.timeFirst);
        timeSecond = findViewById(R.id.timeSecond);
        timeThird = findViewById(R.id.timeThird);

        linearLayout1 = findViewById(R.id.linerWin1);
        linearLayout2 = findViewById(R.id.linerWin2);
        linearLayout3 = findViewById(R.id.linerWin3);

        winFirstTime  =findViewById(R.id.winFirstTime);

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