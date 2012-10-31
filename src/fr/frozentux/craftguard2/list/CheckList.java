package fr.frozentux.craftguard2.list;

import java.util.ArrayList;

/**
 * A standard list that contains ids
 * Intended to be used when you need to store all values used at least once to do things like BlackListing
 * Can be extended to store more information
 * 
 * Used in {@link ListManager}
 * @author FrozenTux
 *
 */
public class CheckList {
	
	private ArrayList<Integer> list;
	
	public CheckList(){
		this.list = new ArrayList<Integer>();
	}
	
	public CheckList(ArrayList<Integer> list){
		this.list = list;
	}
	
	/**
	 * Adds a the given {@link Id} to the list if the list does not already contains it.
	 * @param id	The id to add
	 */
	public void addId(int id){
		if(!list.contains(id))list.add(id);
	}
	
	/**
	 * Checks if an {@link Id} is contained into this list
	 * @param id	The id to check
	 * @return		<code>true</code> if the id is contained in the list
	 */
	public boolean containsId(int id){
		return list.contains(id);
	}
	
	/**
	 * Removes an id from the list
	 * @param id	The id to remove
	 */
	public void removeId(int id){
		list.remove(id);
	}
}
