package fr.frozentux.craftguard2.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.frozentux.craftguard2.CraftGuardPlugin;

/**
 * CommandExecutor charged to handle command when a player types /cg
 * @author FrozenTux
 *
 */
public class CgCommandExecutor implements CommandExecutor {
	
	private CraftGuardPlugin plugin;
	
	public CgCommandExecutor(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		
		if(args.length >= 1){
			String[] noPrefixArgs = Arrays.copyOfRange(args, 1, args.length);
			if(args[0].equals("add")) return CgAddCommand.execute(sender, label, noPrefixArgs, plugin);
			if(args[0].equals("list")) return CgListCommand.execute(sender, label, noPrefixArgs, plugin);
			if(args[0].equals("remove")) return CgRemoveCommand.execute(sender, label, noPrefixArgs, plugin);
			if(args[0].equals("reload")) return CgReloadCommand.execute(sender, label, noPrefixArgs, plugin);
			if(args[0].equals("addlist")) return CgAddListCommand.execute(sender, label, noPrefixArgs, plugin);
			if(args[0].equals("removelist")) return CgRemoveListCommand.execute(sender, label, noPrefixArgs, plugin);
			if(args[0].equals("reload")) return CgRemoveListCommand.execute(sender, label, noPrefixArgs, plugin);
		}
		
		CgHelpCommand.execute(sender, label, args, plugin);
		
		return false;
	}

}
