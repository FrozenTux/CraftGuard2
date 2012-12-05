package fr.frozentux.craftguard2.module.craft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.PermissionChecker;

/**
 * Listener for all players-related actions including login and inventory click
 * @author FrozenTux
 *
 */
public class CraftListener implements Listener {
	
	private CraftGuardPlugin plugin;
	
	public CraftListener(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		SlotType slotType = e.getSlotType();
		InventoryType invType = e.getInventory().getType();
		int slot = e.getSlot();
		Player player = (Player)e.getWhoClicked();
		
		if(slotType == SlotType.RESULT && (invType.equals(InventoryType.CRAFTING) || invType.equals(InventoryType.WORKBENCH)) && slot == 0 && e.getInventory().getItem(0) != null){
			ItemStack object = e.getInventory().getItem(slot);
			int id = object.getTypeId();
			byte metadata = object.getData().getData();
			
			boolean allowed = !PermissionChecker.check(player, id, metadata, plugin, "craft");
			if(allowed){
				e.setCancelled(allowed);
				if(allowed)player.updateInventory();
			}
			return;
			
		}
		
	}
	
}
