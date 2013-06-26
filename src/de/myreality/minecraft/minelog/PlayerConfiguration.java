package de.myreality.minecraft.minelog;

import java.io.Serializable;

/**
 * Simple configuration file
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class PlayerConfiguration implements Serializable {
	// ===========================================================
	// Constants
	// ===========================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ===========================================================
	// Fields
	// ===========================================================

	private PluginLevel level;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PlayerConfiguration() {
		level = PluginLevel.FULL;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * @return the level
	 */
	public PluginLevel getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(PluginLevel level) {
		this.level = level;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
