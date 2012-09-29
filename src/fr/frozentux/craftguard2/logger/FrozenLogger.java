package fr.frozentux.craftguard2.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A little extension to logger, capable to send debug messages to a player
 * @author FrozenTux
 *
 */
public class FrozenLogger {
	
	private Logger logger;
	
	private Plugin plugin;
	private boolean debug, playerDebug;
	private String debugPlayerName;
	
	/**
	 * A little extension to logger, capable to send debug messages to a player
	 * This constructor disables debug to player
	 * @param plugin	The plugin using this logger
	 * @param debug		If true messages with level debug will be shown
	 */
	public FrozenLogger(Plugin plugin, boolean debug){
		this.plugin = plugin;
		this.logger = plugin.getLogger();
		this.debug = debug;
		if(debug){
			debug("Debug messages enabled. Debug-to-player disabled");
		}
	}
	
	/**
	 * A little extension to logger, capable to send debug messages to a player
	 * This constructor enables debug to player if debug is set to true
	 * @param plugin			The plugin using this logger
	 * @param debug				If true messages with level debug will be shown
	 * @param debugPlayerName	The name of the player who has to receive debug messages
	 */
	public FrozenLogger(Plugin plugin, boolean debug, String debugPlayerName){
		this.plugin = plugin;
		this.debug = debug;
		this.playerDebug = debug;
		this.debugPlayerName = debugPlayerName;
		if(debug)debug("Debug messages enabled. Debug-to-player enabled on " + debugPlayerName);
	}
	
	/**
	 * Logs an info message
	 * @param message	Message to log
	 */
	public void info(String message){
		logger.log(Level.INFO, message);
		if(playerDebug){
			debugToPlayer(message, "INFO");
		}
	}
	
	/**
	 * Logs an severe message, usually an error
	 * @param message	Message to log
	 */
	public void severe(String message){
		logger.log(Level.SEVERE, message);
		if(playerDebug){
			debugToPlayer(message, "SEVERE");
		}
	}
	
	/**
	 * Logs an debug message, only if debug has been set to true
	 * @param message	Message to log
	 */
	public void debug(String message){
		if(debug)logger.log(Level.FINE, "(Debug) " + message);
		if(playerDebug){
			debugToPlayer(message, "DEBUG");
		}
	}
	
	private void debugToPlayer(String message, String level){
		Player p = plugin.getServer().getPlayer(debugPlayerName);
		if(p != null){
			p.sendMessage(ChatColor.AQUA + "*" + level + "(" + plugin.getName() +  ") > " + ChatColor.ITALIC + message); 
		}
	}
	
}
