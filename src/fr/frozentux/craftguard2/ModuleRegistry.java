package fr.frozentux.craftguard2;

import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.Validate;

import fr.frozentux.craftguard2.module.CraftGuardModule;

/**
 * Class that contains every module
 * @author FrozenTux
 *
 */
public class ModuleRegistry {
	
	private HashMap<String, CraftGuardModule> modules;
	
	private CraftGuardPlugin plugin;
	
	public ModuleRegistry(CraftGuardPlugin plugin){
		this.plugin = plugin;
		this.modules = new HashMap<String, CraftGuardModule>();
	}
	
	public ModuleRegistry(CraftGuardPlugin plugin, Set<CraftGuardModule> modules){
		this.plugin = plugin;
		this.modules = new HashMap<String, CraftGuardModule>();
		for(CraftGuardModule module : modules){
			this.modules.put(module.getType(), module);
		}
	}
	
	public void add(CraftGuardModule module){
		Validate.notNull(module, "The module to register can't be null");
		modules.put(module.getType(), module);
	}
	
	public boolean containsModule(String type){
		return modules.containsKey(type);
	}
	
	public CraftGuardModule getModule(String type){
		return modules.get(type);
	}
	
	public Set<String> getModulesNames(){
		return modules.keySet();
	}
}
