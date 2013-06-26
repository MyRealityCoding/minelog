package de.myreality.minecraft.minelog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * All commands for the mine log plugin
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class MineLogCommands implements CommandExecutor {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	
	private MineLogPlugin plugin;
	
	private Map<String, String> commands = new TreeMap<String, String>();
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public MineLogCommands(MineLogPlugin plugin) {
		this.plugin = plugin;
		commands.put("level", "Set the current level [off | farmer | battle | full]");
		commands.put("info", "Get plugin information");
		commands.put("messages", "Set new message files");
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
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if (args.length == 0) {
			return help(sender);
		}
		
		String cmd = args[0];
		
		if (!commands.containsKey(cmd.toLowerCase())) {
			return help(sender);
		}
		
		try {
			Method m = getClass().getMethod(cmd, CommandSender.class, String[].class);
		
			if (!hasCommandPermission(sender, cmd)) {
				sender.sendMessage(ChatColor.RED + "You've no permission to use this command.");
				return true;
			} else {
				return (Boolean) m.invoke(this, sender, reduceArgs(args));
			}
		} catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            return help(sender);
        }
		
		return false;		
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean level(CommandSender sender, String args[]) {
		
		if (args.length > 0) {
			String name = args[0];
			
			try {
				PluginLevel level = PluginLevel.getPluginLevel(name);
				PluginConfiguration configuration = plugin.getConfiguration();
				configuration.setLevel((Player)sender, level);
				sender.sendMessage(ChatColor.GREEN + "Level has been successfully set to " + name);
			} catch (PluginLevelNotFoundException e) {
				sender.sendMessage(ChatColor.RED + "Level '" + name + "' does not exist!");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Not enough arguments!");
		}
		
		return true;
	}
	
	public boolean info(CommandSender sender, String args[]) {
		
		for (String line : plugin.PLUGIN_INFO) {
			sender.sendMessage(ChatColor.GREEN + line);
		}
		
		// Get the player
		Player player = (Player)sender;
		PluginConfiguration config = plugin.getConfiguration();
		PluginLevel currentLevel = config.getPluginLevel(player);
		sender.sendMessage(ChatColor.WHITE + "Current level: " + ChatColor.GOLD + currentLevel);
		
		return true;
	}
	
	
	
	public boolean messages(CommandSender sender, String args[]) {
		
		PluginConfiguration config = plugin.getConfiguration();
		MessageConfiguration msg = config.getMessageConfiguration();
		msg.setFiles(args, plugin);			
		sender.sendMessage(ChatColor.GREEN + "Message files have been successfully set.");		
		
		return true;		
	}
	
	private boolean help(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GREEN + "== MineLog Commands ==");
		
		for (Entry<String, String> entry : commands.entrySet()) {
			String cmd = entry.getKey();
			String info = entry.getValue();
			if (!hasCommandPermission(sender, cmd)) {
				continue;
			}
			sender.sendMessage(ChatColor.GREEN + "/minelog " + cmd + " - " + info);
		}
		
		return true;
	}
	
	private static boolean hasCommandPermission(CommandSender sender, String cmd) {
        return sender.hasPermission("minelog." + cmd);
	}
	
	private static String[] reduceArgs(String[] args) {
        if (args.length <= 1)
            return new String[0];
        
        return Arrays.copyOfRange(args, 1, args.length);
    }
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
