package me.Staartvin.SimpleSuffix.Sqlite;


import me.Staartvin.SimpleSuffix.SimpleSuffix;

public class SqLite {

	SimpleSuffix plugin;
	
	public SqLite(SimpleSuffix instance) {
		plugin = instance;
	}
	
	public Database getPrefix(String playerName) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).findUnique();
		
		if (database != null) {
			return database;
		}
		else {
			return null;
		}
	}
	
	public Database getSuffix(String playerName) {
		Database database = plugin.getDatabase().find(Database.class).where().ieq("playerName", playerName).findUnique();
		
		if (database != null) {
			return database;
		}
		else {
			return null;
		}
	}
}
