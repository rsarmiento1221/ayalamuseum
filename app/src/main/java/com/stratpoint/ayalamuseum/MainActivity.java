package com.stratpoint.ayalamuseum;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.stratpoint.ayalamuseum.databinding.ActivityMainBinding;
import com.stratpoint.ayalamuseum.fragment.CameraFilterFragment;
import com.stratpoint.ayalamuseum.manager.ManagerFactory;
import com.stratpoint.ayalamuseum.manager.SharedPrefsManager;
import com.stratpoint.ayalamuseum.manager.DatabaseManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private SharedPrefsManager sharedPrefsManager;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(activityMainBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        switchFragment(new CameraFilterFragment());

        sharedPrefsManager = (SharedPrefsManager) ManagerFactory.createManager(this, ManagerFactory.TYPE_SHARED_PREFS);
        databaseManager = (DatabaseManager) ManagerFactory.createManager(this, ManagerFactory.TYPE_DATABASE);

        if (!sharedPrefsManager.hasDefaultFilters()) {
            databaseManager.insertDefaultFilters();
            sharedPrefsManager.assignDefaultFilters();
        }
    }

    /**
     * Method for changing the currently displayed fragment.
     * @param fragment instance of fragment to display.
     */
    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, fragment)
                .commit();
    }
}