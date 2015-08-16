package me.staartvin.simplesuffix.permissions;

import me.staartvin.simplesuffix.SimpleSuffix;
import me.staartvin.simplesuffix.sqlite.Database;
import me.staartvin.simplesuffix.vault.VaultHandler;

import org.bukkit.entity.Player;

public class PermissionsHandler {

	SimpleSuffix plugin;

	public PermissionsHandler(SimpleSuffix instance) {
		plugin = instance;
	}

	public void setupPermissions() {
		if (useDatabase()) {
			plugin.setupDatabase();
			plugin.getLoggerClass()
					.logNormal(
							"Using database because permissions plugin doesn't provide chat support!");
		}
	}

	public boolean useDatabase() {
		if (plugin.getVaultHandler().getVault() != null) {
			if (VaultHandler.chat == null) {
				// Fall back to database because no chat support has been found.
				return true;
			}
		}
		return false;
	}

	public String getPrefix(Player player) {
		if (useDatabase()) {
			Database prefix = plugin.getSqLite().getPrefix(player.getName());
			if (prefix == null)
				return null;
			return prefix.getPrefix();
		}
		return plugin.getVaultHandler().getPlayerPrefix(player);
	}

	public String getSuffix(Player player) {
		if (useDatabase()) {
			Database suffix = plugin.getSqLite().getSuffix(player.getName());
			if (suffix == null)
				return null;
			return suffix.getSuffix();
		}
		return plugin.getVaultHandler().getPlayerSuffix(player);
	}

	public void setPrefix(String playerName, String prefix) {
		if (useDatabase()) {
			Database dprefix = plugin.getSqLite().getPrefix(playerName);
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(playerName);
				dprefix.setPrefix(prefix);
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix(prefix);
			plugin.getDatabase().save(dprefix);
			return;
		}
	}

	public void setSuffix(String playerName, String suffix) {
		if (useDatabase()) {
			Database dsuffix = plugin.getSqLite().getSuffix(playerName);
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(playerName);
				dsuffix.setPrefix("");
				dsuffix.setSuffix(suffix);
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setPrefix(suffix);
			plugin.getDatabase().save(dsuffix);
			return;
		}
	}
}
