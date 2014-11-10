package com.maxb00.command;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.maxb00.Main;
import com.maxb00.kits.Kits;
import com.maxb00.libs.HashMaps;
import com.maxb00.libs.Permissions;
import com.maxb00.perks.ArmorColor;

public class Command implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main main;
	private Economy economy = null;
	public Command(Main main){
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender,
			org.bukkit.command.Command cmd, String commandLabel,
			String[] args) { {
		Player player = (Player) sender;
		PlayerInventory pi = player.getInventory();
		if (commandLabel.equalsIgnoreCase("pvp")) {
			if (sender instanceof Player) {
				if (sender.hasPermission(new Permissions().kitPvp)) {
					Kits.pvp(player);
				} else {
					player.sendMessage(ChatColor.GRAY
							+ "You need the permission " + ChatColor.RED
							+ "maxb00.kit.pvp" + ChatColor.GRAY
							+ " to use this kit");
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("mage")) {
			if (sender instanceof Player) {
				if (sender.hasPermission(new Permissions().kitMage)) {
					Kits.mage(player);
				} else {
					player.sendMessage(ChatColor.GRAY
							+ "You need the permission " + ChatColor.RED
							+ "maxb00.kit.mage" + ChatColor.GRAY
							+ " to use this kit");
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("archer")) {
			if (sender instanceof Player) {
				if (sender.hasPermission(new Permissions().kitArcher)) {
					Kits.archer(player);
				} else {
					player.sendMessage(ChatColor.GRAY
							+ "You need the permission " + ChatColor.RED
							+ "maxb00.kit.archer" + ChatColor.GRAY
							+ " to use this kit");
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("undead")) {
			if (sender instanceof Player) {
				if (sender.hasPermission(new Permissions().kitUndead)) {
					Kits.undead(player);
				} else {
					player.sendMessage(ChatColor.GRAY
							+ "You need the permission " + ChatColor.RED
							+ "maxb00.kit.undead" + ChatColor.GRAY
							+ " to use this kit");
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("joker")) {
			if (sender instanceof Player) {
				if (sender.hasPermission(new Permissions().kitJoker)) {				
					if(!HashMaps.joker2.containsKey(player)){
						Kits.joker(player);
					} else if(HashMaps.joker2.containsKey(player)){
						Kits.joker2(player);
					}else {
						player.sendMessage(ChatColor.GRAY
								+ "You need the permission " + ChatColor.RED
								+ "maxb00.kit.brute" + ChatColor.GRAY
								+ " to use this kit");
					}
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("ci")|| commandLabel.equalsIgnoreCase("clear")) {
			pi.clear();
			pi.setArmorContents(null);
		}
		if (commandLabel.equalsIgnoreCase("compass")
				|| commandLabel.equalsIgnoreCase("c")) {
			pi.addItem(new ItemStack(Material.COMPASS));
		}
		if (commandLabel.equalsIgnoreCase("rainbow")
				|| commandLabel.equalsIgnoreCase("armorColor")
				|| commandLabel.equalsIgnoreCase("ac")) {
			if (player.hasPermission(new Permissions().rainbow)) {
				pi.setHelmet(new ItemStack(Material.LEATHER_HELMET));
				pi.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				pi.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				pi.setBoots(new ItemStack(Material.LEATHER_BOOTS));
				ArmorColor.armorColor();
			} else {
				player.sendMessage(ChatColor.GRAY + "You need the permission "
						+ ChatColor.RED + "maxb00.lobby.rainbow"
						+ ChatColor.GRAY
						+ " to use this kit. Donate to get it!");
			}
		}
		if (commandLabel.equalsIgnoreCase("cancelarmor")
				|| commandLabel.equalsIgnoreCase("rainbowx")) {
			if (pi.getHelmet() != null
					&& pi.getHelmet().getType() == Material.LEATHER_HELMET) {
				pi.setArmorContents(null);
			}
		}
		if (commandLabel.equalsIgnoreCase("kitup")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("brute")) {
					// rankup brute kit
					// "rankup brute"
					double i = economy.getBalance(player);
					if(!HashMaps.joker2.containsKey(player)){
						if (i >= 10.0) {
							economy.withdrawPlayer(player, 10.0);
							HashMaps.joker2.put(player, null);
							player.sendMessage("Upgraded to Brute II!");
						}
					}
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("hat")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("bedrock")) {
					pi.setHelmet(new ItemStack(Material.BEDROCK));
				}
				else if (args[0].equalsIgnoreCase("glass")) {
					pi.setHelmet(new ItemStack(Material.GLASS));
				}
				else if (args[0].equalsIgnoreCase("glowstone")) {
					pi.setHelmet(new ItemStack(Material.GLOWSTONE));
				} else {
					player.sendMessage("Not a valid Hat!");
				}
			}
		}

		return false;
			}
	}
}
