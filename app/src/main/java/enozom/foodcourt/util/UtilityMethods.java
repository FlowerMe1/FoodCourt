package enozom.foodcourt.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import enozom.foodcourt.R;

public class UtilityMethods {

    public static Snackbar showSnackbar(final String snackbarMessage, View contentView, Context context) {
        Snackbar snackbar = getSnackbar(snackbarMessage, contentView, context, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }

    public static Snackbar getSnackbar(final String snackbarMessage, View contentView, Context context, int length) {
        Snackbar snackbar = Snackbar.make(contentView, snackbarMessage, length);
        styleSnackbar(snackbar, context);
        return snackbar;
    }

    private static void styleSnackbar(Snackbar snackbar, Context context) {
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ActivityCompat.getColor(context, R.color.colorPrimary));
        snackbar.setActionTextColor(ActivityCompat.getColor(context, android.R.color.white));
        TextView actionTextView = (TextView) group.findViewById(R.id.snackbar_action);
        actionTextView.setTextSize(16);
        actionTextView.setTypeface(null, Typeface.BOLD);
        TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
    }

    public static int getBitmapWidth(Bitmap source, int targetHeight) {
        double aspectRatio = (double) source.getWidth() / (double) source.getHeight();
        int targetWidth = (int) (targetHeight * aspectRatio);
        return targetWidth;
    }

    public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {
        if (bitmapToScale == null)
            return null;
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / width, newHeight / height);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
        return scaledBitmap;
    }
}
