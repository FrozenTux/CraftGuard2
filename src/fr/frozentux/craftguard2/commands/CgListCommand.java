package fr.frozentux.craftguard2.commands;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;

public class CgListCommand extends CgCommandComponent {
	
	public static boolean execute(CommandSender sender, String command, String[] args, CraftGuardPlugin plugin) {
		
		if(args.length != 1){
			if(args.length == 0){
				sender.sendMessage(ChatColor.AQUA + "Lists :");
				Iterator<String> it = plugin.getListManager().getListsNames().iterator();
				while(it.hasNext()){
					sender.sendMessage(ChatColor.AQUA + "- " + it.next());
				}
				return true;
			}else{
				sender.sendMessage(CgCommandComponent.MESSAGE_ARGUMENTS + "/cg list <list>");
				return false;
			}
		}else if(!sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.list") && !sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.*")){
			sender.sendMessage(CgCommandComponent.MESSAGE_PERMISSION);
			return false;
		}else if(plugin.getListManager().getList(args[0]) == null){
			sender.sendMessage(ChatColor.RED + "List " + args[0] + " does not exist");
			return false;
		}
		
		sender.sendMessage(ChatColor.AQUA + "List : " + args[0] + plugin.getListManager().getList(args[0]).toString());
		
		return true;
	}
}
