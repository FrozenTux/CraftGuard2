package fr.frozentux.craftguard2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public abstract class CgCommandComponent {
	
	protected static final String MESSAGE_PERMISSION = ChatColor.RED + "You can't do that !";
	protected static final String MESSAGE_ARGUMENTS = ChatColor.RED + "Invalid arguments. ";
	
	public static boolean execute(CommandSender sender, String command, String[] args, CraftGuardPlugin plugin){
		return false;
		
	}

}
