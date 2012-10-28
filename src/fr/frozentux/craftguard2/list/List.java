package fr.frozentux.craftguard2.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class List {
	
	private String name, permission, parentName;
	
	private List parent;
	
	private HashMap<Integer, Id> commonIds;
	
	private HashMap<String, TypeList> typesLists;
	
	private ListManager manager;
	
	public List(String name, String permission, String parent, ListManager manager){
		this.name = name;
		this.permission = permission;
		this.parentName = parent;
		this.manager = manager;
		this.commonIds = new HashMap<Integer, Id>();
		this.typesLists = new HashMap<String, TypeList>();
	}
	
	public List(String name, String permission, String parent, java.util.List<String> list, ListManager manager){
		this.name = name;
		this.permission = permission;
		this.parentName = parent;
		this.manager = manager;
		this.commonIds = new HashMap<Integer, Id>();
		this.typesLists = new HashMap<String, TypeList>();
		
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			Id id = new Id(it.next());
			commonIds.put(id.getId(), id);
		}
	}
	
	public boolean typeListAvailable(String type){
		return typesLists.containsKey(type);
	}
	
	public boolean containsCommonId(int id){
		return commonIds.containsKey(id);
	}
	
	public TypeList getTypeList(String type){
		return typesLists.get(type);
	}
	
	public Id getCommonId(int id){
		return commonIds.get(id);
	}
	
	public String getName(){
		return name;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public List getParent(){
		return parent;
	}
	
	public void registerParent(){
		parent = manager.getList(parentName);
	}
}
