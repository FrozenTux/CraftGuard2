package fr.frozentux.craftguard2.list;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Data structure reprensenting a craftGuard List, with it's attributes, it's type lists and other stuff 
 * @author FrozenTux
 */
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
	
	/**
	 * Checks if the list has a {@link TypeList} for the given type
	 * @param type	The type
	 * @return	true if the {@link TypeList} exists
	 */
	public boolean typeListAvailable(String type){
		return typesLists.containsKey(type);
	}
	
	/**
	 * Checks if the commond ids list for this list contains a given {@link Id}
	 * @param id	The id to check
	 * @return		<code>true</code> if the id is contained
	 */
	public boolean containsCommonId(int id){
		return commonIds.containsKey(id);
	}
	
	/**
	 * Returns the {@link TypeList} for a give type
	 * @param type	The type
	 * @return		The {@link TypeList} if it exists; otherwise <code>null</code>
	 */
	public TypeList getTypeList(String type){
		return typesLists.get(type);
	}
	
	/**
	 * Returns an {@link Id} from the common ids list
	 * @param id	The id of the {@link Id}
	 * @return		The {@link Id} if exists; otherwise null
	 */
	public Id getCommonId(int id){
		return commonIds.get(id);
	}
	
	/**
	 * @return A {@link HashMap} containing all the common ids with the id as int as key and the {@link Id} object as value
	 */
	public HashMap<Integer, Id> getCommonIds(){
		return commonIds;
	}
	
	/**
	 * @return The name of this list
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return	The permission for this list
	 */
	public String getPermission(){
		return permission;
	}
	
	/**
	 * @return The parent list of this list if exists; otherwise null;
	 */
	public List getParent(){
		return parent;
	}
	
	/**
	 * Called to retrieve the parent list from the listManager with the given parentName in constructor
	 * Must be called after all lists are loaded
	 */
	public void registerParent(){
		parent = manager.getList(parentName);
	}
	
	/**
	 * @return The listManager storing this list
	 */
	public ListManager getListManager(){
		return manager;
	}
	
	public void addTypeList(TypeList list){
		typesLists.put(list.getType(), list);
	}
}
