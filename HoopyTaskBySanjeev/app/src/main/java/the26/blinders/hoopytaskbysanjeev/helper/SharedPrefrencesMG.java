package the26.blinders.hoopytaskbysanjeev.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sanjeev on 01/7/19.
 */

public class SharedPrefrencesMG {
    /**
     * PREFS_NAME is a file name which generates inside data folder of application
     */
    private static final String PREFS_NAME = "HoopyTaskBySanjeev";

    static SharedPreferences sp;
    static SharedPreferences.Editor prefEditor = null;

    private static Context mContext = null;
    public static SharedPrefrencesMG instance = null;

    public static SharedPrefrencesMG getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new SharedPrefrencesMG();
        }
        sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefEditor = sp.edit();
        return instance;
    }

    public void saveString(String key, String value) {
        prefEditor.putString(key, value);
        prefEditor.apply();
    }

    public void saveInt(String key, int value) {
        prefEditor.putInt(key, value);
        prefEditor.apply();
    }


    public String getData(String key) {
        // if name key available then it will returned value of name otherwise returned empty string.
        return sp.getString(key, "");
    }

    public int getDataInt(String key) {
        // if name key available then it will returned value of name otherwise returned empty string.
        return sp.getInt(key, 0);
    }

    public void clearData() {
        prefEditor.clear();
        prefEditor.apply();
    }
}
