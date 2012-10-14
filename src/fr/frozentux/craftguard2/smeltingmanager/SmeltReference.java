package fr.frozentux.craftguard2.smeltingmanager;

import java.util.HashMap;

/**
 * Class storing what an object gives when smelted
 * @author FrozenTux
 *
 */
public class SmeltReference {
	
	private static HashMap<Integer, Integer> reference = new HashMap<Integer, Integer>();
	
	private static SmeltReference singleton = new SmeltReference();
	
	public static SmeltReference getReference(){
		return singleton;
	}
	
	/**
	 * Get what the following id will give when smelted
	 * @param id	The id to check
	 * @return		The smelting value or the id itself if not registered
	 */
	public int getSmelting(int id){
		Integer result = reference.get(id);
		if(result != null)return result;
		else return id;
	}
	
	public void addSmelting(int id, int result){
		reference.put(id, result);
	}
	
	public void clearReference(){
		reference = new HashMap<Integer, Integer>();
	}

}
