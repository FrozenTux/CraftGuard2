package fr.frozentux.craftguard2;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import fr.frozentux.craftguard2.logger.FrozenLogger;

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
	
	public void onEnable(){
		
		frozenLogger = new FrozenLogger(this, DEBUG, DEBUG_PLAYER);
		
		//Metrics init
		try {
			metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void onDisable(){
		
	}
	
	public FrozenLogger getFrozenLogger(){
		return frozenLogger;
	}
	
}
