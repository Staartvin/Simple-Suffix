package me.staartvin.simplesuffix.permissions;

import me.staartvin.simplesuffix.SimpleSuffix;
import me.staartvin.simplesuffix.sqlite.Database;
import me.staartvin.simplesuffix.vault.VaultHandler;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PermissionsHandler{

	SimpleSuffix plugin;
	
	public PermissionsHandler(SimpleSuffix instance) {
		plugin = instance;
	}
	
	public void setupPermissions() {
		if (useDatabase()) {
			plugin.setupDatabase();
			plugin.getLoggerClass().logNormal("Using database because permissions plugin doesn't provide chat support!");
		}
	}
	
	public boolean useDatabase() {
		if (plugin.getVaultHandler().getVault() != null) {
			plugin.getVaultHandler();
			if (VaultHandler.chat == null) {
				return true;
			}
		}
		return false;
	}
	
	public String getPrefix(Player player){ 
		if (useDatabase()) {
			Database prefix = plugin.getSqLite().getPrefix(player.getName());
			if (prefix == null) return null;
			return prefix.getPrefix();
		}
		return plugin.getVaultHandler().getPlayerPrefix(player);
	}
	
	public String getSuffix(Player player){ 
		if (useDatabase()) {
			Database suffix = plugin.getSqLite().getSuffix(player.getName());
			if (suffix == null) return null;
			return suffix.getSuffix();
		}
		return plugin.getVaultHandler().getPlayerSuffix(player);
	}
	
	public void setGlobalPrefix(Player player, String prefix){ 
		if (useDatabase()) {
			Database dprefix = plugin.getSqLite().getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix(prefix);
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix(prefix);
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.getVaultHandler().setGlobalPlayerPrefix(player, prefix);
	}
	
	public void setGlobalSuffix(Player player, String suffix){ 
		if (useDatabase()) {
			Database dsuffix = plugin.getSqLite().getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix(suffix);
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix(suffix);
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.getVaultHandler().setGlobalPlayerSuffix(player, suffix);
	}
	
	public void setWorldSuffix(Player player, String suffix, World world){ 
		if (useDatabase()) {
			Database dsuffix = plugin.getSqLite().getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix(suffix);
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix(suffix);
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.getVaultHandler().setWorldPlayerSuffix(player, suffix, world);
	}
	
	public void setWorldPrefix(Player player, String prefix, World world){ 
		if (useDatabase()) {
			Database dprefix = plugin.getSqLite().getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix(prefix);
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix(prefix);
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.getVaultHandler().setWorldPlayerPrefix(player, prefix, world);
	}
	
	public void clearGlobalSuffix(Player player){ 
		if (useDatabase()) {
			Database dsuffix = plugin.getSqLite().getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix("");
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix("");
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.getVaultHandler().clearGlobalSuffix(player.getName());
	}
	
	public void clearWorldPrefix(Player player, String world){ 
		if (useDatabase()) {
			Database dprefix = plugin.getSqLite().getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix("");
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix("");
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.getVaultHandler().clearWorldPrefix(player.getName(), world);
	}
	
	public void clearWorldSuffix(Player player, String world){ 
		if (useDatabase()) {
			Database dsuffix = plugin.getSqLite().getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix("");
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix("");
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.getVaultHandler().clearWorldSuffix(player.getName(), world);
	}
	
	public void clearGlobalPrefix(Player player){ 
		if (useDatabase()) {
			Database dprefix = plugin.getSqLite().getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix("");
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix("");
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.getVaultHandler().clearGlobalPrefix(player.getName());
	}
}
