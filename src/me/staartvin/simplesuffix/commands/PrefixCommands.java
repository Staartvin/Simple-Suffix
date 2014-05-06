package me.staartvin.simplesuffix.commands;

import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PrefixCommands implements CommandExecutor {

	SimpleSuffix plugin;
	
	public PrefixCommands(SimpleSuffix instance) {
		plugin = instance;
	}	
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("prefix")){
			
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Too few arguments!");
				sender.sendMessage(ChatColor.RED + "Syntax: " + "/prefix (other) (playername) (world 'world') (prefix)");
				return true;
			}
			
			if (!args[0].equalsIgnoreCase("other")) {
				
				return plugin.getCommands().setPrefix(sender, args, true);
				
			} else {
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "Too few arguments!");
					sender.sendMessage(ChatColor.RED + "Syntax: " + "/prefix other (playername) (world 'world') (prefix)");
					return true;
				}
				
				return plugin.getCommands().setPrefix(sender, args, false);
			}
		}
		sender.sendMessage(ChatColor.RED + "Incorrect command usage!");
		sender.sendMessage(ChatColor.YELLOW + "Use '/prefix other <playername> <prefix>' or '/prefix <prefix>'");
		return true;
	}
}
