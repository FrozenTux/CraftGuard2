package fr.frozentux.craftguard2.list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.list.craft.CraftList;

public class ListLoader {
	
	private CraftGuardPlugin plugin;
	
	private File configurationFile;
	private FileConfiguration configuration;
	
	public ListLoader(CraftGuardPlugin plugin, FileConfiguration fileConfiguration, File file){
		this.plugin = plugin;
		this.configurationFile = file;
		this.configuration = fileConfiguration;
	}
	
	public HashMap<String, List> load(){
		HashMap<String, List> lists = new HashMap<String, List>();
		
		if(!configurationFile.exists()){
			configuration.set("example.permission", "permission");
			ArrayList<String> commonIds = new ArrayList<String>();
			commonIds.add("24");
			commonIds.add("32:2");
			configuration.set("example.commonids", commonIds);
			configuration.options().header("CraftGuard 2.X by FrozenTux\nhttp://dev.bukkit.org/server-mods/craftguard\n\nThis file stores only lists definition with their common ids, their permissions and their parents").copyHeader(true);
			
			try {
				configuration.save(configurationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			configuration = new YamlConfiguration();
			configuration.load(configurationFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Iterator<String> it = configuration.getKeys(false).iterator();
		
		while(it.hasNext()){	//This loop will be run for each list
			String name = it.next();
			String permission = configuration.getString(name + ".permission");
			String parentName = configuration.getString(name + ".parent");
			java.util.List<String> commonIds = configuration.getStringList(name + "commonids");
			if(commonIds != null)lists.put(name, new List(name, permission, parentName, commonIds, plugin.getListManager()));
			else lists.put(name, new List(name, permission, parentName, plugin.getListManager()));
		}
		
		plugin.getCraftGuardLogger().info("Succesfully loaded " + lists.size() + " lists");
		
		return lists;
	}
}
