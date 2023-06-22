package uz.gita.khanpuzzle347.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import uz.gita.khanpuzzle347.R;

public class Settings {
    private static Context context;
    private static Settings settings;
    private final String FILE_NAME = "SETTINGS";
    private final String MUSIC = "MUSIC";
    private MediaPlayer music;
    private MediaPlayer touch;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private Settings(Context context){
        sharedPreferences = Settings.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public static Settings getInstance(){
        return settings;
    }

    public static Context getContext(){
        return context;
    }

    public void setMusic(Context context) {
        this.music = MediaPlayer.create(context, R.raw.music);
        touch = MediaPlayer.create(context, R.raw.touch);
        music.setLooping(true);
    }

    public static void init(Context controlContext){
        if (settings == null) {
            context = controlContext;
            settings = new Settings(controlContext);
        }
    }
    public MediaPlayer getMusic(){
        if (music==null){
            music = MediaPlayer.create(Settings.getContext(), R.raw.music);
        }
        return music;
    }

    public void setRekord(int score, String time){
        int first = sharedPreferences.getInt("rek0", Integer.MAX_VALUE);
        int second = sharedPreferences.getInt("rek1", Integer.MAX_VALUE);
        int third = sharedPreferences.getInt("rek2", Integer.MAX_VALUE);

        if(score <= first) {
            sharedPreferences.edit().putInt("rek1", first).apply();
            sharedPreferences.edit().putString("time1", sharedPreferences.getString("time0", "00:00")).apply();
            sharedPreferences.edit().putInt("rek2", second).apply();
            sharedPreferences.edit().putString("time2", sharedPreferences.getString("time1", "00:00")).apply();
            sharedPreferences.edit().putInt("rek0", score).apply();
            sharedPreferences.edit().putString("time0", time).apply();
        }else if(score <= second) {
            sharedPreferences.edit().putInt("rek2", second).apply();
            sharedPreferences.edit().putString("time2", sharedPreferences.getString("time1", "00:00")).apply();
            sharedPreferences.edit().putInt("rek1", score).apply();
            sharedPreferences.edit().putString("time1", time).apply();
        } else if (score<=third){
            sharedPreferences.edit().putInt("rek2", score).apply();
            sharedPreferences.edit().putString("time2", time).apply();
        }
    }


    public boolean isMusicOn() {
        return sharedPreferences.getBoolean("MUSIC", true);
    }

    public void startMusic() {
        music.start();
        editor.putBoolean(MUSIC, true).apply();
    }
    public void startTouch() {
        touch.start();
    }

    public void pauseMusic() {
        music.pause();
        editor.putBoolean(MUSIC, false).apply();
    }
    public void pauseMusicO() {
        music.pause();
    }

    public void releaseMusic() {
        music.stop();
        music.release();
        touch.release();
        editor.putBoolean(MUSIC, false).apply();
        touch= null;
        music = null;
    }
    public void setTimerBase(Long base){
        editor.putLong("oldTime", base).apply();
    }
    public void setTimerString(String time){
        editor.putString("oldTimeString", time).apply();
    }
    public Long getTimerBase(){
        return sharedPreferences.getLong("oldTime", 0);
    }
    public int getFirstScore(){
        return sharedPreferences.getInt("rek0", 999);
    }
    public void setCurrentScore(String  score){
        editor.putString("SCORE", score ).apply();
    }
    public String  getCurrentScore(){
        return sharedPreferences.getString("SCORE", "0");
    }
    public void setEmptyList(){
        editor.putString("LIST", "").apply();

    }
    public boolean playingStatus(){
        return sharedPreferences.getBoolean("isPlaying", false);
    }
    public void setPlayingStatus(Boolean bool){
        editor.putBoolean("isPlaying", bool).apply();
    }
    public void setList(String s){
        editor.putString("LIST", s).apply();
    }
    public String getList(){
        return sharedPreferences.getString("LIST", "");
    }
    public void setOldFreeSpaces(int x, int y){
        editor.putString("EMPTY_CELL", x + "##" + y).apply();
    }
    public void setOldScore(String score){
        editor.putString("oldScore", score).apply();
    }

    public String  getOldScore(){
       return sharedPreferences.getString("oldScore", "0");
    }
    public String [] getFreeSpaces(){
        return sharedPreferences.getString("EMPTY_CELL", "3##3").split("##");
    }
}
