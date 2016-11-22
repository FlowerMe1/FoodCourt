package enozom.foodcourt.data;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import enozom.foodcourt.models.Store;

public class FoodCourtApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initActiveAndroid();
    }

    private void initActiveAndroid() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(Store.class);
        ActiveAndroid.initialize(configurationBuilder.create());
    }

}
