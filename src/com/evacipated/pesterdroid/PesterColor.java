package com.evacipated.pesterdroid;

import java.util.Vector;

import android.graphics.Color;

public class PesterColor {
	private int color;
	
	public PesterColor() {
		
	}

	public PesterColor(int color) {
		this.color = color;
	}
	
	public PesterColor(String colorString) {
		this.color = Color.parseColor(colorString);
	}
	
	public PesterColor(int red, int green, int blue) {
		this(red, green, blue, 255);
	}
	
	public PesterColor(int red, int green, int blue, int alpha) {
		this.color = Color.argb(alpha, red, green, blue);
	}
	
	public Vector<Integer> getRgba() {
		Vector<Integer> rgba = new Vector<Integer>(4);
		
		rgba.set(0, Color.red(color));
		rgba.set(1, Color.green(color));
		rgba.set(2, Color.blue(color));
		rgba.set(3, Color.alpha(color));
		
		return rgba;
	}
	
	public String getName() {
		return String.format("#%1$02X%2$02X%3$02X", Color.red(color), Color.green(color), Color.blue(color));
	}
}
