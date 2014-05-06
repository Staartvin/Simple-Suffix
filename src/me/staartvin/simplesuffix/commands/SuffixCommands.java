package me.staartvin.simplesuffix.commands;


import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SuffixCommands implements CommandExecutor {

	SimpleSuffix plugin;
	
	public SuffixCommands(SimpleSuffix instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("suffix")) {
			
			// Invalid syntax
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Too few arguments!");
				sender.sendMessage(ChatColor.RED + "Syntax: " + "/suffix (other) (playername) (world 'world') (suffix)");
				return true;
			}
			
			// Set your own suffix
			if (!args[0].equalsIgnoreCase("other")) {
				return plugin.getCommands().setSuffix(sender, args, true);	
			}
			
			else {
			
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "Too few arguments!");
					sender.sendMessage(ChatColor.RED + "Syntax: " + "/suffix other (playername) (world 'world') (suffix)");
					return true;
				}

				return plugin.getCommands().setSuffix(sender, args, false);
			}
		}
		sender.sendMessage(ChatColor.RED + "Incorrect command usage!");
		sender.sendMessage(ChatColor.YELLOW + "Use '/suffix other <playername> <suffix>' or '/suffix <suffix>'");
		return true;
	}
}
