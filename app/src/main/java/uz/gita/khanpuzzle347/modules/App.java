package uz.gita.khanpuzzle347.modules;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        Settings.init(this);
        super.onCreate();
    }
}
