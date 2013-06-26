package de.myreality.minecraft.minelog.messages;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.myreality.minecraft.minelog.MineLogPlugin;
import de.myreality.minecraft.minelog.PluginConfiguration;

/**
 * Manager for the welcome message
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since
 * @version
 */
public class WelcomeMessageManager implements Listener {

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	private MineLogPlugin plugin;
	
	private MessageParser parser;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public WelcomeMessageManager(MineLogPlugin plugin) {
		this.plugin = plugin;
		parser = new MessageParser();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PluginConfiguration config = plugin.getConfiguration();
		
		// Generate the message
		List<String> messages = config.getMessageConfiguration().loadMessages(plugin);
		
		// Show the message
		for (String message : messages) {
			if (!message.isEmpty()) {
				player.sendMessage(parser.parse(message, player));
			}
		}
	}
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
