package br.com.zynger.recyclernotifier;

import android.content.res.TypedArray;
import android.view.View;

public class RecyclerNotifierUtil {

    public static int visibilityFromString(TypedArray array, int index, int defaultValue) {
        String value = array.getString(index);

        if (value == null) {
            return defaultValue;
        } else {
            if (value.equalsIgnoreCase("gone")) {
                return View.GONE;
            } else if (value.equalsIgnoreCase("invisible")) {
                return View.INVISIBLE;
            } else if (value.equalsIgnoreCase("visible")) {
                return View.VISIBLE;
            } else {
                return defaultValue;
            }
        }
    }
}
