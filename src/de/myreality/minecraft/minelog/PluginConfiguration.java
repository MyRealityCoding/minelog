package de.myreality.minecraft.minelog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin configuration file
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since
 * @version
 */
public class PluginConfiguration implements Serializable {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final String FILE = "config.dat";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ===========================================================
	// Fields
	// ===========================================================

	private Map<String, PlayerConfiguration> entries;
	private MessageConfiguration messageConfiguration;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PluginConfiguration() {
		entries = new HashMap<String, PlayerConfiguration>();
		messageConfiguration = new MessageConfiguration();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setLevel(Player player, PluginLevel level) {

		String playerName = player.getName();

		if (!entries.containsKey(playerName)) {
			PlayerConfiguration configuration = new PlayerConfiguration();
			configuration.setLevel(level);
			entries.put(playerName, configuration);
		} else {
			PlayerConfiguration config = entries.get(playerName);
			config.setLevel(level);
		}
	}
	
	public MessageConfiguration getMessageConfiguration() {
		return messageConfiguration;
	}

	public PluginLevel getPluginLevel(Player player) {
		PlayerConfiguration configuration = entries.get(player.getName());

		if (configuration != null) {
			return configuration.getLevel();
		} else {
			return PluginLevel.FULL;
		}
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void save(JavaPlugin plugin) {
		File file = new File(plugin.getDataFolder(), FILE);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileOutputStream fileStream = null;
		ObjectOutputStream objectStream = null;

		try {
			fileStream = new FileOutputStream(file);
			objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileStream != null) {
					fileStream.close();
				}

				if (objectStream != null) {
					objectStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void load(JavaPlugin plugin) {
		File file = new File(plugin.getDataFolder(), FILE);

		if (file.exists()) {
			FileInputStream fileStream = null;
			ObjectInputStream objectStream = null;

			try {
				fileStream = new FileInputStream(file);
				objectStream = new ObjectInputStream(fileStream);
				PluginConfiguration config = (PluginConfiguration) objectStream
						.readObject();
				entries = config.entries;
				messageConfiguration = config.messageConfiguration;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fileStream != null) {
						fileStream.close();
					}

					if (objectStream != null) {
						objectStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
