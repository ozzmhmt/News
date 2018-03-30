package co.appstorm.newsx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class PreferenceUtils {
    private static PreferenceUtils instace;
    private SharedPreferences prefs;

    private static final String SHOW_INTRODUCE = "introduce";
    private static final String SHOW_SELECT_CATEGORY = "select_category";
    private static final String SHOW_INTERSTITIALADS = "show_interstitialads";

    private PreferenceUtils(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceUtils getInstace(Context context) {
        if (instace == null)
            instace = new PreferenceUtils(context);
        return instace;
    }

    private SharedPreferences getPrefs() {
        return prefs;
    }

    public void putShowIntroduce(boolean isShow) {
        getPrefs().edit().putBoolean(PreferenceUtils.SHOW_INTRODUCE, isShow).apply();
    }

    public boolean getShowIntroduce() {
        return getPrefs().getBoolean(PreferenceUtils.SHOW_INTRODUCE, false);
    }

    public void putShowSelectCategory(boolean isShow) {
        getPrefs().edit().putBoolean(PreferenceUtils.SHOW_SELECT_CATEGORY, isShow).apply();
    }

    public boolean getShowSelectCategory() {
        return getPrefs().getBoolean(PreferenceUtils.SHOW_SELECT_CATEGORY, false);
    }

    public void putShowInterstitialAds(int count) {
        getPrefs().edit().putInt(PreferenceUtils.SHOW_INTERSTITIALADS, count).apply();
    }

    public int getShowInterstitialAds() {
        return getPrefs().getInt(PreferenceUtils.SHOW_INTERSTITIALADS, 1);
    }

    public void putCategorySelected(String cateId) {
        getPrefs().edit().putString("cateId" + cateId, String.valueOf(cateId)).apply();
    }

    public String getCategorySelected(String cateId) {
        return String.valueOf(getPrefs().getAll().get("cateId"+cateId));
        //return getPrefs().getString("cateId" + cateId, "");
    }

    public void removeCategorySelected(String cateId) {
        getPrefs().edit().remove("cateId" + cateId).apply();
    }

    public void putFavorite(String cateId, String articleId) {
        getPrefs().edit().putString("articleId" + cateId + "-" + articleId, cateId + "-" + articleId).apply();
    }

    public String getFavorite(String cateId, String articleId) {
        return getPrefs().getString("articleId" + cateId + "-" + articleId, "");
    }

    public void removeFavorite(String cateId, String articleId) {
        getPrefs().edit().remove("articleId" + cateId + "-" + articleId).apply();
    }

    public void putRecentlyView(String cateId, String articleId) {
        getPrefs().edit().putString("recent_articleId" + cateId + "-" + articleId, cateId + "-" + articleId).apply();
    }


    public String getRecentlyView(String cateId, String articleId) {
        return getPrefs().getString("recent_articleId" + cateId + "-" + articleId, "");
    }

    public void removeRecentlyView(String cateId, String articleId) {
        getPrefs().edit().remove("recent_articleId" + cateId + "-" + articleId).apply();
    }
}
