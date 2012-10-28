package fr.frozentux.craftguard2.listener;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.list.ListManager;
import fr.frozentux.craftguard2.list.craft.CraftList;
import fr.frozentux.craftguard2.smeltingmanager.SmeltReference;

/**
 * Class which provides method to see if a player has permission to craft something
 * @author FrozenTux
 *
 */
public class CraftPermissionChecker {
	
	public static boolean checkCraft(Player player, int id, byte data, CraftGuardPlugin plugin){
		ListManager manager = plugin.getListManager();
		
		boolean allowed = false;
		if(manager.inCheckList(id)){
			
			Iterator<String> it = manager.getListsNames().iterator();
			while(it.hasNext() && !allowed){
				
				CraftList craftList = manager.getList(it.next());
				
				if(player.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + "." + craftList.getPermission()) && craftList.containsId(id)){
					allowed = craftList.getId(id).hasMetadata(data);
				}
			}
			
		}else allowed = plugin.getConfiguration().getBooleanKey("preventiveallow");
		
		if(!allowed){
			if(plugin.getConfiguration().getBooleanKey("notifyplayer"))player.sendMessage(ChatColor.RED + parseMessage(plugin.getConfiguration().getStringKey("denymessage"), player, id));
			if(plugin.getConfiguration().getBooleanKey("log"))plugin.getCraftGuardLogger().info(parseMessage(plugin.getConfiguration().getStringKey("logmessage"), player, id));
		}
		
		return allowed;
	}
	
	public static boolean checkFurnace(Player player, int id, byte data, CraftGuardPlugin plugin){
		id = SmeltReference.getReference().getSmelting(id);
		
		return checkCraft(player, id, data, plugin);
	}
	
	private static String parseMessage(String message, Player player, int id){
		return message.replaceAll("%p", player.getName()).replaceAll("%i", String.valueOf(id)).replaceAll("%n", Material.getMaterial(id).toString()); 
	}
	
}
