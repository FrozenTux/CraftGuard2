package fr.frozentux.craftguard2.module.breaking;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.PermissionChecker;

public class BreakListener implements Listener {

	private CraftGuardPlugin plugin;
	
	public BreakListener(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		boolean denied = !PermissionChecker.check(p, b.getTypeId(), b.getData(), plugin, "break");
		if(denied){
			e.setCancelled(denied);
			p.updateInventory();
		}
	}
	
}
