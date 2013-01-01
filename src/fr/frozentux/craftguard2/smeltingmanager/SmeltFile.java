package fr.frozentux.craftguard2.smeltingmanager;

import java.io.File;
import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class SmeltFile {
	
	private CraftGuardPlugin plugin;
	
	private FileConfiguration file;
	private File smeltingFile;
	
	private int[] baseSmelting = {319, 363, 365, 349, 15,  14,  56,  21,  73,  16,  12, 4, 337, 17,  81};
	private int[] baseResult =  {320, 364, 366, 350, 265, 266, 264, 351, 331, 263, 20, 1, 336, 263, 351};
	
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
				plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Integer.valueOf(key)), Material.getMaterial(file.getInt(key))));
				customCount++;
			}
		}
		
		if(customCount != 0)plugin.getCraftGuardLogger().info("Smelting Manager : Applied " + customCount + " smeltings");
		
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
