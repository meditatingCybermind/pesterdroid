package com.distantsphere.pesterchum.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserProfile {
	private SharedPreferences userprefs;
	private SharedPreferences prefs;
	
	private PesterProfile chat;
	
	public UserProfile(Activity activity, String user) {
		prefs = activity.getPreferences(Context.MODE_PRIVATE);
		userprefs = activity.getSharedPreferences(user, Context.MODE_PRIVATE);
	}
}
