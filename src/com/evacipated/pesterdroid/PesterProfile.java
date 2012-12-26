package com.evacipated.pesterdroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import android.util.Pair;

public class PesterProfile {
	private String handle;
	private PesterColor color;
	private String mood; // Needs to be changed to Mood class later

	private String initials;

	public PesterProfile() {
		this.handle = new String();
		this.color = new PesterColor();
		this.mood = new String();
	}

	// To be called whenever handle changes
	private void updateInitials() {
		initials = PesterProfile.initials(handle);
	}

	public String getInitials() {
		return initials;
	}

	public void setHandle(String handle) {
		this.handle = handle;
		updateInitials();
	}

	public String getHandle() {
		return handle;
	}

	public void setColor(PesterColor color) {
		this.color = color;
	}

	public PesterColor getColor() {
		return color;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getMood() {
		return mood;
	}

	public String getColorHtml() {
		return this.color.getName();
	}

	public String getColorCmd() {
		Vector<Integer> rgba = this.color.getRgba();
		return String.format("%1$d%2$d%3$d", rgba.get(0), rgba.get(1), rgba.get(2));
	}

	public class Builder {
		private PesterProfile profile;

		public Builder() {
			this.profile = new PesterProfile();
		}

		public Builder setHandle(String handle) {
			profile.setHandle(handle);
			return this;
		}

		public Builder setColor(PesterColor color) {
			profile.setColor(color);
			return this;
		}

		public Builder setMood(String mood) {
			profile.setMood(mood);
			return this;
		}

		public PesterProfile build() {
			return profile;
		}
	}

	public static boolean checkLength(String handle) {
		return handle.length() <= 256;
	}

	public static Pair<Boolean, String> checkValid(String handle) {
		List<Character> caps = new ArrayList<Character>();
		for (int i = 0; i < handle.length(); ++i) {
			char c = handle.charAt(i);
			if (Character.isUpperCase(c))
				caps.add(c);
		}

		if (caps.size() != 1)
			return Pair.create(false, "Must have exactly 1 uppercase letter");
		if (Character.isUpperCase(handle.charAt(0)))
			return Pair.create(false, "Cannot start with uppercase letter");
		if (Pattern.matches("[^A-Za-z0-9]", handle))
			return Pair.create(false, "Only alphanumberic characters allowed");
		return Pair.create(true, "");
	}

	public static String initials(String handle) {
		List<Character> caps = new ArrayList<Character>();
		for (int i = 0; i < handle.length(); ++i) {
			char c = handle.charAt(i);
			if (Character.isUpperCase(c))
				caps.add(c);
		}

		String initials;
		if (caps.size() == 0)
			initials = String.valueOf(handle.charAt(0));
		else
			initials = String.valueOf(handle.charAt(0)) + caps.get(0);
		return initials.toUpperCase();
	}
}
