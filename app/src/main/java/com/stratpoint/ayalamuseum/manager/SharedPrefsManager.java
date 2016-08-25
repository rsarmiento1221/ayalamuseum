package com.stratpoint.ayalamuseum.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.common.base.Optional;

/**
 * Created by raymondsarmiento on 8/12/16.
 */
public class SharedPrefsManager extends Manager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefsManager sharedPrefsManager;

    private static final String HAS_DEFAULT_FILTER_KEY = "has_default_filter";

    SharedPrefsManager(Context context) {
        super(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private SharedPrefsManager insert(String key, String value) {

        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putString(key, value);

        return this;
    }

    private SharedPrefsManager insert(String key, boolean value) {

        if (editor == null) {
            editor = sharedPreferences.edit();
        }

        editor.putBoolean(key, value);

        return this;
    }

    private String getString(String key) {

        return sharedPreferences.getString(key, null);
    }

    private Optional<Boolean> getBoolean(String key) {

        if (sharedPreferences.contains(key)) {
            return Optional.of(sharedPreferences.getBoolean(key, false));
        }

        return Optional.absent();
    }

    private void commit() {
        if (editor == null) {
            throw new NullPointerException("Editor is not instantiated.");
        }

        editor.commit();
    }

    public boolean hasDefaultFilters() {
        Optional<Boolean> hasDefaultFilters = getBoolean(HAS_DEFAULT_FILTER_KEY);

        if (hasDefaultFilters.isPresent()) {
            return hasDefaultFilters.get();
        }

        return false;
    }

    public void assignDefaultFilters() {
        insert(HAS_DEFAULT_FILTER_KEY, true).commit();
    }
}
