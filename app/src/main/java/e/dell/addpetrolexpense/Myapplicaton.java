package e.dell.addpetrolexpense;

import android.app.Application;

import com.facebook.stetho.Stetho;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Myapplicaton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("acme_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
