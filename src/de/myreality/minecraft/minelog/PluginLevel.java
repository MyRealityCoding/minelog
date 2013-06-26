package de.myreality.minecraft.minelog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * PluginLevel of the plugin
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public enum PluginLevel implements Serializable {

	// ===========================================================
	// Constants
	// ===========================================================

	OFF("off"), FARMER("farmer"), BATTLE("battle"), FULL("full");

	// ===========================================================
	// Fields
	// ===========================================================

	private String levelString;
	
	// Preload levels for performance issues
	private static Map<String, PluginLevel> cachedLevels;

	// ===========================================================
	// Constructors
	// ===========================================================

	static {
		cachedLevels = new HashMap<String, PluginLevel>();
		
		for (PluginLevel level : values()) {
			cachedLevels.put(level.toString().toLowerCase(), level);
		}
	}
	
	PluginLevel(String levelString) {
		this.levelString = levelString;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return levelString;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	public static PluginLevel getPluginLevel(String key) throws PluginLevelNotFoundException {
		
		PluginLevel level = cachedLevels.get(key.toLowerCase());
		
		if (level != null) {
			return level;
		} else {
			throw new PluginLevelNotFoundException(key);
		}
	}
}
