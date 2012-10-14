package fr.frozentux.craftguard2.list;

import java.util.HashMap;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.config.ListLoader;

public class ListManager {
	
	private CraftGuardPlugin plugin;
	
	private HashMap<String, List> groupsLists;
	
	private ListLoader loader;
	
	public ListManager(CraftGuardPlugin plugin, ListLoader loader){
		this.plugin = plugin;
		this.loader = loader;
	}
	
	public void init(){
		groupsLists = loader.load();
	}
	
	public List getList(String name){
		return groupsLists.get(name);
	}
	
	public void addList(List list, boolean replaceIfExisting){
		if(!groupsLists.containsKey(list.getName()) || replaceIfExisting)groupsLists.put(list.getName(), list);
	}
	
}
