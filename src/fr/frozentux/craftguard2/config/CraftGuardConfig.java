package fr.frozentux.craftguard2.config;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;

import fr.frozentux.craftguard2.CraftGuardPlugin;

/**
 * Loads, writes and stores configuration options for CraftGuard
 * @author FrozenTux
 *
 */
public class CraftGuardConfig {
	
	private CraftGuardPlugin plugin;
	
	private FileConfiguration file;
	
	private HashMap<String, Object> fields;
	
	/**
	 * Loads, writes and stores configuration options for CraftGuard
	 * @param plugin	The plugin using this object
	 *
	 */
	public CraftGuardConfig(CraftGuardPlugin plugin){
		this.plugin = plugin;
		fields = new HashMap<String, Object>();
		file = plugin.getConfig();
	}
	
	/**
	 * Loads the configuration from the config file, erasing values in memory
	 */
	public void load(){
		plugin.reloadConfig();
		
		file.addDefault("checkFurnaces", true);
		file.addDefault("preventiveallow", true);
		file.addDefault("denyMessage", "You can't craft %n !");
		file.addDefault("log", true);
		file.addDefault("logMessage", "%p tried to craft %n(%i) but has been denied");
		file.addDefault("basePerm", "craftguard");
		
		if(!file.isSet("log")){
			plugin.getFrozenLogger().info("Configuration file not detected ! Writing defaults...");
			file.options().header("CraftGuard version 2.X by FrozenTux\nhttp://dev.bukkit.org/server-mods/craftguard").copyHeader();
			file.options().copyDefaults();
			plugin.saveConfig();
		}
		
		Iterator<String> it = file.getKeys(false).iterator();
		
		while(it.hasNext()){
			String key = it.next();
			fields.put(key, file.get(key));
		}
		
		plugin.getFrozenLogger().info("Configuration loaded !");
	}
	
	public void write(){
		Iterator<String> it = fields.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			file.set(key, fields.get(key));
		}
		
		plugin.saveConfig();
		plugin.getFrozenLogger().info("Configuration saved !");
	}
}
