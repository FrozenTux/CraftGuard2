package fr.frozentux.craftguard2.module.place;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.PermissionChecker;

public class PlaceListener implements Listener {

	private CraftGuardPlugin plugin;
	
	public PlaceListener(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		boolean denied = !PermissionChecker.check(p, b.getTypeId(), b.getData(), plugin, "place");
		if(denied){
			e.setCancelled(denied);
			p.updateInventory();
		}
	}
	
}
