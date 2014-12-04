package org.wordpress.android.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class AutoSaveTextHelper {
    public static final String PREFERENCES_PREFIX = "AutoSaveTextHelperPrefs";
    private String mPreferencesName = PREFERENCES_PREFIX;
    private String mUniqueId;

    public void setUniqueId(String uniqueId) {
        mUniqueId = uniqueId;
    }

    public String getUniqueId() {
        return mUniqueId;
    }

    private String getViewPathId(View view) {
        StringBuilder sb = new StringBuilder();
        for (View currentView = view; currentView != null && currentView.getParent() != null
                && currentView.getParent() instanceof View; currentView = (View) currentView.getParent()) {
            sb.append(currentView.getId());
        }
        return sb.toString();
    }

    public void setGroupIdentifier(String groupIdentifier) {
        mPreferencesName = PREFERENCES_PREFIX + groupIdentifier;
    }

    public void clearSavedText(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(mPreferencesName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getViewPathId(view) + mUniqueId);
        editor.apply();
    }

    public String loadString(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(mPreferencesName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(getViewPathId(view) + mUniqueId, "");
    }

    public void saveString(View view, String text) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(mPreferencesName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getViewPathId(view) + mUniqueId, text);
        editor.apply();
    }

    public static void clearGroup(Context context, String preferenceName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_PREFIX + preferenceName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
