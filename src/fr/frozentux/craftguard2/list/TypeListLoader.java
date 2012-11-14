package fr.frozentux.craftguard2.list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.api.CraftGuard2Plugin;

/**
 * Loader for a specific type of TypeList for a CraftGuard module
 * @author FrozenTux
 *
 */
public class TypeListLoader {
	
	protected String type;
	
	protected CraftGuardPlugin plugin;
	
	protected ListManager manager;
	
	protected File file;
	protected FileConfiguration configuration;
	
	protected ArrayList<String> defaultList;
	
	protected String header;
	
	/**
	 * @param type		The type of the typeList this loader will load
	 * @param file		A File object pointing at the location where the configuration file is.
	 * 					It should point at <code>(CraftGuard Folder)/(type).yml</code>
	 * @param module	The module using this loader
	 */
	public TypeListLoader(String type, File file, CraftGuardPlugin plugin){
		this.type = type;
		this.file = file;
		this.configuration = new YamlConfiguration();
		this.manager = plugin.getCraftGuard().getListManager();
		this.plugin = plugin;
		this.defaultList = new ArrayList<String>();
		defaultList.add("5");
		defaultList.add("35:4:6");
		this.header = null;
	}
	
	public TypeListLoader(String type, File file, CraftGuardPlugin plugin, String header){
		this.type = type;
		this.file = file;
		this.configuration = new YamlConfiguration();
		this.manager = plugin.getCraftGuard().getListManager();
		this.plugin = plugin;
		this.defaultList = new ArrayList<String>();
		defaultList.add("5");
		defaultList.add("35:4:6");
		this.header = header;
	}

	public void loadAllLists(){
		if(!file.exists()){
			try{
				file.createNewFile();
				String aRandomListName = "example";
				if(!manager.getListsNames().contains("example")){
					//Seems that the example list has been deleted, we have to find another one
					//This code could crash if the main list file is existing but empty, but why the server admin woud do that ? ;)
					aRandomListName = (String) manager.getListsNames().toArray()[0];
				}
				configuration.set(aRandomListName, defaultList);
				saveLists();
			}catch(Exception e){
				return;
			}
		}
		
		try{
			configuration.load(file);
		}catch(Exception e){
			return;
		}
			
		Iterator<String> it = manager.getListsNames().iterator();
		
		while(it.hasNext()){
			String name = it.next();
			
			java.util.List<String> stringList = configuration.getStringList(name);
			
			TypeList typeList = (stringList == null) ? new TypeList(type, manager.getList(name)) : new TypeList(type, manager.getList(name), stringList);
			manager.getList(name).addTypeList(typeList);
		}
	}
	
	public void loadList(String list, boolean reloadFromFile){
		if(reloadFromFile){
			try{
				file.createNewFile();
				configuration.load(file);
			}catch(Exception e){
				return;
			}
		}
		
		if(manager.getListsNames().contains(list)){
			java.util.List<String> stringList = configuration.getStringList(list);
			
			TypeList typeList = (stringList == null) ? new TypeList(type, manager.getList(list)) : new TypeList(type, manager.getList(list), stringList);
			manager.getList(list).addTypeList(typeList);
		}
	}
	
	public void writeAllLists(){
		Iterator<String> it = manager.getListsNames().iterator();
		
		while(it.hasNext()){
			writeList(manager.getList(it.next()), false);
		}
		saveLists();
	}
	
	public void writeList(List list, boolean save){
		TypeList typeList = list.getTypeList(type);
		
		configuration.set(list.getName(), typeList.toStringList(false, false));
		if(save)saveLists();
	}
	
	private void saveLists(){
		if(header != null){
			configuration.options().header(header).copyHeader(true);
		}
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
