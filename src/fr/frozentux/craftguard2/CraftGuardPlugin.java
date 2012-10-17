package fr.frozentux.craftguard2;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
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
	
	//Constant set to true when develloping and to false when releasing
	public static final boolean DEBUG = true;
	public static final String DEBUG_PLAYER = "FrozenTux";
	
	private Metrics metrics;
	
	private FrozenLogger frozenLogger; 
	
	private CraftGuardConfig config;
	
	private ListLoader listLoader;
	private File listFile;
	private ListManager listManager;
	
	private PlayerListener playerListener;
	private CraftPermissionChecker permissionChecker;
	
	public void onEnable(){
		//Logger init
		frozenLogger = new FrozenLogger(this, DEBUG, DEBUG_PLAYER);
		
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
		
		//ListManager init
		listFile = new File(this.getDataFolder().getAbsolutePath() + File.separator + "lists.yml");
		listLoader = new ListLoader(this, new YamlConfiguration(), listFile);
		listManager = new ListManager(this, listLoader);
		
		//Listener init
		playerListener = new PlayerListener();
		this.getServer().getPluginManager().registerEvents(playerListener, this);
		
	}
	
	public void onDisable(){
		
	}
	
	
	/*
	 * Getters
	 */
	public FrozenLogger getFrozenLogger(){
		return frozenLogger;
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
