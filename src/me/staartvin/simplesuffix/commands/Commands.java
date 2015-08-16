package me.staartvin.simplesuffix.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class Commands implements TabExecutor {

	SimpleSuffix plugin;

	public Commands(SimpleSuffix instance) {
		plugin = instance;
	}

	public boolean checkCharacterLimit(String message, boolean prefix) {
		message = message.toLowerCase();
		message = message.replaceAll("&k", "").replaceAll("&l", "")
				.replaceAll("&m", "").replaceAll("&n", "").replaceAll("&o", "")
				.replaceAll("&r", "").replaceAll("&0", "").replaceAll("&1", "")
				.replaceAll("&2", "").replaceAll("&3", "").replaceAll("&4", "")
				.replaceAll("&5", "").replaceAll("&6", "").replaceAll("&7", "")
				.replaceAll("&8", "").replaceAll("&9", "").replaceAll("&a", "")
				.replaceAll("&b", "").replaceAll("&c", "").replaceAll("&d", "")
				.replaceAll("&e", "").replaceAll("&f", "");

		if (prefix) {
			if (message.length() > plugin.getConfig().getInt(
					"character limit prefix", 20)) {
				return false;
			}
		} else {
			if (message.length() > plugin.getConfig().getInt(
					"character limit suffix", 20)) {
				return false;
			}
		}
		return true;
	}

	public boolean isOwnChange(CommandSender sender, String[] arguments) {

		for (String arg : arguments) {

			arg = arg.trim().toLowerCase();

			if (arg.contains("player:")) {

				String playerName = arg.substring(arg.indexOf(":") + 1).trim();

				// If target name is the same as the sender, it is still an own change.
				if (playerName.equalsIgnoreCase(sender.getName()))
					continue;
				else
					return false;
			}
		}

		return true;
	}

	public boolean isWorldSpecific(String[] arguments) {

		for (String arg : arguments) {

			arg = arg.trim().toLowerCase();

			if (arg.contains("world:")) {

				return true;
			}
		}

		return false;
	}

	public String getWorld(String[] arguments) {
		for (String arg : arguments) {

			arg = arg.trim().toLowerCase();

			if (arg.contains("world:")) {

				String worldName = arg.substring(arg.indexOf(":") + 1).trim();

				World world = plugin.getServer().getWorld(worldName);

				if (world == null)
					continue;

				return world.getName();
			}
		}

		return null;
	}

	public String getTargetName(String[] arguments) {
		for (String arg : arguments) {

			arg = arg.trim().toLowerCase();

			if (arg.contains("player:")) {

				String playerName = arg.substring(arg.indexOf(":") + 1).trim();

				return playerName;
			}
		}

		return null;
	}

	public String[] removeWorldName(String[] args) {

		// Is not world specific
		if (!this.isWorldSpecific(args))
			return args;

		List<String> arguments = new ArrayList<String>(Arrays.asList(args));

		for (int i = 0; i < args.length; i++) {
			if (args[i].toLowerCase().trim().contains("world:")) {
				arguments.remove(i);
			}
		}

		return arguments.toArray(new String[arguments.size()]);
	}

	public String[] removeTargetName(String[] args) {

		List<String> arguments = new ArrayList<String>(Arrays.asList(args));

		for (int i = 0; i < args.length; i++) {
			if (args[i].toLowerCase().trim().contains("player:")) {
				arguments.remove(i);
			}
		}

		return arguments.toArray(new String[arguments.size()]);
	}

	public boolean hasCensoredWords(String message) {
		message = message.toLowerCase();
		List<String> censoredWords = plugin.getConfigClass().getCensoredWords();

		for (String word : censoredWords) {
			if (message.contains(word))
				return true;
		}
		return false;
	}

	public boolean checkColours(String message, CommandSender sender) {
		if (message.contains("&0") || message.contains("&1")
				|| message.contains("&2") || message.contains("&3")
				|| message.contains("&4") || message.contains("&5")
				|| message.contains("&6") || message.contains("&7")
				|| message.contains("&8") || message.contains("&9")
				|| message.contains("&a") || message.contains("&b")
				|| message.contains("&c") || message.contains("&d")
				|| message.contains("&e") || message.contains("&f")) {
			
			if (sender.hasPermission("simplesuffix.colours")
					|| sender.hasPermission("simplesuffix.colors")) {
				return true;
			}
			
			sender.sendMessage(ChatColor.RED + "You may not use colours!");
			return false;
		}
		return true;
	}

	public boolean checkForFormats(String message, CommandSender sender) {
		message = message.toLowerCase();
		// Random
		if (message.contains("&k")) {
			if (!sender.hasPermission("simplesuffix.format.random")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&k'!");
				return false;
			}
		}
		// Bold
		else if (message.contains("&l")) {
			if (!sender.hasPermission("simplesuffix.format.bold")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&l'!");
				return false;
			}
		}
		// Strike
		else if (message.contains("&m")) {
			if (!sender.hasPermission("simplesuffix.format.strike")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&m'!");
				return false;
			}
		}
		// Underlined
		else if (message.contains("&n")) {
			if (!sender.hasPermission("simplesuffix.format.underlined")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&n'!");
				return false;
			}
		}
		// Italic
		else if (message.contains("&o")) {
			if (!sender.hasPermission("simplesuffix.format.italic")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&o'!");
				return false;
			}
		}
		// Reset
		else if (message.contains("&r")) {
			if (!sender.hasPermission("simplesuffix.format.reset")) {
				sender.sendMessage(ChatColor.RED + "You may not use '&r'!");
				return false;
			}
		}
		return true;
	}

	public String buildString(String[] args) {
		StringBuilder builder = new StringBuilder("");

		for (int i = 0; i < args.length; i++) {
			if (i == 0) {
				builder.append(args[i]);
			} else {
				builder.append(" " + args[i]);
			}
		}

		return builder.toString();
	}

	public boolean shouldClear(String fix) {
		fix = fix.toLowerCase();
		if (fix.contains("off") || fix.contains("clear"))
			return true;
		else
			return false;
	}

	public void setPrefix(CommandSender sender, String args[], boolean self) {
		// sender is player performing command
		// arguments are the arguments that are from the command
		// self is whether a player wants to set its own prefix or someone else's.

		plugin.getLoggerClass().debug("I pass test 1");

		// Do permission test
		if (self) {
			if (!plugin.getCommands().hasPermission(
					"simplesuffix.set.prefix.self", sender))
				return;
		} else {
			if (!plugin.getCommands().hasPermission(
					"simplesuffix.set.prefix.other", sender))
				return;
		}

		// Is sender a player?
		if (self) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only players can perform this command!");
				return;
			}
		}

		plugin.getLoggerClass().debug("I pass test 2");

		boolean isWorldSpecific = this.isWorldSpecific(args);
		String worldName = null;

		// Get the world.
		if (isWorldSpecific) {
			worldName = this.getWorld(args);

			if (worldName == null) {
				sender.sendMessage(ChatColor.RED
						+ "Invalid world: this world doesn't exist!");
				return;
			}

			// Remove world name from args
			args = this.removeWorldName(args);
		}

		Player player = null;

		// Get the player.
		if (self) {
			player = (Player) sender;
		} else {

			String targetName = this.getTargetName(args);

			if (targetName == null) {
				sender.sendMessage(ChatColor.RED
						+ "You didn't specify a player!");
				return;
			}

			// Search player
			List<Player> matchedPlayers = plugin.getServer().matchPlayer(
					targetName);

			if (matchedPlayers.size() == 0) {
				sender.sendMessage(ChatColor.RED + "Player '" + targetName
						+ "' is not online!");
				return;
			}

			if (matchedPlayers.size() > 1) {
				sender.sendMessage(ChatColor.RED
						+ "There are more than 1 players found that match your target's name. Be more precise!");
				return;
			}

			// Get the only player.
			player = matchedPlayers.get(0);
		}

		// Remove target name
		args = this.removeTargetName(args);

		// Build string from left over arguments.
		String prefix = this.buildString(args);
		
		if (prefix.trim().equals("")) {
			sender.sendMessage(ChatColor.RED + "You did not supply a prefix!");
			return;
		}

		// Check for censored words
		if (this.hasCensoredWords(prefix)
				&& !sender.hasPermission("simplesuffix.prefix.bypass.censor")) {
			sender.sendMessage(ChatColor.RED
					+ "Your prefix contains censored words. This is not allowed.");
			return;
		}

		// Check for character limit
		if (!this.checkCharacterLimit(prefix, true)) {
			sender.sendMessage(ChatColor.RED
					+ "Your prefix exceeds the character limit!");
			return;
		}

		// Check on colours
		if (!this.checkColours(prefix, sender)) {
			return;
		}

		//Check on formats
		if (!this.checkForFormats(prefix, sender)) {
			return;
		}

		boolean shouldClear = this.shouldClear(prefix);

		// Set prefix
		plugin.getVaultHandler().setPrefix(worldName, player.getName(), prefix,
				shouldClear);

		// Message player
		if (self) {
			if (!isWorldSpecific) {
				if (shouldClear) {
					// Not world specific, from yourself and will clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your global prefix is cleared!");
				} else {
					// Not world specific, from yourself and will NOT clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your global prefix is set to: " + ChatColor.GOLD
							+ prefix);
				}
			} else {
				if (shouldClear) {
					// World specific, from yourself and will clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your prefix on world " + ChatColor.DARK_AQUA
							+ worldName + ChatColor.GREEN + " is cleared!");
				} else {
					// World specific, from yourself and will NOT clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your prefix on world " + ChatColor.DARK_AQUA
							+ worldName + ChatColor.GREEN + " is set to: "
							+ ChatColor.GOLD + prefix);
				}
			}
		} else {
			if (!isWorldSpecific) {
				if (shouldClear) {
					// Not world specific, for another player and will clear
					sender.sendMessage(ChatColor.GREEN
							+ "The global prefix of " + ChatColor.YELLOW
							+ player.getName() + ChatColor.GREEN
							+ " is cleared!");
				} else {
					// Not world specific, for another player and will NOT clear
					sender.sendMessage(ChatColor.GREEN
							+ "The global prefix of " + ChatColor.YELLOW
							+ player.getName() + ChatColor.GREEN
							+ " is set to: " + ChatColor.GOLD + prefix);
				}
			} else {
				if (shouldClear) {
					// World specific, for another player and will clear
					sender.sendMessage(ChatColor.GREEN + "The prefix on world "
							+ ChatColor.DARK_AQUA + worldName + ChatColor.GREEN
							+ " of " + ChatColor.YELLOW + player.getName()
							+ ChatColor.GREEN + " is cleared!");

				} else {
					// World specific, for another player and will NOT clear
					sender.sendMessage(ChatColor.GREEN + "The prefix on world "
							+ ChatColor.DARK_AQUA + worldName + ChatColor.GREEN
							+ " of " + ChatColor.YELLOW + player.getName()
							+ ChatColor.GREEN + " is set to: " + ChatColor.GOLD
							+ prefix);
				}
			}
		}

		return;
	}

	public void setSuffix(CommandSender sender, String args[], boolean self) {
		// sender is player performing command
		// arguments are the arguments that are from the command
		// self is whether a player wants to set its own prefix or someone else's.

		plugin.getLoggerClass().debug("I pass test 1");

		// Do permission test
		if (self) {
			if (!plugin.getCommands().hasPermission(
					"simplesuffix.set.suffix.self", sender))
				return;
		} else {
			if (!plugin.getCommands().hasPermission(
					"simplesuffix.set.suffix.other", sender))
				return;
		}

		// Is sender a player?
		if (self) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only players can perform this command!");
				return;
			}
		}

		plugin.getLoggerClass().debug("I pass test 2");

		boolean isWorldSpecific = this.isWorldSpecific(args);
		String worldName = null;

		// Get the world.
		if (isWorldSpecific) {
			worldName = this.getWorld(args);

			if (worldName == null) {
				sender.sendMessage(ChatColor.RED
						+ "Invalid world: this world doesn't exist!");
				return;
			}

			// Remove world name from args
			args = this.removeWorldName(args);
		}

		Player player = null;

		// Get the player.
		if (self) {
			player = (Player) sender;
		} else {

			String targetName = this.getTargetName(args);

			if (targetName == null) {
				sender.sendMessage(ChatColor.RED
						+ "You didn't specify a player!");
				return;
			}

			// Search player
			List<Player> matchedPlayers = plugin.getServer().matchPlayer(
					targetName);

			if (matchedPlayers.size() == 0) {
				sender.sendMessage(ChatColor.RED + "Player '" + targetName
						+ "' is not online!");
				return;
			}

			if (matchedPlayers.size() > 1) {
				sender.sendMessage(ChatColor.RED
						+ "There are more than 1 players found that match your target's name. Be more precise!");
				return;
			}

			// Get the only player.
			player = matchedPlayers.get(0);
		}

		// Remove target name
		args = this.removeTargetName(args);

		// Build string from left over arguments.
		String suffix = this.buildString(args);
		
		if (suffix.trim().equals("")) {
			sender.sendMessage(ChatColor.RED + "You did not supply a suffix!");
			return;
		}

		// Check for censored words
		if (this.hasCensoredWords(suffix)
				&& !sender.hasPermission("simplesuffix.suffix.bypass.censor")) {
			sender.sendMessage(ChatColor.RED
					+ "Your suffix contains censored words. This is not allowed.");
			return;
		}

		// Check for character limit
		if (!this.checkCharacterLimit(suffix, false)) {
			sender.sendMessage(ChatColor.RED
					+ "Your suffix exceeds the character limit!");
			return;
		}

		// Check on colours
		if (!this.checkColours(suffix, sender)) {
			return;
		}

		//Check on formats
		if (!this.checkForFormats(suffix, sender)) {
			return;
		}

		boolean shouldClear = this.shouldClear(suffix);

		// Set prefix
		plugin.getVaultHandler().setSuffix(worldName, player.getName(), suffix,
				shouldClear);

		// Message player
		if (self) {
			if (!isWorldSpecific) {
				if (shouldClear) {
					// Not world specific, from yourself and will clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your global suffix is cleared!");
				} else {
					// Not world specific, from yourself and will NOT clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your global suffix is set to: " + ChatColor.GOLD
							+ suffix);
				}
			} else {
				if (shouldClear) {
					// World specific, from yourself and will clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your suffix on world " + ChatColor.DARK_AQUA
							+ worldName + ChatColor.GREEN + " is cleared!");
				} else {
					// World specific, from yourself and will NOT clear
					sender.sendMessage(ChatColor.GREEN
							+ "Your suffix on world " + ChatColor.DARK_AQUA
							+ worldName + ChatColor.GREEN + " is set to: "
							+ ChatColor.GOLD + suffix);
				}
			}
		} else {
			if (!isWorldSpecific) {
				if (shouldClear) {
					// Not world specific, for another player and will clear
					sender.sendMessage(ChatColor.GREEN
							+ "The global suffix of " + ChatColor.YELLOW
							+ player.getName() + ChatColor.GREEN
							+ " is cleared!");
				} else {
					// Not world specific, for another player and will NOT clear
					sender.sendMessage(ChatColor.GREEN
							+ "The global suffix of " + ChatColor.YELLOW
							+ player.getName() + ChatColor.GREEN
							+ " is set to: " + ChatColor.GOLD + suffix);
				}
			} else {
				if (shouldClear) {
					// World specific, for another player and will clear
					sender.sendMessage(ChatColor.GREEN + "The suffix on world "
							+ ChatColor.DARK_AQUA + worldName + ChatColor.GREEN
							+ " of " + ChatColor.YELLOW + player.getName()
							+ ChatColor.GREEN + " is cleared!");

				} else {
					// World specific, for another player and will NOT clear
					sender.sendMessage(ChatColor.GREEN + "The suffix on world "
							+ ChatColor.DARK_AQUA + worldName + ChatColor.GREEN
							+ " of " + ChatColor.YELLOW + player.getName()
							+ ChatColor.GREEN + " is set to: " + ChatColor.GOLD
							+ suffix);
				}
			}
		}

		return;
	}

	public boolean hasPermission(String permission, CommandSender sender) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(ChatColor.RED + "You cannot do this! You need ("
					+ permission + ") to do this!");
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.command.TabCompleter#onTabComplete(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {

		if (args.length == 0)
			return null;

		String lastArg = args[args.length - 1];

		if (lastArg == null)
			return null;

		// Player is trying to find a player
		if (lastArg.toLowerCase().contains("player:")) {
			// Inin list for return.
			List<String> names = new ArrayList<String>();

			// Get extra stuff he might have typed.
			String extra = lastArg.substring(lastArg.indexOf(":") + 1);

			if (extra == null || extra.equals("")) {

				// Get all online players
				for (Player p: plugin.getServer().getOnlinePlayers()) {
					names.add("player:" + p.getName());
				}
			}

			// Get all matching players.
			List<Player> matchingPlayers = plugin.getServer()
					.matchPlayer(extra);

			if (matchingPlayers.size() == 1) {
				// Found one matching player
				names.add("player:" + matchingPlayers.get(0).getName());

				return names;
			} else if (matchingPlayers.size() > 1) {
				for (Player p : matchingPlayers) {
					names.add("player:" + p.getName());
				}

				return names;
			}
		}

		// Player is trying to find a player
		if (lastArg.toLowerCase().contains("world:")) {
			// Inin list for return.
			List<String> names = new ArrayList<String>();

			// Get all matching world.
			List<World> worlds = plugin.getServer()
					.getWorlds();

			if (worlds.size() == 1) {
				// Found one matching world
				names.add("world:" + worlds.get(0).getName());

				return names;
			} else if (worlds.size() > 1) {
				for (World w : worlds) {
					names.add("world:" + w.getName());
				}

				return names;
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}
}
