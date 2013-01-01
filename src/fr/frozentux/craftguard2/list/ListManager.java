package fr.frozentux.craftguard2.list;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.module.CraftGuardModule;

public class ListManager {
	
	private CraftGuardPlugin plugin;
	
	private HashMap<String, List> lists;
	
	private Set<Integer> checkList;
	
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
		
		generateCheckList();
	}
	
	public List getList(String name){
		return lists.get(name);
	}
	
	public void addList(List list, boolean replaceIfExisting){
		if(!lists.containsKey(list.getName()) || replaceIfExisting){
			lists.put(list.getName(), list);
			this.saveList(list);
		}
	}
	
	public Set<String> getListsNames(){
		return lists.keySet();
	}
	
	public void saveList(List list){
		loader.writeList(list, true);
	}
	
	public void saveLists(){
		loader.writeAllLists();
	}
	
	public void removeList(String list){
		lists.remove(list);
		this.saveLists();
	}
	
	public Set<Integer> getCheckList(){
		return checkList;
	}
	
	public void addToCheckList(int id){
		checkList.add(id);
	}
	
	public boolean inCheckList(int id){
		return checkList.contains(id);
	}
	
	public void generateCheckList(){
		//First we will generate the default list
		Iterator<String> listNames = getListsNames().iterator();
		checkList = new HashSet<Integer>();
		
		while(listNames.hasNext()){
			List list = getList(listNames.next());
			Iterator<Integer> idIterator = list.getIds(false).keySet().iterator();
			while(idIterator.hasNext())addToCheckList(idIterator.next());
			if(list.hasTypeLists()){
				Iterator<CraftGuardModule> enabledIterator = plugin.getEnabledModules().iterator();
				while(enabledIterator.hasNext()){
					String module = enabledIterator.next().getType();
					if(list.typeListAvailable(module)){
						Iterator<Integer> typeListIterator = list.getTypeList(module, false).keySet().iterator();
						while(typeListIterator.hasNext())addToCheckList(typeListIterator.next());
					}
				}
			}
		}
	}
	
}
