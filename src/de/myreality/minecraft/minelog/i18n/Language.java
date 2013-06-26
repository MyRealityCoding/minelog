package de.myreality.minecraft.minelog.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Language implementation of the plugin
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Language {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static ResourceBundle bundle;
	
	public static final String PATH = "i18n/messages";
	
	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	static {
		bundle = ResourceBundle.getBundle(PATH);
	}
	
	private Language() { }
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static String getMessage(String key) {
		return bundle.getString(key);
	}
	
	public static void reload(Locale locale) {
        bundle = ResourceBundle.getBundle(PATH, locale);
    }
	
	public static boolean containsKey(String key) {
		return bundle.containsKey(key);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
