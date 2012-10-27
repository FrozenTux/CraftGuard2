package fr.frozentux.craftguard2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;

/**
 * Sends the list of the command to the sender with their description
 * @author FrozenTux
 *
 */
public class CgHelpCommand extends CgCommandComponent {
	
	public static boolean execute(CommandSender sender, String command, String[] args, CraftGuardPlugin plugin) {
		sender.sendMessage(ChatColor.AQUA + "=== CraftGuard ver. " + plugin.getDescription().getVersion() + " ===");
		
		sender.sendMessage(ChatColor.GREEN + "/cg add <list> <id> " + ChatColor.AQUA + " Adds <id> to the specified <list>");
		sender.sendMessage(ChatColor.GREEN + "/cg remove <list> <id> " + ChatColor.AQUA + " Removes <id> from the specified <list> (does not support metadata !)");
		sender.sendMessage(ChatColor.GREEN + "/cg list <list> " + ChatColor.AQUA + " Lists the content of the specified <list>");
		sender.sendMessage(ChatColor.GREEN + "/cg reload " + ChatColor.AQUA + " Reloads configuration, lists and smeltings from files");
		sender.sendMessage(ChatColor.GREEN + "/cg addlist <list> " + ChatColor.AQUA + " Adds a new empty list with the specified name");
		sender.sendMessage(ChatColor.GREEN + "/cg removelist <list> " + ChatColor.AQUA + " Deletes the specified <list>");

		return false;
	}

}
