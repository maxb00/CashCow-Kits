package gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.maxb00.Main;

public class Selector implements Listener {
	public static Inventory KitSelector;
	@SuppressWarnings("unused")
	private Main main;
	public Selector(Main main){
		this.main = main;
	}
	
	public void openGUI(Player player) {
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack mage = new ItemStack(Material.BLAZE_ROD);
		ItemStack archer = new ItemStack(Material.BOW);
		ItemStack undead = new ItemStack(Material.ROTTEN_FLESH);
		ItemStack brute = new ItemStack(Material.WOOD_SWORD);

		KitSelector.clear();
		KitSelector.setItem(2, undead);
		KitSelector.setItem(4, sword);
		KitSelector.setItem(5, mage);
		KitSelector.setItem(3, archer);
		KitSelector.setItem(6, brute);
		player.openInventory(KitSelector);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		ItemStack is = event.getItem();

		if (a == Action.PHYSICAL || is == null || is.getType() == Material.AIR)
			return;

		if (is.getType() == Material.COMPASS)
			openGUI(event.getPlayer());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (clicked == null)
			return;
		if (inventory.getName().equals("Kit Selector")) {
			if (clicked.getType() == Material.DIAMOND_SWORD) {
				player.performCommand("PvP");
				player.closeInventory();
			}
			if (clicked.getType() == Material.BLAZE_ROD) {
				player.performCommand("mage");
				player.closeInventory();
			}
			if (clicked.getType() == Material.BOW) {
				player.performCommand("archer");
				player.closeInventory();
			}
			if (clicked.getType() == Material.ROTTEN_FLESH) {
				player.performCommand("undead");
				player.closeInventory();
			}
			if (clicked.getType() == Material.WOOD_SWORD) {
				player.performCommand("joker");
				player.closeInventory();
			}
		}
	}
}
