package fr.frozentux.craftguard2.module.use;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.frozentux.craftguard2.CraftGuardPlugin;
import fr.frozentux.craftguard2.PermissionChecker;

public class UseListener implements Listener {

	private CraftGuardPlugin plugin;
	
	private static ArrayList<Material> specialBlocks = new ArrayList<Material>();
	
	static{
		
		specialBlocks.add(Material.DISPENSER);
		specialBlocks.add(Material.NOTE_BLOCK);
		specialBlocks.add(Material.BED_BLOCK);
		specialBlocks.add(Material.CHEST);
		specialBlocks.add(Material.WORKBENCH);
		specialBlocks.add(Material.FURNACE);
		specialBlocks.add(Material.BURNING_FURNACE);
		specialBlocks.add(Material.SIGN_POST);
		specialBlocks.add(Material.WOODEN_DOOR);
		specialBlocks.add(Material.WALL_SIGN);
		specialBlocks.add(Material.LEVER);
		specialBlocks.add(Material.STONE_BUTTON);
		specialBlocks.add(Material.JUKEBOX);
		specialBlocks.add(Material.DIODE_BLOCK_OFF);
		specialBlocks.add(Material.DIODE_BLOCK_ON);
		specialBlocks.add(Material.TRAP_DOOR);
		specialBlocks.add(Material.FENCE_GATE);
		specialBlocks.add(Material.ENCHANTMENT_TABLE);
		specialBlocks.add(Material.BREWING_STAND);
		specialBlocks.add(Material.COMMAND);
		specialBlocks.add(Material.BEACON);
		specialBlocks.add(Material.ANVIL);
	}
	
	public UseListener(CraftGuardPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(!e.getAction().equals(Action.PHYSICAL)){
			Player p = e.getPlayer();
			ItemStack i = e.getItem();
			boolean denied = false;
			boolean special = (e.getClickedBlock() == null) ? false : specialBlocks.contains(e.getClickedBlock().getType());
			if(i != null && i.getType() != Material.AIR && !special)denied = !PermissionChecker.check(p, i.getTypeId(), i.getData().getData(), plugin, "use");
			if(denied){
				e.setCancelled(denied);
				p.updateInventory();
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractWithEntity(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		ItemStack i = p.getItemInHand();
		boolean denied = false;
		if(i != null && i.getType() != Material.AIR && !e.getRightClicked().getType().equals(EntityType.VILLAGER))denied = !PermissionChecker.check(p, i.getTypeId(), i.getData().getData(), plugin, "use");
		if(denied){
			e.setCancelled(denied);
			p.updateInventory();
		}
	}
	
	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent e){
		try{
			Player p = (Player)e.getDamager();
			ItemStack i = p.getItemInHand();
			boolean denied = false;
			if(i != null && i.getType() != Material.AIR)denied = !PermissionChecker.check(p, i.getTypeId(), i.getData().getData(), plugin, "use");
			if(denied){
				e.setCancelled(denied);
				p.updateInventory();
			}
		}catch(Exception ex){
			return;
		}
	}
	
}
