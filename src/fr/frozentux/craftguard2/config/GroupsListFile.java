package fr.frozentux.craftguard2.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Loads, writes and stores configuration options for CraftGuard
 * @author FrozenTux
 *
 */
public class GroupsListFile {
	
	private Plugin plugin;
	
	private HashMap<String, HashMap<Integer, ArrayList<Integer>>> groupsLists;
	
	private File configurationFile;
	private FileConfiguration configuration;
	
	public GroupsListFile(Plugin plugin, FileConfiguration fileConfiguration, File file){
		this.plugin = plugin;
		this.configurationFile = file;
		this.configuration = fileConfiguration;
	}
	
	
	
}
