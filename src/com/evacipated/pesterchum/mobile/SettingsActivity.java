package com.evacipated.pesterchum.mobile;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.evacipated.pesterchum.mobile.R;
import com.evacipated.preferences.ListPreferenceMultiSelect;

public class SettingsActivity extends SherlockPreferenceActivity {
	EditTextPreference username;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        username = (EditTextPreference)findPreference("username");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username.setSummary(prefs.getString("username", "pesterClient"));
        username.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary((String)newValue);
				return true;
			}
		});
        
        ListPreferenceMultiSelect notify = (ListPreferenceMultiSelect)findPreference("notify_types");
        String[] values = notify.getValues();
		if (values == null || values.length == 0) {
			PreferenceScreen notify_settings = (PreferenceScreen)findPreference("notify_settings");
			notify_settings.setEnabled(false);
		} else {
			PreferenceScreen notify_settings = (PreferenceScreen)findPreference("notify_settings");
			notify_settings.setEnabled(true);
		}
        notify.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (((ArrayList<String>) newValue).isEmpty()) {
					PreferenceScreen notify_settings = (PreferenceScreen)findPreference("notify_settings");
					notify_settings.setEnabled(false);
				} else {
					PreferenceScreen notify_settings = (PreferenceScreen)findPreference("notify_settings");
					notify_settings.setEnabled(true);
				}
				return true;
			}
		});
        
        final ListPreference orientation = (ListPreference)findPreference("orientation_value");
        orientation.setSummary(orientation.getEntry());
        CheckBoxPreference olock = (CheckBoxPreference)findPreference("orientation_lock");
        olock.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				Boolean value = (Boolean)newValue;
				if (value) {
					Log.d("orientation", orientation.getEntry().toString());
					if (orientation.getEntry().equals("Portrait"))
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					else
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				} else {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
				}
				return true;
			}
		});
        
        orientation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String value = (String)newValue;
				preference.setSummary(value);
				if (value.equals("Portrait"))
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				else
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				return true;
			}
		});
        
        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder
			.setTitle("Low Bandwith Mode")
			.setMessage("Low Bandwidth Mode drastically reduces the amount of data used, thus increasing battery life at the cost of being unable to see when chums are online.")
			.setPositiveButton("OK", null);
        CheckBoxPreference lowbandwidth = (CheckBoxPreference)findPreference("bandwidth_low");
        lowbandwidth.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if ((Boolean)newValue) {
					AlertDialog alertDialog = alertbuilder.create();
					alertDialog.show();
				}
				
				return true;
			}
		});
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("orientation_lock", false)) {
        	if (prefs.getString("orientation_value", "Portrait").equals("Portrait")) {
        		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        	} else {
        		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        	}
        } else {
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
