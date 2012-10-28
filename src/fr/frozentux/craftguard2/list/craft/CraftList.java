package fr.frozentux.craftguard2.list.craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Material;

import fr.frozentux.craftguard2.list.List;
import fr.frozentux.craftguard2.list.ListManager;
import fr.frozentux.craftguard2.list.Id;
import fr.frozentux.craftguard2.list.TypeList;

/**
 * Data structure that reprensents a CraftGuard list/group
 * @author FrozenTux
 *
 */
public class CraftList extends TypeList{
		
	private List list;
	
	private HashMap<Integer, Id> ids;
	
	public CraftList(List list){
		this.ids = new HashMap<Integer, Id>();
		this.list = list;
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
		return (ids.containsKey(id) || (list.getParent() != null && ((CraftList) list.getParent().getTypeList(getType())).containsId(id)));
	}
	
	/**
	 * Returns an Id object from the list based on a given id
	 * @param id	The id to get
	 * @return	The id object corresponding to the given id if the list contains it; null otherwise
	 */
	public Id getId(int id){
		if(list.getParent() != null && ((CraftList) list.getParent().getTypeList(getType())).containsId(id)){
			if(ids.containsKey(id))return ids.get(id).merge(((CraftList) list.getParent().getTypeList(getType())).getId(id));
			else return ((CraftList) list.getParent().getTypeList(getType())).getId(id);
		}
		else return ids.get(id);
	}
	
	/**
	 * Returns the whole list of ids
	 * If this list has a parent, the returned map will contain ids from the parent list as well
	 * @return	A map of the ids
	 */
	public HashMap<Integer, Id> getIds(boolean containParents){
		if(!containParents || list.getParent() == null)return ids;
		
		HashMap<Integer, Id> mergedMap = ids;
		Iterator<Integer> it = ((CraftList) list.getParent().getTypeList(getType())).getIds(true).keySet().iterator();
		
		while(it.hasNext()){
			int id = it.next();
			if(mergedMap.containsKey(id)) mergedMap.put(id, mergedMap.get(id).merge(((CraftList) list.getParent().getTypeList(getType())).getId(id)));
			else mergedMap.put(id, ((CraftList) list.getParent().getTypeList(getType())).getId(id));
		}
		
		return mergedMap;
	}
	
	public String toString(boolean containingParent){
		String result = this.list.getName() + ":\n";
		Iterator<Id> it = this.getIds(containingParent).values().iterator();
		
		while(it.hasNext()){
			Id id = it.next();
			result = result + "- " + Material.getMaterial(id.getId()).name() + "(" + id.toString() + ")\n"; 
		}
		
		return result;
	}
	
	public java.util.List<String> toStringList(boolean containingParent){
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Id> it = this.getIds(containingParent).values().iterator();
		
		while(it.hasNext()){
			result.add(it.next().toString());
		}
		
		return result;
	}
	
}
