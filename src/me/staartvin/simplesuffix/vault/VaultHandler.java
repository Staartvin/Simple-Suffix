package me.staartvin.simplesuffix.vault;

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

	@SuppressWarnings("deprecation")
	public void setPrefix(String worldName, String playerName, String prefix,
			boolean clear) {

		String message = plugin.getConfig().getString("predefined prefix");

		message = plugin.getConfigClass().replaceText(message, prefix);

		// Should we clear it?
		if (clear) {
			message = "";
			prefix = "";
		}

		// One specific world
		if (worldName != null) {
			chat.setPlayerPrefix(worldName, playerName, message);
		} else {
			// All worlds
			for (World world : plugin.getServer().getWorlds()) {
				chat.setPlayerPrefix(world.getName(), playerName, message);
			}
		}
		
		plugin.getPermHandler().setPrefix(playerName, prefix);
	}

	@SuppressWarnings("deprecation")
	public void setSuffix(String worldName, String playerName, String suffix,
			boolean clear) {

		String message = plugin.getConfig().getString("predefined suffix");

		message = plugin.getConfigClass().replaceText(message, suffix);

		// Should we clear it?
		if (clear) {
			message = "";
			suffix = "";
		}

		// One specific world
		if (worldName != null) {
			chat.setPlayerSuffix(worldName, playerName, message);
		} else {
			// All worlds
			for (World world : plugin.getServer().getWorlds()) {
				chat.setPlayerSuffix(world.getName(), playerName, message);
			}
		}
		
		plugin.getPermHandler().setSuffix(playerName, suffix);
	}

	public String getPlayerPrefix(Player player) {
		return chat.getPlayerPrefix(player);
	}
}
