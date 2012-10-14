package fr.frozentux.craftguard2.smeltingmanager;

import java.io.File;
import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class SmeltFile {
	
	private CraftGuardPlugin plugin;
	
	private FileConfiguration file;
	private File smeltingFile;
	
	private int[] baseSmelting = {5, 319, 363, 365, 349, 15,  14,  56,  21,  73,  16,  12, 4, 337, 17,  81};
	private int[] baseResult =  {1, 320, 364, 366, 350, 265, 266, 264, 351, 331, 263, 20, 1, 336, 263, 351};
	
	public SmeltFile(FileConfiguration file, File smeltFile, CraftGuardPlugin plugin){
		this.file = file;
		this.smeltingFile = smeltFile;
		this.plugin = plugin;
	}
	
	public void load(){
		int baseCount = 0, customCount = 0;
		
		for(int i = 0 ; i < baseSmelting.length ; i++){
			SmeltReference.getReference().addSmelting(baseSmelting[i], baseResult[i]);
			baseCount++;
		}
		
		if(!smeltingFile.exists())return;
		
		Iterator<String> it = file.getKeys(false).iterator();
		
		while(it.hasNext()){
			String key = it.next();
			if(isInt(key) || isInt(String.valueOf(file.get(key)))){
				SmeltReference.getReference().addSmelting(Integer.valueOf(key), file.getInt(key));
				customCount++;
			}
		}
		
		if(customCount != 0)plugin.getFrozenLogger().info("Smelting Manager : Loaded " + baseCount + customCount + " smeltings (" + customCount + " custom)");
		
	}
	
	private boolean isInt(String toTest){
		try{
			Integer.parseInt(toTest);
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
}
