package fr.frozentux.craftguard2.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class ListManager {
	
	private CraftGuardPlugin plugin;
	
	private HashMap<String, List> lists;
	
	private HashMap<String, CheckList> checkLists;
	
	private ListLoader loader;
	
	public ListManager(CraftGuardPlugin plugin, ListLoader loader){
		this.plugin = plugin;
		this.loader = loader;
	}
	
	public void init(){
		lists = loader.load();
		
		Iterator<List> it = lists.values().iterator();
		while(it.hasNext()){
			it.next().registerParent();
		}
	}
	
	public List getList(String name){
		return lists.get(name);
	}
	
	public void addList(List list, boolean replaceIfExisting){
		if(!lists.containsKey(list.getName()) || replaceIfExisting){
			lists.put(list.getName(), list);
		}
	}
	
	public Set<String> getListsNames(){
		return lists.keySet();
	}
	
	public void saveList(List list){
		loader.writeList(list, true);
	}
	
	public void saveLists(){
		loader.writeAllLists(this);
	}
	
	public void removeList(String list){
		lists.remove(list);
	}
	
	public void registerCheckList(String type, CheckList checkList){
		checkLists.put(type, checkList);
	}
	
	public CheckList getCheckList(String type){
		return checkLists.get(type);
	}
	
}
