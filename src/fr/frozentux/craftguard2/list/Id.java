package fr.frozentux.craftguard2.list;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Data structure reprensenting an ID with it's metadata
 * @author FrozenTux
 *
 */
public class Id {
	
	private int id;
	private boolean overrideAllMetadata;
	private ArrayList<Integer> metadata;
	
	public Id(int id){
		this.id = id;
		this.metadata = new ArrayList<Integer>();
		this.overrideAllMetadata = true;
	}
	
	/**
	 * Data structure reprensenting an ID with it's metadata
	 * @param id		
	 * @param metadata
	 */
	public Id(int id, ArrayList<Integer> metadata){
		this.id = id;
		this.metadata = metadata;
		this.overrideAllMetadata = false;
	}
	
	/**
	 * Adds a metadata to the metadata list
	 * @param metadata	The metadata to add
	 */
	public void addMetadata(int metadata){
		this.overrideAllMetadata = false;
		if(!this.metadata.contains(metadata))this.metadata.add(metadata);
	}
	
	/**
	 * Removes a metadata from the metadata list
	 * @param metadata	The metadata to remove
	 */
	public void removeMetadata(int metadata){
		this.metadata.remove(metadata);
	}
	
	/**
	 * Check if this Id has a specified metadata
	 * @param metadata	The metadata to check
	 * @return	If the id contains the metadata
	 */
	public boolean hasMetadata(int metadata){
		return (overrideAllMetadata || this.metadata.contains(metadata));
	}
	
	/**
	 * Returns a copy of the metadata list
	 * @return	The copy (obtained by <code>ArrayList.clone()</code>) of the metadata list
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Integer> getMetadata(){
		return (ArrayList<Integer>) metadata.clone();
	}
	
	/**
	 * Returns the id of this object
	 * @return	The id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Returns a string representing this id object under the form :<br/>
	 * <code>"id:metadata:metadata:[...]"</code>
	 */
	public String toString(){
		String result = String.valueOf(id);
		if(!this.overrideAllMetadata){
			Iterator<Integer> it = metadata.iterator();
			while(it.hasNext()){
				result = result + ":" + it.next();
			}
		}
		
		return result;
	}
	
	/**
	 * Merge this id's metadata with an other id
	 * @param otherId	The id to merge with
	 * @return			The merged id; or self if ids are not of the same type
	 */
	public Id merge(Id otherId){
		if(this.id != otherId.getId())return this;
		
		Id merged = new Id(id, metadata);
		Iterator<Integer> it = otherId.getMetadata().iterator();
		while(it.hasNext())merged.addMetadata(it.next());
		
		return merged;
	}
	
}
