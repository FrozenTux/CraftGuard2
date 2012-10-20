package fr.frozentux.craftguard2.list;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Data structure that reprensents a CraftGuard list/group
 * @author FrozenTux
 *
 */
public class List {
	
	private String name, permission;
	private List parent;
	private ListManager manager;
	
	private HashMap<Integer, Id> ids;
	
	/**
	 * Data structure that reprensents a CraftGuard list/group
	 * This constructor initializes an empty list
	 * @param name			The name of the list
	 * @param permission	The permission of the list
	 * @param parent		The parent list (may be null if no parent)
	 * @param manager		The {@link ListManager} storing this list
	 */
	public List(String name, String permission, List parent, ListManager manager){
		this.name = name;
		this.permission = (permission == null) ? name : permission;
		this.parent = parent;
		this.ids = new HashMap<Integer, Id>();
		this.manager = manager;
	}
	
	/**
	 * Data structure that reprensents a CraftGuard list/group
	 * @param name			The name of the list
	 * @param permission	The permission of the list
	 * @param ids			The Hashmap containing the ids and data values
	 * @param parent		The parent list (may be null if no parent)
	 * @param manager		The {@link ListManager} storing this list
	 */
	public List(String name, String permission,  HashMap<Integer, Id> ids, List parent, ListManager manager){
		this.name = name;
		this.permission = (permission == null) ? name : permission;
		this.parent = parent;
		this.ids = ids;
	}
	
	/**
	 * Tries to add an id to the list.
	 * If the id already exists, the metadata from the id parameter will be added to the list.
	 * @param id	The id to add
	 */
	public void addId(Id id){
		if(ids.containsKey(id.getId())){
			Iterator<Integer> i = id.getMetadata().iterator();
			while(i.hasNext()){
				ids.get(id.getId()).addMetadata(i.next());
			}
		}else{
			ids.put(id.getId(), id);
			manager.addIdToCheckList(id.getId());
		}
		
	}
	
	/**
	 * Removes a given id object with all it's metadata 
	 * @param id
	 */
	public void removeId(int id){
		ids.remove(id);
	}
	
	/**
	 * Checks if this group contains a specified id
	 * @param id	The id to check
	 * @return	true if the list contains the id
	 */
	public boolean containsId(int id){
		return (ids.containsKey(id) || (parent != null && parent.containsId(id)));
	}
	
	/**
	 * Returns an Id object from the list based on a given id
	 * @param id	The id to get
	 * @return	The id object corresponding to the given id if the list contains it; null otherwise
	 */
	public Id getId(int id){
		if(parent != null && parent.containsId(id))return ids.get(id).merge(parent.getId(id));
		else return ids.get(id);
	}
	
	/**
	 * Returns the whole list of ids
	 * If this list has a parent, the returned map will contain ids from the parent list as well
	 * @return	A map of the ids
	 */
	public HashMap<Integer, Id> getIds(boolean containParents){
		if(!containParents || parent == null)return ids;
		
		HashMap<Integer, Id> mergedMap = ids;
		Iterator<Integer> it = parent.getIds(true).keySet().iterator();
		
		while(it.hasNext()){
			int id = it.next();
			if(mergedMap.containsKey(id)) mergedMap.put(id, mergedMap.get(id).merge(parent.getId(id)));
			else mergedMap.put(id, parent.getId(id));
		}
		
		return mergedMap;
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
}
