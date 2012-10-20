package fr.frozentux.craftguard2.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.list.Id;
import fr.frozentux.craftguard2.list.List;
import fr.frozentux.craftguard2.list.ListManager;

/**
 * Loads, writes and stores groups lists for CraftGuard
 * @author FrozenTux
 *
 */
public class ListLoader {
	
	private CraftGuardPlugin plugin;
	
	private File configurationFile;
	private FileConfiguration configuration;
	
	/**
	 * Loads, writes and stores groups lists for CraftGuard
	 * @author FrozenTux
	 *
	 */
	public ListLoader(CraftGuardPlugin plugin, FileConfiguration fileConfiguration, File file){
		this.plugin = plugin;
		this.configurationFile = file;
		this.configuration = fileConfiguration;
	}
	
	/**
	 * Loads the list from the {@link FileConfiguration} specified in the constructor
	 * @return A HashMap of the loaded groups
	 */
	public HashMap<String, List> load(){
		
		//Initializing the groups list or clearing it
		HashMap<String, List> groupsLists = new HashMap<String, List>();
		
		//If the file doesn't exist, write defaults
		if(!configurationFile.exists()){
			plugin.getCraftGuardLogger().debug("ListFile not existing");
			HashMap<Integer, Id> exampleMap = new HashMap<Integer, Id>();
			exampleMap.put(5, new Id(5));//PLANKS
			exampleMap.put(35, new Id(35));
			exampleMap.get(35).addMetadata(2);//Only purple WOOL
			List exampleList = new List("example", "samplepermission", exampleMap, null, plugin.getListManager());
			
			configuration.addDefault(exampleList.getName() + ".list", encodeList(exampleList));
			configuration.addDefault(exampleList.getName() + ".permission", exampleList.getPermission());
			configuration.options().header("CraftGuard 2.X by FrozenTux").copyHeader(true).copyDefaults(true);
			try {
				configuration.save(configurationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Load the file
		try {
			configuration.load(configurationFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Set<String> keys = configuration.getKeys(false);
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()){	//This loop will be run for each list
			String name = it.next();
			List list = buildList(name, groupsLists);
			groupsLists.put(name, list);
		}
		
		plugin.getCraftGuardLogger().info("Succesfully loaded " + groupsLists.size() + " lists");
		
		return groupsLists;
	}
	
	/**
	 * Returns an id from a raw config string (obtained via <code>serializeList()</code>
	 * @param raw	The raw string to decode
	 * @return		The corresponding id object
	 */
	public Id decodeId(String raw){
		//First getting the base id
		int id = Integer.valueOf(raw.split(":")[0]);
		
		//Then parsing metadata (if any)
		if(raw.split(":").length > 1){
			int length = raw.split(":").length;
			ArrayList<Integer> metadata = new ArrayList<Integer>();
			
			for(int i = 1 ; i < length ; i++){	//Starts at 1 because 0 is the id
				metadata.add(Integer.valueOf(raw.split(":")[i]));
			}
			return new Id(id, metadata);
			
		}else{
			return new Id(id);
		}
		
	}
	
	/**
	 * Encodes a list to a list of raw String ids
	 * @param list	The list to encode
	 * @return		The encoded list
	 */
	public ArrayList<String> encodeList(List list){
		plugin.getCraftGuardLogger().debug("Encoding list " + list.getName());
		//Getting keys (list of ids)
		HashMap<Integer, Id> map = list.getIds(false);
		Set<Integer> keys = map.keySet();
		Iterator<Integer> it = keys.iterator();
		
		ArrayList<String> ids = new ArrayList<String>();
		
		while(it.hasNext())ids.add(map.get(it.next()).toString());
		
		return ids;
	}
	
	/**
	 * Builds a list from the given path in the FileConfiguration
	 * @param path	The path of the list (also taken as it's name)
	 * @return	The built list
	 */
	public List buildList(String path, HashMap<String, List> groupsLists){
		plugin.getCraftGuardLogger().debug("Building list " + path);
		java.util.List<String> rawList = configuration.getStringList(path + ".list");
		
		String permission = configuration.getString(path + ".permission");	//Note : permission can be null, a check is done in the List object
		String parentName = configuration.getString(path + ".parent");	//May also be null
		List parent = groupsLists.get(parentName);
		
		Iterator<String> idIterator = rawList.iterator();
		List list = new List(path, permission, parent, plugin.getListManager());
		
		while(idIterator.hasNext()){
			list.addId(decodeId(idIterator.next()));
		}
		
		return list;
	}
	
	public void writeLists(ListManager manager){
		plugin.getCraftGuardLogger().info("Saving " + manager.getListsNames().size() + " lists...");
		
		configurationFile.delete();
		try{
			configurationFile.createNewFile();
			configuration.load(configurationFile);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		configuration.options().header("CraftGuard 2.X by FrozenTux").copyHeader(true);
		
		Iterator<String> it = manager.getListsNames().iterator();
		
		while(it.hasNext()){
			List list = manager.getList(it.next());
			configuration.set(list.getName() + ".list", encodeList(list));
			if(!list.getPermission().equals(list.getName()))configuration.set(list.getName() + ".permission", list.getPermission());
			if(!(list.getParent() == null))configuration.set(list.getName() + ".parent", list.getParent().getName());
		}
		
		try {
			configuration.save(configurationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
