package me.staartvin.simplesuffix.vault;

import java.util.List;

import me.staartvin.simplesuffix.SimpleSuffix;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHandler {

	private SimpleSuffix plugin;
	public static Chat chat = null;
	public Plugin vault;

	public VaultHandler(SimpleSuffix instance) {
		plugin = instance;
	}

	public boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = plugin.getServer()
				.getServicesManager()
				.getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = (Chat) chatProvider.getProvider();
		}

		return (chat != null);
	}

	public Plugin getVault() {
		vault = plugin.getServer().getPluginManager().getPlugin("Vault");
		return vault;
	}

	public String getPlayerSuffix(Player player) {
		return chat.getPlayerSuffix(player);
	}

	public void setGlobalPlayerSuffix(Player player, String suffix) {
		List<World> worlds = plugin.getServer().getWorlds();

		for (World world : worlds) {
			chat.setPlayerSuffix(world, player.getName(), plugin.getConfigClass()
					.replaceText(
							plugin.getConfig().getString("predefined suffix"),
							suffix));
		}
	}

	public String getPlayerPrefix(Player player) {
		return chat.getPlayerPrefix(player);
	}

	public void setGlobalPlayerPrefix(Player player, String prefix) {
		List<World> worlds = plugin.getServer().getWorlds();
		for (World world : worlds) {
			chat.setPlayerPrefix(world, player.getName(), plugin.getConfigClass().replaceText(plugin
					.getConfig().getString("predefined prefix"), prefix));
		}
	}
	
	public void setWorldPlayerPrefix(Player player, String prefix, World world) {
		chat.setPlayerPrefix(world, player.getName(), plugin.getConfigClass().replaceText(plugin
					.getConfig().getString("predefined prefix"), prefix));
	}
	
	public void setWorldPlayerSuffix(Player player, String suffix, World world) {
			chat.setPlayerSuffix(world, player.getName(), plugin.getConfigClass()
					.replaceText(
							plugin.getConfig().getString("predefined suffix"),
							suffix));
	}
	
	public void clearGlobalPrefix(String player) {
		for (World world:plugin.getServer().getWorlds()) {
			chat.setPlayerPrefix(world, player, "");
		}
	}
	
	public void clearGlobalSuffix(String player) {
		for (World world:plugin.getServer().getWorlds()) {
			chat.setPlayerSuffix(world, player, "");
		}
	}
	
	public void clearWorldPrefix(String player, String world) {
		chat.setPlayerPrefix(world, player, "");
	}
	
	public void clearWorldSuffix(String player, String world) {
		chat.setPlayerSuffix(world, player, "");
	}
}
