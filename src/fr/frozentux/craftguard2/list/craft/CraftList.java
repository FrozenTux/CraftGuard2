package fr.frozentux.craftguard2.list.craft;

import java.util.HashMap;
import java.util.Iterator;

import fr.frozentux.craftguard2.list.List;
import fr.frozentux.craftguard2.list.Id;
import fr.frozentux.craftguard2.list.TypeList;

/**
 * Type List storing values for the craft perspective
 * @author FrozenTux
 *
 */
public class CraftList extends TypeList{
	
	protected static String type = "craft";
		
	public CraftList(List list){
		this.ids = new HashMap<Integer, Id>();
		this.list = list;
	}
	
	public CraftList(List list, HashMap<Integer, Id> ids){
		this.ids = ids;
		this.list = list;
	}
	
	public CraftList(List list, java.util.List<String> ids){
		this.ids = new HashMap<Integer, Id>();
		this.list = list;
		
		Iterator<String> it = ids.iterator();
		while(it.hasNext()){
			this.addId(new Id(it.next()));
		}
	}
	
}
