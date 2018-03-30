package co.appstorm.newsx.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class MyUtils {
    public static boolean isLollipop() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    }

    public static Reader toReaderFile(Context context, String file) {
        Reader reader = null;
        try {
            InputStream is = context.getAssets().open("jsons/" + file);
            reader = new InputStreamReader(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    /**
     * An internal method to reduce the size of the given text to a maximum. Every text longer
     * will be cut, every text short will stay the same.
     */
    public static String ellipsize(String input) {
        final int MAX = 20;
        if (input.length() <= MAX) {
            return input;
        } else {
            return input.substring(0, MAX - 1) + "…";
        }
    }
}
