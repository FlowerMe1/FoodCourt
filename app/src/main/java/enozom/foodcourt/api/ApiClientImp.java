package enozom.foodcourt.api;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONArray;

import enozom.foodcourt.R;
import enozom.foodcourt.api.interfaces.ApiClient;
import enozom.foodcourt.api.interfaces.ClientCallback;

public class ApiClientImp extends VolleyHelper implements ApiClient {

    private static ApiClientImp instance;

    private String baseUrl;

    public static ApiClientImp getInstance(Context context) {
        if (instance == null) {
            instance = new ApiClientImp(context);
        }
        return instance;
    }

    private ApiClientImp(Context context) {
        super(context);
        /*Get the base url value from strings file.*/
        baseUrl = context.getResources().getString(R.string.base_url);
    }


    @Override
    public void getStores(ClientCallback callback) {
        String requestUrl = baseUrl + GET_STORES_URL;
        scheduleJsonObjectRequest(Request.Method.GET, requestUrl, callback, JSONArray.class);
    }
}
