package fr.frozentux.craftguard2;

import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.Validate;

import fr.frozentux.craftguard2.module.CraftGuard2Module;

/**
 * Class that contains every module
 * @author FrozenTux
 *
 */
public class ModuleRegistry {
	
	private HashMap<String, CraftGuard2Module> modules;
	
	private CraftGuardPlugin plugin;
	
	public ModuleRegistry(CraftGuardPlugin plugin){
		this.plugin = plugin;
		this.modules = new HashMap<String, CraftGuard2Module>();
	}
	
	public ModuleRegistry(CraftGuardPlugin plugin, Set<CraftGuard2Module> modules){
		this.plugin = plugin;
		this.modules = new HashMap<String, CraftGuard2Module>();
		for(CraftGuard2Module module : modules){
			this.modules.put(module.getType(), module);
		}
	}
	
	public void add(CraftGuard2Module module){
		Validate.notNull(module, "The module to register can't be null");
		modules.put(module.getType(), module);
	}
	
	public boolean containsModule(String type){
		return modules.containsKey(type);
	}
	
	public CraftGuard2Module getModule(String type){
		return modules.get(type);
	}
	
	public Set<String> getModulesNames(){
		return modules.keySet();
	}
}
