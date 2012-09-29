package fr.frozentux.craftguard2.config;

import java.util.HashMap;

import org.bukkit.plugin.Plugin;

/**
 * Loads, writes and stores configuration options for CraftGuard
 * @author FrozenTux
 *
 */
public class CraftGuardConfig {
	
	private Plugin plugin;
	
	private HashMap<String, Object> keys;
	
	/**
	 * Loads, writes and stores configuration options for CraftGuard
	 * @param plugin	The plugin using this object
	 *
	 */
	public CraftGuardConfig(Plugin plugin){
		this.plugin = plugin;
		keys = new HashMap<String, Object>();
	}
	
	/**
	 * Loads the configuration from the config file, erasing values in memory
	 */
	public void load(){
		
	}
	
	
	
	
}
