package fr.frozentux.craftguard2.api;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.list.TypeList;
import fr.frozentux.craftguard2.list.TypeListLoader;
import fr.frozentux.craftguard2.logger.CraftGuardLogger;

/**
 * CraftGuard 2 base module
 * @author FrozenTux
 *
 */
public abstract class CraftGuard2Module extends JavaPlugin {
	
	protected String type;
	
	protected CraftGuardPlugin craftGuard;
	
	protected CraftGuardLogger logger;
	
	protected TypeListLoader loader;
	
	public void onEnable(){
		Plugin plugin = this.getServer().getPluginManager().getPlugin("CraftGuard");
		if(plugin == null){
			this.getLogger().severe("CraftGuard is missing ! Disabling...");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		craftGuard = (CraftGuardPlugin)plugin;
		
		CraftGuardLogger logger = new CraftGuardLogger(this);
		if(craftGuard.getConfiguration().getBooleanKey("debug"))logger.enableDebug();
		
		if(type == null){
			logger.severe("Class attribute type is not defined !");
			this.getServer().getPluginManager().disablePlugin(this);
		}
		
		File file = new File(craftGuard.getDataFolder().getAbsolutePath() + File.separator + type + ".yml");
		
		loader = new TypeListLoader(type, file, this);
	}
	
	public CraftGuardLogger getCraftGuardLogger(){
		return logger;
	}
	
	public CraftGuardPlugin getCraftGuard(){
		return craftGuard;
	}

}
