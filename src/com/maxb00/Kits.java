package com.maxb00;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kits {
	
	public static void joker(Player player){
		PlayerInventory pi = player.getInventory();
		
		ItemStack jokerS = new ItemStack(Material.WOOD_SWORD);
		jokerS.addEnchantment(Enchantment.DAMAGE_ALL, 3);
		pi.clear();
		pi.addItem(jokerS);
		pi.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		pi.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		pi.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
	}
	public static void joker2(Player player){
		PlayerInventory pi = player.getInventory();
		
		ItemStack jokerS = new ItemStack(Material.WOOD_SWORD);
		jokerS.addEnchantment(Enchantment.DAMAGE_ALL, 5);
		pi.clear();
		pi.addItem(jokerS);
		pi.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		pi.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		pi.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
	}
    public static void pvp(Player player){
    	PlayerInventory pi = player.getInventory();
    	pi.clear();
		pi.addItem(new ItemStack(Material.DIAMOND_SWORD));
		pi.setHelmet(new ItemStack(Material.IRON_HELMET));
		pi.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		pi.setBoots(new ItemStack(Material.IRON_BOOTS));
    }
    public static void mage(Player player){
    	PlayerInventory pi = player.getInventory();
    	
    	pi.clear();
		pi.addItem(new ItemStack(Material.STONE_SWORD));
		pi.addItem(new ItemStack(Material.BLAZE_ROD));
		pi.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		pi.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		pi.setBoots(new ItemStack(Material.LEATHER_BOOTS));
    }
    public static void archer(Player player){
    	PlayerInventory pi =player.getInventory();
    	pi.clear();
    	pi.addItem(new ItemStack(Material.STONE_SWORD));
    	pi.addItem(new ItemStack(Material.BOW));
    	pi.addItem(new ItemStack(Material.ARROW, 64));
    	pi.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
    	pi.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
    	pi.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
    	pi.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
    }
    public static void undead(Player player){
    	PlayerInventory pi = player.getInventory();
    	pi.clear();
		pi.addItem(new ItemStack(Material.IRON_SWORD));
		pi.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000, 0));
		pi.addItem(new ItemStack(Material.GOLDEN_APPLE));
    }
	
}
