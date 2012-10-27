package fr.frozentux.craftguard2.list.craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class CraftListManager {
	
	private CraftGuardPlugin plugin;
	
	private HashMap<String, CraftList> groupsLists;
	
	private ArrayList<Integer> checkList;
	
	private CraftListLoader loader;
	
	public CraftListManager(CraftGuardPlugin plugin, CraftListLoader loader){
		this.plugin = plugin;
		this.loader = loader;
	}
	
	public void init(){
		checkList = new ArrayList<Integer>();
		groupsLists = loader.load();
		
		Iterator<CraftList> it = groupsLists.values().iterator();
		while(it.hasNext()){
			it.next().registerParent();
		}
	}
	
	public CraftList getList(String name){
		return groupsLists.get(name);
	}
	
	public void addList(CraftList craftList, boolean replaceIfExisting){
		if(!groupsLists.containsKey(craftList.getName()) || replaceIfExisting){
			Iterator<Integer> it = craftList.getIds(false).keySet().iterator();
			while(it.hasNext()){
				int id = it.next();
				if(!checkList.contains(id))checkList.add(id);
			}
			groupsLists.put(craftList.getName(), craftList);
		}
	}
	
	public void addIdToCheckList(int id){
		if(!checkList.contains(id))checkList.add(id);
	}
	
	public boolean inCheckList(int id){
		return checkList.contains(id);
	}
	
	public Set<String> getListsNames(){
		return groupsLists.keySet();
	}
	
	public void saveList(CraftList craftList){
		loader.writeList(craftList, true);
	}
	
	public void saveLists(){
		loader.writeAllLists(this);
	}
	
	public void removeList(String list){
		groupsLists.remove(list);
	}
	
}
