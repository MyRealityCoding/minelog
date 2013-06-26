package de.myreality.minecraft.minelog.messages;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Parser which parses a complete messages. Additionally the parser considers
 * the last chat color which has been defined.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since
 * @version
 */
public class MessageParser {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private ChatColor lastColor = ChatColor.WHITE;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public String parse(String message, Player player) {

		int lastIndex = 0;
		message = lastColor + message;

		for (PlaceHolder p : PlaceHolder.values()) {
			message = p.parse(message, player);
		}

		for (ChatColor color : ChatColor.values()) {
			String pattern = '%' + color.name() + '%';
			message = message.replace(pattern, color.toString());

			if (message.contains(color.toString())) {
				if (message.lastIndexOf(color.toString()) > lastIndex) {
					lastIndex = message.lastIndexOf(color.toString());
					lastColor = color;
				}
			}
		}

		return message;

	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	enum PlaceHolder {

		PLAYER {
			@Override
			String parse(Player player) {
				return player.getDisplayName();
			}
		},
		SERVER {
			@Override
			String parse(Player player) {
				return player.getServer().getServerName();
			}
		},
		IP {
			@Override
			String parse(Player player) {
				return player.getServer().getIp();
			}
		},
		PORT {
			@Override
			String parse(Player player) {
				return String.valueOf(player.getServer().getPort());
			}
		},
		BUKKIT {
			@Override
			String parse(Player player) {
				return player.getServer().getBukkitVersion();
			}
		},
		LOCATION {
			@Override
			String parse(Player player) {
				Location loc = player.getLocation();
				return (int)loc.getX() + "|" + (int)loc.getY() + "|" + (int)loc.getZ();
			}
		},
		ONLINE {
			@Override
			String parse(Player player) {
				return String
						.valueOf(player.getServer().getOnlinePlayers().length);
			}
		},
		HEALTH {
			@Override
			String parse(Player player) {
				return String.valueOf(player.getHealth());
			}
		},
		EXP {
			@Override
			String parse(Player player) {
				return String.valueOf(player.getExp());
			}
		};

		public String parse(String message, Player player) {
			return message.replace(toString(), parse(player));
		}

		@Override
		public String toString() {
			return '%' + name() + '%';
		}

		abstract String parse(Player player);
	}
}
