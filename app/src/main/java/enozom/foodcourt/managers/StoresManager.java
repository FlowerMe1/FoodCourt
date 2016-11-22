package enozom.foodcourt.managers;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import enozom.foodcourt.models.Store;

public class StoresManager {

    private static StoresManager instance;

    public static StoresManager getInstance() {
        if (instance == null)
            instance = new StoresManager();
        return instance;
    }

    private StoresManager() {

    }

    public void saveStores(List<Store> storeArrayList) {
        ActiveAndroid.beginTransaction();


        for (Store store : storeArrayList) {
            store.save();
        }

        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();
    }

    public void deleteStores() {
        new Delete().from(Store.class).execute();
    }

    public List<Store> getStoresList() {
        List<Store> storesList = new Select().from(Store.class).orderBy(Store.STORE_ID).execute();
        return storesList;
    }
}
