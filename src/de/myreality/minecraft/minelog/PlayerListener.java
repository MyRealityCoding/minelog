package de.myreality.minecraft.minelog;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Player listener that listens a specific player
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class PlayerListener implements Listener {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final ChatColor DEFAULT_COLOR = ChatColor.GRAY;
	private static final ChatColor DAMAGE_COLOR = ChatColor.RED;
	private static final ChatColor EXP_COLOR = ChatColor.GREEN;
	private static final ChatColor LVL_COLOR = ChatColor.GOLD;
	private static final ChatColor SPECIAL_ITEM_COLOR = ChatColor.AQUA;
	private static final ChatColor NORMAL_ITEM_COLOR = ChatColor.WHITE;
	
	// ===========================================================
	// Fields
	// ===========================================================

	private MineLogPlugin plugin;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public PlayerListener(MineLogPlugin plugin) {
		this.plugin = plugin;
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
	public void handleExperience(PlayerExpChangeEvent event) {
		
		PluginLevel level = getLevelOfPlayer(event.getPlayer());
		
		if (!level.equals(PluginLevel.OFF)) {
			sendEXPMessage(event.getPlayer(), event.getAmount());
		}
	}
	
	@EventHandler
	public void handleLevel(PlayerLevelChangeEvent event) {
		
		PluginLevel level = getLevelOfPlayer(event.getPlayer());
		
		if (level.equals(PluginLevel.BATTLE) ||
				level.equals(PluginLevel.FULL)) {
			sendLevelMessage(event.getPlayer(), event.getNewLevel());
		}
	}
	
	@EventHandler
	public void handleDamage(EntityDamageEvent event) {
		if (event.getEntityType().equals(EntityType.PLAYER)) {
			
			Player player = (Player) event.getEntity();
			PluginLevel level = getLevelOfPlayer(player);
		
			if (level == PluginLevel.BATTLE || level == PluginLevel.FULL) {	
					sendDamageMessage(player, event.getDamage());
			}		
		}
	}
	
	@EventHandler
	public void handleItems(PlayerPickupItemEvent event) {
		
		PluginLevel level = getLevelOfPlayer(event.getPlayer());
		
		if (level.equals(PluginLevel.FARMER) ||
			level.equals(PluginLevel.FULL)) {
			Item item = event.getItem();
			int itemAmount = item.getItemStack().getAmount();			
			
			sendItemMessage(event.getPlayer(), getItemDisplayName(item), itemAmount);
		}
	}
	
	
	private String getItemDisplayName(Item item) {
		ItemMeta meta = item.getItemStack().getItemMeta();
		String itemName = "";
		
		if (meta.hasDisplayName()) {
			itemName = meta.getDisplayName();
		} else {
			itemName = item.getItemStack().getType().toString().toLowerCase();
		}
		
		itemName = '[' + itemName.replace('_', ' ') + ']';
		
		// Item is enchanted -> Make it blue
		if (!item.getItemStack().getEnchantments().isEmpty()) {
			itemName = SPECIAL_ITEM_COLOR + itemName;
		} else {
			itemName = NORMAL_ITEM_COLOR + itemName;
		}
		
		return itemName;
	}
	
	private void sendItemMessage(Player player, String item, int amount) {
		sendMessage(player, DEFAULT_COLOR, "You receive " + amount + "x " + item + "");
	}
	
	private void sendDamageMessage(Player player, int damage) {
		sendMessage(player, DAMAGE_COLOR, "You suffer " + damage + " damage");
	}
	
	private void sendLevelMessage(Player player, int level) {
		sendMessage(player, LVL_COLOR, "You reach level " + level + "!");
	}
	
	private void sendEXPMessage(Player player, int exp) {
		sendMessage(player, EXP_COLOR, "You gain " + exp + " XP");
	}
	
	private void sendMessage(Player player, ChatColor color, String message) {
		player.sendMessage(color + message);
	}
	
	private PluginLevel getLevelOfPlayer(Player player) {
		return plugin.getConfiguration().getPluginLevel(player);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
