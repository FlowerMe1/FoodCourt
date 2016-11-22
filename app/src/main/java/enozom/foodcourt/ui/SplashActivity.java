package enozom.foodcourt.ui;

import android.os.Bundle;
import android.os.Handler;

import enozom.foodcourt.R;

public class SplashActivity extends BaseActivity implements Runnable {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void run() {
        navigateActivity(StoresActivity.class);
        finish();
    }
}
