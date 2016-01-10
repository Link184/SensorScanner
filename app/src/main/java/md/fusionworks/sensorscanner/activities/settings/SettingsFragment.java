package md.fusionworks.sensorscanner.activities.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import md.fusionworks.sensorscanner.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private ListPreference listX;
    private ListPreference listY;
    private SwitchPreference chechBoxX;
    private SwitchPreference chechBoxY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        listX = (ListPreference) getPreferenceManager().findPreference("listOfXRange");
        listY = (ListPreference) getPreferenceManager().findPreference("listOfYRange");
        chechBoxX = (SwitchPreference) getPreferenceManager().findPreference("rangeXCheckBox");
        chechBoxY = (SwitchPreference) getPreferenceManager().findPreference("rangeYCheckBox");

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!chechBoxX.isChecked()){
            listX.setValue("3");
        }
        if (!chechBoxY.isChecked()) {
            listY.setValue("3");
        }
    }
}
