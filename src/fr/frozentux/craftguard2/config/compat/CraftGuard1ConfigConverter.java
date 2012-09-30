package fr.frozentux.craftguard2.config.compat;

import java.io.File;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import fr.frozentux.craftguard2.CraftGuardPlugin;

/**
 * Class that converts configuration file from CraftGuard 1.x format to CraftGuard 2.x format
 * @author FrozenTux
 *
 */
public class CraftGuard1ConfigConverter {
	
	private CraftGuardPlugin plugin;
	
	private FileConfiguration config, list;
	private File configFile, listFile;
	
	/**
	 * Class that converts configuration file from CraftGuard 1.x format to CraftGuard 2.x format
	 * @param config		FileConfiguration instance of the plugin's config file (containing the old data structure)
	 * @param configFile	File pointing at the configuration file
	 * @param list			FileConfiguration instance of the craftguard's list file
	 * @param listFile		File pointing at the list file
	 * @param plugin		Plugin using this converter
	 */
	public CraftGuard1ConfigConverter(FileConfiguration config, File configFile, FileConfiguration list, File listFile, CraftGuardPlugin plugin){
		this.config = config;
		this.list = list;
		this.plugin = plugin;
		this.configFile = configFile;
		this.listFile = listFile;
	}
	
	/**
	 * Converts the configuration false
	 * @return	If something has gone wrong
	 */
	public boolean convert(){
		
		Set<String> keys = config.getConfigurationSection("craftguard").getKeys(false);
		
		
		
		return true;
	}
}
