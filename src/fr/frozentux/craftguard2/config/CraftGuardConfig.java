package fr.frozentux.craftguard2.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	
	private String[] defaultKeys = {"checkFurnaces", "preventiveallow", "denyMessage", "log", "logMessage", "basePerm", "debug"};
	private Object[] defaultValues = {true, true, "You can't craft %n !", true, "%p tried to craft %n(%i) but has been denied", "basePerm", false};
	
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
			plugin.getCraftGuardLogger().info("Old configuration format has been detected ! Your file will be converted.");
			new CraftGuard1ConfigConverter(plugin).convert();
		}
		
		for(int i = 0 ; i<defaultKeys.length ; i++){
			file.addDefault(defaultKeys[i], defaultValues[i]);
		}
		
		if(!file.isSet("log")){
			plugin.getCraftGuardLogger().info("Configuration file not detected ! Writing defaults...");
			file.options().header("CraftGuard version 2.X by FrozenTux\nhttp://dev.bukkit.org/server-mods/craftguard\n\nIf you erase some values by error remove log line and missing default values will be rewritten !").copyHeader();
			file.options().copyDefaults();
			plugin.saveConfig();
		}
		
		Iterator<String> it = file.getKeys(false).iterator();
		
		while(it.hasNext()){
			String key = it.next();
			fields.put(key, file.get(key));
		}
		
		plugin.getCraftGuardLogger().info("Configuration loaded !");
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
		plugin.getCraftGuardLogger().info("Configuration saved !");
	}
	
	/**
	 * Gets the field corresponding to the given key
	 * @param key	The key of the field
	 * @return		<code>null</code> if the field doesn't exist;otherwise the field as an {@link Object}
	 */
	public Object getKey(String key){
		Object value = fields.get(key);
		List<String> defaultList = Arrays.asList(defaultKeys);
		if(value == null && defaultList.contains(key))value = Arrays.asList(defaultValues).get(defaultList.indexOf(key));
		return value;
	}
	
	/**
	 * Gets the field corresponding to the given key
	 * @param key	The key of the field
	 * @return		<code>null</code> if the field doesn't exist;otherwise the field as an {@link String}
	 */
	public String getStringKey(String key){
		return String.valueOf(getKey(key));
	}
	
	/**
	 * Gets the field corresponding to the given key
	 * @param key	The key of the field
	 * @return		<code>null</code> if the field doesn't exist;otherwise the field as an {@link Boolean}
	 */
	public boolean getBooleanKey(String key){
		return (Boolean)getKey(key);
	}
}
