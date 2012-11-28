package fr.frozentux.craftguard2.module;

import org.bukkit.event.Listener;

import fr.frozentux.craftguard2.CraftGuardPlugin;

/**
 * This class represents a function of CrafGuard2 (for instance craft, use) and everything needed to make it work
 * @author FrozenTux
 *
 */
public class CraftGuardModule {

	protected Listener listener;
	
	protected String type;
	
	protected CraftGuardPlugin plugin;
	
	public CraftGuardModule(String type, Listener listener, CraftGuardPlugin plugin){
		this.type = type.toLowerCase();
		this.listener = listener;
	}
	
	public String getType(){
		return type;
	}
	
	public Listener getListener(){
		return listener;
	}
	
	public CraftGuardPlugin getCraftguard(){
		return plugin;
	}
	
}
