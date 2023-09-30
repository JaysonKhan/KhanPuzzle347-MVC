package uz.gita.khanpuzzle347;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.khanpuzzle347.modules.Settings;
import uz.gita.khanpuzzle347.modules.SpaceFreeBox;

public class MainActivity extends AppCompatActivity {
    private long catchTime;
    private TextView textScore;
    private Chronometer textTime;
    private Button[][] boxes;
    private List<Integer> numbers;
    private int[][] numMatrix;
    private SpaceFreeBox freeSpace;
    private int score;
    private TextView rekord;
    private int rekordInt;
    private boolean isStart = false;
    private ImageView volumeButton;
    private Settings settings;
    private MediaPlayer click;
    AlertDialog back;


    private void installClickMethods() {

        findViewById(R.id.btn_restart).setOnClickListener(v -> {
            settings.startTouch();
            restart();
        });
        findViewById(R.id.btn_finish).setOnClickListener(v -> {
            settings.startTouch();
            finish();
        });

        final ViewGroup group = findViewById(R.id.kontener); // Layout
        final int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            final View item = group.getChildAt(i);
            Button box = (Button) item;
            final int x = i / 4;
            final int y = i % 4;
            box.setOnClickListener(view -> onBoxClicker(box, x, y));
            boxes[x][y] = box;
        }

