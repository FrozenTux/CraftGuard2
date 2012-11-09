package fr.frozentux.craftguard2.list;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.frozentux.craftguard2.api.CraftGuard2Module;

/**
 * Loader for a specific type of TypeList for a CraftGuard module
 * @author FrozenTux
 *
 */
public class TypeListLoader {
	
	protected String type;
	
	protected CraftGuard2Module module;
	
	protected ListManager manager;
	
	protected File file;
	protected FileConfiguration configuration;
	
	/**
	 * @param type		The type of the typeList this loader will load
	 * @param file		A File object pointing at the location where the configuration file is.
	 * 					It should point at <code>(CraftGuard Folder)/(type).yml</code>
	 * @param module	The module using this loader
	 */
	public TypeListLoader(String type, File file, CraftGuard2Module module){
		this.type = type;
		this.file = file;
		this.configuration = new YamlConfiguration();
		this.manager = module.getCraftGuard().getListManager();
		this.module = module;
	}

	public void loadAllLists(){
		try{
			file.createNewFile();
			configuration.load(file);
		}catch(Exception e){
			return;
		}
			
		Iterator<String> it = manager.getListsNames().iterator();
		
		while(it.hasNext()){
			String name = it.next();
			
			java.util.List<String> stringList = configuration.getStringList(name);
			
			TypeList typeList = (stringList == null) ? new TypeList(type, manager.getList(name)) : new TypeList(type, manager.getList(name), stringList);
			manager.getList(name).addTypeList(typeList);
		}
	}
	
	public void loadList(String list, boolean reloadFromFile){
		if(reloadFromFile){
			try{
				file.createNewFile();
				configuration.load(file);
			}catch(Exception e){
				return;
			}
		}
		
		if(manager.getListsNames().contains(list)){
			java.util.List<String> stringList = configuration.getStringList(list);
			
			TypeList typeList = (stringList == null) ? new TypeList(type, manager.getList(list)) : new TypeList(type, manager.getList(list), stringList);
			manager.getList(list).addTypeList(typeList);
		}
	}
	
	public void writeAllLists(){
		Iterator<String> it = manager.getListsNames().iterator();
		
		while(it.hasNext()){
			writeList(manager.getList(it.next()), false);
		}
		saveLists();
	}
	
	public void writeList(List list, boolean save){
		TypeList typeList = list.getTypeList(type);
		
		configuration.set(list.getName(), typeList.toStringList(false, false));
		if(save)saveLists();
	}
	
	private void saveLists(){
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
