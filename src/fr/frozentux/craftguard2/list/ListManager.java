package fr.frozentux.craftguard2.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class ListManager {
	
	private CraftGuardPlugin plugin;
	
	private HashMap<String, List> lists;
	
	private ListLoader loader;
	
	public ListManager(CraftGuardPlugin plugin, ListLoader loader){
		this.plugin = plugin;
		this.loader = loader;
	}
	
	public void init(){
		checkList = new ArrayList<Integer>();
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
			Iterator<Integer> it = list.getIds(false).keySet().iterator();
			while(it.hasNext()){
				int id = it.next();
				//TODO ADD to checkList. But we need a CheckList first
			}
			lists.put(list.getName(), list);
		}
	}
	
	public void addIdToCheckList(int id){
		if(!checkList.contains(id))checkList.add(id);
	}
	
	public boolean inCheckList(int id){
		return checkList.contains(id);
	}
	
	public Set<String> getListsNames(){
		return lists.keySet();
	}
	
	public void saveList(List list){
		loader.writeList(craftList, true);
	}
	
	public void saveLists(){
		loader.writeAllLists(this);
	}
	
	public void removeList(String list){
		lists.remove(list);
	}
	
}
