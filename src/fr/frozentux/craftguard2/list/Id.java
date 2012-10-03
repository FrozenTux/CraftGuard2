package fr.frozentux.craftguard2.list;

import java.util.ArrayList;

/**
 * Data structure reprensenting an ID with it's metadata
 * @author FrozenTux
 *
 */
public class Id {
	
	private int id;
	private boolean overrideAllMetadata;
	private ArrayList<Integer> metadata;
	
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
	
}
