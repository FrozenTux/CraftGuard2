package fr.frozentux.craftguard2.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Material;

/**
 * Base structure for a "type list", a list containing all values from a specific perspective (for instance, craft, use, ...)
 * @author FrozenTux
 *
 */
public class TypeList {
	
	protected List list;
	
	protected String type;
	
	protected HashMap<Integer, Id> ids;
	
	public TypeList(String type, List list){
		this.ids = new HashMap<Integer, Id>();
		this.list = list;
		this.type = type;
	}
	
	public TypeList(String type, List list, HashMap<Integer, Id> ids){
		this.ids = ids;
		this.list = list;
		this.type = type;
	}
	
	public TypeList(String type, List list, java.util.List<String> ids){
		this.ids = new HashMap<Integer, Id>();
		this.list = list;
		
		Iterator<String> it = ids.iterator();
		while(it.hasNext()){
			this.addId(new Id(it.next()));
		}
		
		this.type = type;
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
			list.getListManager().getCheckList(type).addId(id.getId());
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
	 * @param containParent	If true it will also take the ids from the parent list in account
	 * @param containCommon If true it will also take common ids from the {@link List} in account
	 * @return	true if the list contains the id
	 */
	public boolean containsId(int id, boolean containParent, boolean containCommon){
		return ((list.containsCommonId(id) && containCommon) || ids.containsKey(id) || (containParent && list.getParent() != null && list.getParent().getTypeList(type).containsId(id, true, true)));
	}
	
	/**
	 * Returns an Id object from the list based on a given id
	 * @param id	The id to get
	 * @return	The id object corresponding to the given id if the list contains it; null otherwise
	 */
	public Id getId(int id, boolean containParent, boolean containCommon){
		boolean parent = false, common = false, contains = false;
		
		parent = (list.getParent() != null && list.getParent().getTypeList(getType()).containsId(id, true, true) && containParent);
		common = (list.containsCommonId(id) && containCommon);
		contains = this.containsId(id, false, false);
		
		if(!parent && !common)return ids.get(id);
		else{
			Id toReturn = new Id(id);
			
			if(parent)toReturn = list.getParent().getTypeList(getType()).getId(id, true, true);
			if(common)toReturn = toReturn.merge(list.getCommonId(id));
			if(contains)toReturn = toReturn.merge(ids.get(id));
			return toReturn;
		}
				
		
		
	}
	
	/**
	 * Returns the whole list of ids
	 * If this list has a parent, the returned map will contain ids from the parent list as well
	 * @return	A map of the ids
	 */
	public HashMap<Integer, Id> getIds(boolean containParents, boolean containCommon){
		if(!containCommon || (!containParents || list.getParent() == null))return ids;
		
		HashMap<Integer, Id> mergedMap = ids;
		
		if(containParents && list.getParent() != null){
			Iterator<Integer> it = list.getParent().getTypeList(getType()).getIds(true, true).keySet().iterator();
			
			while(it.hasNext()){
				int id = it.next();
				if(mergedMap.containsKey(id)) mergedMap.put(id, mergedMap.get(id).merge(list.getParent().getTypeList(getType()).getId(id, true, true)));
				else mergedMap.put(id, list.getParent().getTypeList(getType()).getId(id, true, true));
			}
		}
		
		if(containCommon){
			Iterator<Integer> it = list.getCommonIds().keySet().iterator();
			
			while(it.hasNext()){
				int id = it.next();
				if(mergedMap.containsKey(id)) mergedMap.put(id, mergedMap.get(id).merge(list.getCommonId(id)));
				else mergedMap.put(id, list.getCommonId(id));
			}
		}
		
		
		
		return mergedMap;
	
	}
	
	/**
	 * Returns a string containing the list under text format as follows :<br/>
	 * <code>name:<br/>
	 * - ELEMENT_NAME(ID)<br/>
	 * - ...<br/></code>
	 * @param containingParent	Does the returned string has to contain values from the parent
	 * @param containCommon	Does the returned string has to contain common values from the list
	 * @return	A {@link String} formatted as above
	 */
	public String toString(boolean containingParent, boolean containCommon){
		String result = this.list.getName() + ":\n";
		Iterator<Id> it = this.getIds(containingParent, containCommon).values().iterator();
		
		while(it.hasNext()){
			Id id = it.next();
			result = result + "- " + Material.getMaterial(id.getId()).name() + "(" + id.toString() + ")\n"; 
		}
		
		return result;
	}
	
	/**
	 * Returns the list formatted as a string {@link java.util.List}<br/>
	 * Each element is formatted like that : <code>id:metadata:metadata:...</code>
	 * @param containingParent	Does the returned list has to contain values from the parent
	 * @param containingParent	Does the returned list has to contain common values from the list
	 * @return	The list
	 */
	public java.util.List<String> toStringList(boolean containingParent, boolean containingCommon){
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Id> it = this.getIds(containingParent, containingCommon).values().iterator();
		
		while(it.hasNext()){
			result.add(it.next().toString());
		}
		
		return result;
	}
	
	/**
	 * Returns the type of the list as specified by this {@link TypeList} implementation class
	 * @return	The type, as a string
	 */
	public String getType(){
		return type;
	}
	
}
