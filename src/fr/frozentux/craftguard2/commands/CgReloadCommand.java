package fr.frozentux.craftguard2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.smeltingmanager.SmeltReference;

public class CgReloadCommand extends CgCommandComponent {
	
	public static boolean execute(CommandSender sender, String command, String[] args, CraftGuardPlugin plugin) {
		
		if(!sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.reload") && !sender.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + ".admin.*")){
			sender.sendMessage(CgCommandComponent.MESSAGE_PERMISSION);
			return false;
		}
		
		sender.sendMessage(ChatColor.GREEN + "CraftGuard : Reloading configuration...");
		if(!(sender instanceof ConsoleCommandSender))plugin.getCraftGuardLogger().info(sender.getName() + " : Reloading configuration...");
		
		plugin.getConfiguration().load();
		
		plugin.initModules();
		
		plugin.getListManager().init();
		
		SmeltReference.getReference().clearReference();
		plugin.getSmeltFile().load();
		
		sender.sendMessage(ChatColor.GREEN + "CraftGuard : Reload complete");
		if(!(sender instanceof ConsoleCommandSender))plugin.getCraftGuardLogger().info(sender.getName() + "Configuration reload complete !");
		
		return true;
	}
}
