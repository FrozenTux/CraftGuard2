package fr.frozentux.craftguard2.module.smelt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.PermissionChecker;
import fr.frozentux.craftguard2.smeltingmanager.SmeltReference;

public class SmeltListener implements Listener {
	
private CraftGuardPlugin plugin;
	
	public SmeltListener(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		
		SlotType slotType = e.getSlotType();
		InventoryType invType = e.getInventory().getType();
		int slot = e.getSlot();
		Player player = (Player)e.getWhoClicked();
		
		if(invType.equals(InventoryType.FURNACE) && (slotType == SlotType.CONTAINER || slotType == SlotType.FUEL || slotType == SlotType.QUICKBAR) && (e.isShiftClick() || slot == 0 || slot == 1)){
			ItemStack object;
			if(e.isShiftClick())object = e.getCurrentItem();
			else{
				if(slot == 0 && e.getCursor() != null)object = e.getCursor();
				else if(slot == 1 && e.getInventory().getItem(0) != null)object = e.getInventory().getItem(0);
				else return;
			}
			
			int id = SmeltReference.getReference().getSmelting(object.getTypeId());
			byte metadata = object.getData().getData();
			
			boolean allowed = !PermissionChecker.check(player, id, metadata, plugin, "smelt");
			if(allowed){
				e.setCancelled(allowed);
				player.updateInventory();
			}
			
		}
	}

}
