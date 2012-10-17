package fr.frozentux.craftguard2.config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.config.compat.CraftGuard1ConfigConverter;

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
		
		if(file.isSet("craftguard")){	//Old configuration type, needs to be converted
			plugin.getFrozenLogger().info("Old configuration format has been detected ! Your file will be converted.");
			File configFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
			new CraftGuard1ConfigConverter(plugin).convert();
		}
		
		file.addDefault("checkFurnaces", true);
		file.addDefault("preventiveallow", true);
		file.addDefault("denyMessage", "You can't craft %n !");
		file.addDefault("log", true);
		file.addDefault("logMessage", "%p tried to craft %n(%i) but has been denied");
		file.addDefault("basePerm", "craftguard");
		file.addDefault("debug", false);
		
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
	
	/**
	 * Writes the fields into the plugin's {@link FileConfiguration}
	 */
	public void write(){
		Iterator<String> it = fields.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			file.set(key, fields.get(key));
		}
		
		plugin.saveConfig();
		plugin.getFrozenLogger().info("Configuration saved !");
	}
	
	/**
	 * Gets the field corresponding to the given key
	 * @param key	The key of the field
	 * @return		<code>null</code> if the field doesn't exist;otherwise the field as an {@link Object}
	 */
	public Object getKey(String key){
		return fields.get(key);
	}
	
	/**
	 * Gets the field corresponding to the given key
	 * @param key	The key of the field
	 * @return		<code>null</code> if the field doesn't exist;otherwise the field as an {@link String}
	 */
	public String getStringKey(String key){
		return String.valueOf(fields.get(key));
	}
	
	/**
	 * Gets the field corresponding to the given key
	 * @param key	The key of the field
	 * @return		<code>null</code> if the field doesn't exist;otherwise the field as an {@link Boolean}
	 */
	public boolean getBooleanKey(String key){
		return (Boolean)fields.get(key);
	}
}
