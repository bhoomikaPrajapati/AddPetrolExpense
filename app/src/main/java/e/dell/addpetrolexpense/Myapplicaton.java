package e.dell.addpetrolexpense;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class Myapplicaton extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
