package me.staartvin.simplesuffix.commands;

import java.util.List;

import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class PrefixCommands implements TabExecutor {

	SimpleSuffix plugin;

	public PrefixCommands(SimpleSuffix instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("prefix")) {

			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Too few arguments!");
				sender.sendMessage(ChatColor.RED + "Syntax: "
						+ "/prefix (player:playername) (world:world) <prefix>");
				return true;
			}

			boolean ownChange = plugin.getCommands().isOwnChange(sender, args);

			if (!ownChange) {
				if (args.length < 2) {
					sender.sendMessage(ChatColor.RED + "Too few arguments!");
					sender.sendMessage(ChatColor.RED
							+ "Syntax: "
							+ "/prefix (player:playername) (world:world) <prefix>");
					return true;
				}
			}

			// Call prefix method
			plugin.getCommands().setPrefix(sender, args, ownChange);

			return true;
		}

		sender.sendMessage(ChatColor.RED + "Incorrect command usage!");
		sender.sendMessage(ChatColor.YELLOW
				+ "Use '/prefix (player:playername) (world:world) or '/prefix <prefix>'");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.command.TabCompleter#onTabComplete(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		// TODO Auto-generated method stub
		return plugin.getCommands().onTabComplete(sender, command, alias, args);
	}
}
