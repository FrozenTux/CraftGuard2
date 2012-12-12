package fr.frozentux.craftguard2.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.list.Id;

public class CgAddCommand extends CgCommandComponent {

	public static boolean execute(CommandSender sender, String command, String[] args, CraftGuardPlugin plugin) {
		
		if(args.length != 2){
			sender.sendMessage(CgCommandComponent.MESSAGE_ARGUMENTS + "/cg add <list> <id>");
			return false;
		}else if(!sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.add") && !sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.*")){
			sender.sendMessage(CgCommandComponent.MESSAGE_PERMISSION);
			return false;
		}else if(plugin.getListManager().getList(args[0]) == null){
			sender.sendMessage(ChatColor.RED + "List " + args[0] + " does not exist");
			return false;
		}
		
		plugin.getListManager().getList(args[0]).addId(new Id(args[1]));
		plugin.getListManager().saveList(plugin.getListManager().getList(args[0]));
		
		sender.sendMessage(ChatColor.GREEN + "Successfully added " + Material.getMaterial(Math.abs(Integer.valueOf(args[1].split(":")[0]))).name() + " (" + args[1] + ") to list " + args[0]);
		
		return true;
	}

}
