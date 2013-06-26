package de.myreality.minecraft.minelog;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.myreality.minecraft.minelog.messages.WelcomeMessageManager;

/**
 * Main plugin class to provide plugin features
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class MineLogPlugin extends JavaPlugin implements Listener {

	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final String VERSION = "%VER%";
	
	public final String[] PLUGIN_INFO = {
		"=================== MineLog ====================",
		"Current version: " + VERSION,
		"written by Miguel Gonzalez",
		"You can find the latest MineLog version here:",
		"http://dev.bukkit.org/server-mods/minelog-plugin",
		"================================================"		
	};
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private PluginConfiguration configuration;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public PluginConfiguration getConfiguration() {
		return configuration;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		super.onDisable();
		reloadConfiguration();
		if (configuration != null) {
			configuration.save(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		configuration = new PluginConfiguration();
		reloadConfiguration();
		configuration.load(this);
		loadInfo();
		showPluginInfo(getLogger());
		this.getCommand("minelog").setExecutor(new MineLogCommands(this));
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new WelcomeMessageManager(this), this);
		getServer().getPluginManager().registerEvents(this, this);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void showPluginInfo(Logger logger) {
		for (String line : PLUGIN_INFO) {
			logger.info(line);
		}
	}
	
	private void loadInfo() {
		for (int i = 0; i < PLUGIN_INFO.length; ++i) {
			PLUGIN_INFO[i] = PLUGIN_INFO[i].replace(VERSION, this.getDescription().getVersion());
		}
	}
	
	/**
	 * Reloads all configuration files
	 */
	public void reloadConfiguration() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		reloadConfiguration();
		if (configuration != null) {
			configuration.save(this);
		}
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
