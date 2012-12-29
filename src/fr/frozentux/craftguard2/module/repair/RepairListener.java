package fr.frozentux.craftguard2.module.repair;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.PermissionChecker;

public class RepairListener implements Listener {
	
	private CraftGuardPlugin plugin;
	
	public RepairListener(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		Player player =  (Player) e.getWhoClicked();
		InventoryType invType = e.getInventory().getType();
		SlotType slotType = e.getSlotType();
		
		if(invType.equals(InventoryType.ANVIL) && ((slotType.equals(SlotType.CRAFTING) && e.getCursor() != null) || ((slotType.equals(SlotType.CONTAINER) || slotType.equals(SlotType.QUICKBAR)) && e.isShiftClick() && e.getCurrentItem() != null))){
			ItemStack item = (e.isShiftClick()) ? e.getCurrentItem() : e.getCursor();
			boolean denied = !PermissionChecker.check(player, item.getTypeId(), item.getData().getData(), plugin, "repair");
			if(denied){
				e.setCancelled(true);
				player.updateInventory();
			}
		}
	}
	
}
