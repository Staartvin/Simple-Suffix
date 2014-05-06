package me.staartvin.simplesuffix;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.simplesuffix.commands.Commands;
import me.staartvin.simplesuffix.commands.PrefixCommands;
import me.staartvin.simplesuffix.commands.SuffixCommands;
import me.staartvin.simplesuffix.config.Config;
import me.staartvin.simplesuffix.listeners.SSChatListener;
import me.staartvin.simplesuffix.permissions.PermissionsHandler;
import me.staartvin.simplesuffix.sqlite.Database;
import me.staartvin.simplesuffix.sqlite.SqLite;
import me.staartvin.simplesuffix.vault.VaultHandler;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleSuffix extends JavaPlugin {

	private VaultHandler vaultHandler; 
	private Logger logger;
	private Config config; 
	private SqLite sqLite; 
	private Commands commands; 
	private PermissionsHandler permHandler; 
	
	@Override
	public void onEnable() {
		
		vaultHandler = new VaultHandler(this);
		logger = new Logger(this);
		config = new Config(this);
		sqLite = new SqLite(this);
		commands = new Commands(this);
		permHandler = new PermissionsHandler(this);
		
		getServer().getPluginManager().registerEvents(new SSChatListener(this), this);
		
		config.loadConfiguration();
		vaultHandler.setupChat();
		if (vaultHandler.getVault() != null) {
			if (VaultHandler.chat == null) {
				logger.logNormal("Permissions plugin does not support chat!");
			} else {
				logger.logNormal("Using Permissions plugin for chat!");	
			}
			
			logger.logVerbose("Hooked into Vault v" + vaultHandler.vault.getDescription().getVersion() + " successfully!");
		}
		
		permHandler.setupPermissions();
		getCommand("suffix").setExecutor(new SuffixCommands(this));
		getCommand("prefix").setExecutor(new PrefixCommands(this));
		
		logger.logNormal("Simple Suffix v" + getDescription().getVersion() + " has been enabled!");
	}
	@Override
	public void onDisable() {
		logger.logNormal("Simple Suffix v" + getDescription().getVersion() + " has been disabled!");
	}
	
	
	
	public void setupDatabase() {
		try {
			getDatabase().find(Database.class).findRowCount();
		} catch (Exception e) {
			logger.logNormal("Installing Database for the first time!");
			installDDL();
		}
	}
	
    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Database.class);
        return list;
    }
	public SqLite getSqLite() {
		return sqLite;
	}

	public Commands getCommands() {
		return commands;
	}

	public PermissionsHandler getPermHandler() {
		return permHandler;
	}
	
	public VaultHandler getVaultHandler() {
		return vaultHandler;
	}
	
	public Logger getLoggerClass() {
		return logger;
	}
	
	public Config getConfigClass() {
		return config;
	}
}
