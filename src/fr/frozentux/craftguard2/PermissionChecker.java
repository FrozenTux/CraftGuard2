package fr.frozentux.craftguard2;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.frozentux.craftguard2.list.List;
import fr.frozentux.craftguard2.list.ListManager;

/**
 * Class which provides method to see if a player has permission to craft something
 * @author FrozenTux
 *
 */
public class PermissionChecker {
	
	public static boolean check(Player player, int id, byte data, CraftGuardPlugin plugin, String type){
		ListManager manager = plugin.getListManager();
		
		boolean allowed = false;
		if(manager.inCheckList(id)){
			
			Iterator<String> it = manager.getListsNames().iterator();
			while(it.hasNext() && !allowed){
				
				List list = manager.getList(it.next());
				
				if(player.hasPermission(plugin.getConfiguration().getStringKey("baseperm") + "." + list.getPermission()) && (list.containsId(id) || list.containsId(-id) || (list.typeListAvailable(type) && (list.getTypeList(type).containsKey(id) || list.getTypeList(type).containsKey(-id))))){
					boolean listContains = (list.containsId(id)) ? list.getId(id).hasMetadata(data) : false;
					boolean listContainsRem = (list.containsId(-id)) ? list.getId(-id).hasMetadata(data) : false;
					boolean typeListContains = (list.typeListAvailable(type) && list.getTypeList(type).containsKey(id)) ? list.getTypeList(type).get(id).hasMetadata(data) : false;
					boolean typeListContainsRem = (list.typeListAvailable(type) && list.getTypeList(type).containsKey(-id)) ? list.getTypeList(type).get(-id).hasMetadata(data) : false;
					allowed = (listContains || typeListContains) && !listContainsRem && !typeListContainsRem;
				}
			}
			
		}else allowed = plugin.getConfiguration().getBooleanKey("preventiveallow");
		
		if(!allowed){
			if(plugin.getConfiguration().getBooleanKey("notifyplayer"))player.sendMessage(ChatColor.RED + parseMessage(plugin.getConfiguration().getStringKey("denymessage"), player, id));
			if(plugin.getConfiguration().getBooleanKey("log"))plugin.getCraftGuardLogger().info(parseMessage(plugin.getConfiguration().getStringKey("logmessage"), player, id));
		}
		
		return allowed;
	}
	
	private static String parseMessage(String message, Player player, int id){
		return message.replaceAll("%p", player.getName()).replaceAll("%i", String.valueOf(id)).replaceAll("%n", Material.getMaterial(id).toString()); 
	}
	
}
