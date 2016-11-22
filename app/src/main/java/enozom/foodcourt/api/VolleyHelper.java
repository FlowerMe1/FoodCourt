package enozom.foodcourt.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;

import enozom.foodcourt.R;
import enozom.foodcourt.api.interfaces.ClientCallback;

public abstract class VolleyHelper {

    protected RequestQueue mRequestQueue;
    private Context context;

    protected VolleyHelper(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    protected void scheduleJsonObjectRequest(int request, String requestUrl, final ClientCallback clientCallback, final Class<?> classType) {//<?>
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(request, requestUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseJsonArray) {
                clientCallback.onServerResultSuccess(responseJsonArray);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyServerError(error, clientCallback);
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

    public static GsonBuilder customizeGsonBuilder() {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT,
                        Modifier.STATIC);
    }

    private void volleyServerError(VolleyError volleyError, ClientCallback clientCallback) {
        String message = context.getResources().getString(R.string.something_wrong);

        if (volleyError instanceof NetworkError) {
            message = context.getString(R.string.cannot_connect_network);
        } else if (volleyError instanceof ServerError) {
            message = context.getString(R.string.server_not_found);
        } else if (volleyError instanceof AuthFailureError) {
            message = context.getString(R.string.cannot_connect_network);
        } else if (volleyError instanceof ParseError) {
            message = context.getString(R.string.parsing_error);
        } else if (volleyError instanceof NoConnectionError) {
            message = context.getString(R.string.cannot_connect_network);
        } else if (volleyError instanceof TimeoutError) {
            message = context.getString(R.string.connection_timeout);
        } else if (volleyError != null && volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            try {
                message = new String(volleyError.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        clientCallback.onServerResultFailure(message);
    }
}
