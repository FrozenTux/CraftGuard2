package fr.frozentux.craftguard2.list;

import java.util.ArrayList;
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
	private ArrayList<List> childs;
	
	private HashMap<Integer, Id> ids;
	
	/**
	 * Data structure that reprensents a CraftGuard list/group
	 * @param name			The name of the list
	 * @param permission	The permission of the list
	 * @param ids			The Hashmap containing the ids and data values
	 * @param parent		The parent group (may be null if no parent)
	 */
	public List(String name, String permission,  HashMap<Integer, Id> ids, List parent){
		this.name = name;
		this.permission = permission;
		this.parent = parent;
		this.ids = ids;
		childs = new ArrayList<List>();
		if(parent != null)parent.registerChild(this);
	}
	
	/**
	 * Tries to add an id to the list.
	 * If the id already exists, the metadata from the id parameter will be added to the list.
	 * @param id	The id to add
	 */
	public void addId(Id id){
		if(ids.containsKey(id.getId())){
			ArrayList<Integer> metadata = id.getMetadata();
			Iterator<Integer> i = metadata.iterator();
			while(i.hasNext()){
				ids.get(id.getId()).addMetadata(i.next());
			}
		}else ids.put(id.getId(), id);
		notifyChilds();
	}
	
	/**
	 * Removes a given id object with all it's metadata 
	 * @param id
	 */
	public void removeId(int id){
		ids.remove(id);
		notifyChilds();
	}
	
	/**
	 * Checks if this group contains a specified id
	 * @param id	The id to check
	 * @return	true if the list contains the id
	 */
	public boolean containsId(int id){
		return ids.containsKey(id);
	}
	
	/**
	 * Returns an Id object from the list based on a given id
	 * @param id	The id to get
	 * @return	The id object corresponding to the given id if the list contains it; null otherwise
	 */
	public Id getId(int id){
		return ids.get(id);
	}
	
	/**
	 * Returns the whole list of ids
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Integer, Id> getIds(){
		return (HashMap<Integer, Id>) ids.clone();
	}
	
	/**
	 * Registers a child of this class to be notified when the list updates
	 * @param child	Child to register
	 */
	public void registerChild(List child){
		childs.add(child);
	}
	
	/**
	 * This method is called by a parent class when it has been updated
	 */
	public void onParentUpdate(){
		//TODO
	}
	
	private void notifyChilds(){
		Iterator<List> it = childs.iterator();
		while(it.hasNext()){
			it.next().onParentUpdate();
		}
	}
	
}
