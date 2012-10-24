package fr.frozentux.craftguard2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class CgRemoveListCommand extends CgCommandComponent {
	
	public static boolean execute(CommandSender sender, String command, String[] args, CraftGuardPlugin plugin) {
		if(args.length != 1){
			sender.sendMessage(CgCommandComponent.MESSAGE_ARGUMENTS + "/cg removelist <list>");
			return false;
		}else if(!sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.removelist") && !sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.*")){
			sender.sendMessage(CgCommandComponent.MESSAGE_PERMISSION);
			return false;
		}else if(plugin.getListManager().getList(args[0]) == null){
			sender.sendMessage(ChatColor.RED + "List " + args[0] + " does not exist !");
			return false;
		}
		
		plugin.getListManager().removeList(args[0]);
		plugin.getListManager().saveLists();
		
		sender.sendMessage(ChatColor.GREEN + "List " + args[0] + " has been removed !");
		
		return true;
	}
	
}
