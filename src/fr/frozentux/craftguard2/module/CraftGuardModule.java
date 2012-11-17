package fr.frozentux.craftguard2.module;

import java.io.File;

import org.bukkit.event.Listener;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.list.TypeListLoader;

/**
 * This class represents a function of CrafGuard2 (for instance craft, use) and everything needed to make it work
 * @author FrozenTux
 *
 */
public class CraftGuardModule {

	protected Listener listener;
	
	protected TypeListLoader loader;
	
	protected String type;
	
	protected CraftGuardPlugin plugin;
	
	public CraftGuardModule(String type, Listener listener, CraftGuardPlugin plugin){
		this.type = type.toLowerCase();
		this.listener = listener;
		File file = new File("plugins" + File.separator + "CraftGuard" + File.separator + type + ".yml");
		this.loader = new TypeListLoader(type, file, plugin.getListManager(), "CraftGuard " + type + " file\nBy FrozenTux");
	}
	
	public String getType(){
		return type;
	}
	
	public Listener getListener(){
		return listener;
	}
	
	public TypeListLoader getLoader(){
		return loader;
	}
	
	public CraftGuardPlugin getCraftguard(){
		return plugin;
	}
	
}
