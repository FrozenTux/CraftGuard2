package fr.frozentux.craftguard2;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import fr.frozentux.craftguard2.config.*;
import fr.frozentux.craftguard2.list.*;
import fr.frozentux.craftguard2.listener.*;
import fr.frozentux.craftguard2.logger.*;

/**
 * CraftGuard 2 Plugin
 * @author FrozenTux
 *
 */
public class CraftGuardPlugin extends JavaPlugin {
	
	private Metrics metrics;
	
	private CraftGuardLogger craftGuardLogger; 
	
	private CraftGuardConfig config;
	
	private ListLoader listLoader;
	private File listFile;
	private ListManager listManager;
	
	private PlayerListener playerListener;
	private CraftPermissionChecker permissionChecker;
	
	public void onEnable(){
		//Logger init
		craftGuardLogger = new CraftGuardLogger(this);
		
		//Metrics init
		try {
			metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Configuration init
		config = new CraftGuardConfig(this);
		config.load();
		if(config.getBooleanKey("debug") == true)craftGuardLogger.enableDebug();
		
		//ListManager init
		listFile = new File(this.getDataFolder().getAbsolutePath() + File.separator + "lists.yml");
		listLoader = new ListLoader(this, new YamlConfiguration(), listFile);
		listManager = new ListManager(this, listLoader);
		
		//Listener init
		playerListener = new PlayerListener(this);
		this.getServer().getPluginManager().registerEvents(playerListener, this);
		
		craftGuardLogger.info("CraftGuard version " + this.getDescription().getVersion() + " has been enabled");
		
	}
	
	public void onDisable(){
		craftGuardLogger.info("CraftGuard version " + this.getDescription().getVersion() + " has been disabled");
	}
	
	
	/*
	 * Getters
	 */
	public CraftGuardLogger getCraftGuardLogger(){
		return craftGuardLogger;
	}
	
	public CraftGuardConfig getConfiguration(){
		return config;
	}
	
	public ListManager getListManager(){
		return listManager;
	}
	
	public CraftPermissionChecker getPermissionChecker(){
		return permissionChecker;
	}
	
}
