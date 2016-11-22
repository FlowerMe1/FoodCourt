package enozom.foodcourt.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;

import enozom.foodcourt.api.ApiClientImp;
import enozom.foodcourt.api.interfaces.ApiClient;

public class BaseActivity extends AppCompatActivity {

    protected ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiClient = ApiClientImp.getInstance(this);
    }

    protected void navigateActivity(Class activityClass) {
        Intent navigateIntent = new Intent(this, activityClass);
        startActivityWithTransition(this, navigateIntent);
    }

    /* start - finish activity .. lollipop transitions*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void startActivityWithTransition(Activity activity, Intent intent) {
        ActivityOptionsCompat options = null;
        try {
            options = getOptionsCompat(activity);
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private ActivityOptionsCompat getOptionsCompat(Activity activity) {
        ActivityOptionsCompat options = null;

        try {
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        applyTransition(activity);
        return options;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Activity applyTransition(Activity activity) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = new Slide();
            ((Slide) transition).setSlideEdge(Gravity.RIGHT);
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(android.R.id.navigationBarBackground, true);
            activity.getWindow().setEnterTransition(transition);
        }
        return activity;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void finishActivity(Activity activity) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAfterTransition();
        } else {
            activity.finish();
        }
    }

    protected void initToolbar(Toolbar toolbar, boolean isShowTitle) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
        }
    }

    protected void setToolBarCustomView(int toolbarLayout) {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(toolbarLayout);
    }
}