        volumeButton.setOnClickListener(view -> {
            settings.startTouch();
            if (settings.isMusicOn()) {
                volumeButton.setImageResource(R.drawable.volumeoff);
                settings.pauseMusic();

            } else {
                volumeButton.setImageResource(R.drawable.volume);
                settings.startMusic();
            }
        });
    }

    private void onBoxClicker(Button box, int x, int y) {
        final int dx = Math.abs(freeSpace.x - x);
        final int dy = Math.abs(freeSpace.y - y);
        if (dx + dy == 1) {
            click.start();
            if (!isStart) {
                catchTime = SystemClock.elapsedRealtime();
                textTime.setBase( SystemClock.elapsedRealtime() - settings.getTimerBase());
                textTime.start();
                isStart = true;
            }
            textScore.setText(String.valueOf(++score));
            final String text = box.getText().toString();
            box.setBackgroundResource(R.color.free_box);
            box.setText("");

            final Button temp = boxes[freeSpace.x][freeSpace.y];
            temp.setText(text);
            temp.setBackgroundResource(R.drawable.box2);

            freeSpace = new SpaceFreeBox(x, y);

            if (isWin()) {
                if (score < rekordInt || rekordInt == 0) {
                    settings.setRekord(score, textTime.getText().toString());
                    rekordInt = settings.getFirstScore();
                    rekord.setText(rekordInt + "");

                }
                settings.setCurrentScore(textScore.getText().toString());
                startActivity(new Intent(MainActivity.this, WinActivity.class));
                restart();
                textTime.stop();
            }
        }
    }

    private boolean isWin() {
        if (freeSpace.x != 3 || freeSpace.y != 3) return false;
        for (int i = 0; i < 15; i++) {
            int x = i / 4;
            int y = i % 4;
            String text = boxes[x][y].getText().toString();
            if (!text.equals(String.valueOf(i + 1))) return false;
        }
        textTime.stop();
        return true;
    }

    private void restart() {
        textTime.setBase(SystemClock.elapsedRealtime());
        textTime.stop();
        isStart = false;
        settings.setEmptyList();
        if(settings.playingStatus()){
            boxes[freeSpace.x][freeSpace.y].setBackgroundResource(R.drawable.box2);
            score = 0;
            textScore.setText(score+"");
            settings.setTimerBase(0l);
        }
        loadData();
        dataToView();
    }

    private void dataToView() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final int index = 4 * i + j;
                if (i == freeSpace.x && j == freeSpace.y) {
                    boxes[i][j].setText("");
                    boxes[i][j].setBackgroundResource(R.color.free_box);
                } else if (numbers.get(index)==0){

                }else {
                    int num = numbers.get(index);
                    boxes[i][j].setText(String.valueOf(num));
                }
            }
        }
    }

    private void loadData() {
        numbers = new ArrayList<>();
        if (settings.playingStatus() && !settings.getList().isEmpty()) {
            String str = settings.getList();
              loadDataFromPref(str);
        } else {
            for (int i = 1; i < 16; i++) {
                numbers.add(i);
            }
            Collections.shuffle(numbers);
            while (!isSolvable(numbers)){
                Collections.shuffle(numbers);
            }
            numbers.add(0);
            isStart =false;
            freeSpace = new SpaceFreeBox(3, 3);
        }
    }

    int getInvCount(int[] arr){
    int inv_count = 0;
    for (int i = 0; i < 15; i++)
        for (int j = i + 1; j < 15; j++)
            if (arr[i] > 0 && arr[j] > 0 && arr[i] > arr[j])
                inv_count++;
    return inv_count;
}
boolean isSolvable(List<Integer> numbers){
    int[][] puzzle = toMatrix(numbers);
        int linearPuzzle[];
        linearPuzzle = new int[15];
        int k = 0;

        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++){
                if (i!=3 || j!=3){
                    linearPuzzle[k++] = puzzle[i][j];
                }
            }
        int invCount = getInvCount(linearPuzzle);

        return (invCount % 2 == 0);
    }

    private int[][] toMatrix(List<Integer> numbers) {
        int res[][] = new int[4][4];
        for (int i = 0; i < 15; i++) {
            int x = i / 4;
            int y = i % 4;
            res[x][y] = numbers.get(i);
        }
        return res;
    }

    @SuppressLint("SetTextI18n")
    private void loadWidgets() {
        click = MediaPlayer.create(this, R.raw.clicked);
        boxes = new Button[4][4];
        numMatrix = new int[4][4];

        volumeButton = findViewById(R.id.volume);

        rekord = findViewById(R.id.record);
        rekord.setText(settings.getFirstScore()+"");

        textScore = findViewById(R.id.text_score);
        textTime = findViewById(R.id.text_time);

    }

    private void saveDataToPref() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int x = i / 4;
            int y = i % 4;
            if (!boxes[x][y].getText().toString().isEmpty()){
                if(x == 3 && y == 3) {
                    sb.append(boxes[x][y].getText().toString());
                }else {
                    sb.append(boxes[x][y].getText()).append("##");
                }
            }
            else{
                sb.append(0+"##");
                settings.setOldFreeSpaces(freeSpace.x, freeSpace.y);
            }
        }
        settings.setList(sb.toString());

        catchTime = SystemClock.elapsedRealtime()-textTime.getBase();
        settings.setTimerBase(catchTime);
        settings.setTimerString(textTime.getText().toString());
        settings.setOldScore(textScore.getText().toString());
    }

    private void loadDataFromPref(String str){
        String[] savedList = str.split("##");
        for (String number : savedList) {
            numbers.add(Integer.parseInt(number));
        }
        numbers.add(0);
        String[] freeSavedSpace = settings.getFreeSpaces();
        freeSpace = new SpaceFreeBox(Integer.parseInt(freeSavedSpace[0]), Integer.parseInt(freeSavedSpace[1]));
        textTime.setBase( SystemClock.elapsedRealtime() - settings.getTimerBase());
        textTime.start();
        textScore.setText(settings.getOldScore());
        score = Integer.parseInt(settings.getOldScore());

        if (freeSpace.x!=3 || freeSpace.y!=3){
            boxes[3][3].setBackgroundResource(R.drawable.box2);
        }
        isStart = true;
    }

    @Override
    public void onBackPressed() {
        if (back==null){
            settings.startTouch();
            back =  new AlertDialog.Builder(this).setTitle("Chiqishni hohlaysanmi?")
                    .setPositiveButton("Ha", (dialogInterface, i) -> finish())
                    .setNegativeButton("Yo'q", (dialogInterface, i) -> back.dismiss())
                    .create();
            back.show();
        }else {
            back.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = Settings.getInstance();

        loadWidgets();
        installClickMethods();
        loadData();
        dataToView();
    }

    @Override
    protected void onPause() {
        settings.pauseMusicO();
        if (!isWin()) {
            saveDataToPref();
            settings.setPlayingStatus(true);
        } else {
            settings.setPlayingStatus(false);
            restart();
        }
        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textTime.start();
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveDataToPref();
    }

    @Override
    protected void onStart() {
        if (settings.isMusicOn()){
            settings.startMusic();
            volumeButton.setImageResource(R.drawable.volume);
        }else{
            volumeButton.setImageResource(R.drawable.volumeoff);
        }
        super.onStart();
    }
    @Override
    protected void onResume() {
        if (settings.isMusicOn()){
            settings.startMusic();
            volumeButton.setImageResource(R.drawable.volume);
        }else{
            volumeButton.setImageResource(R.drawable.volumeoff);
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

}
