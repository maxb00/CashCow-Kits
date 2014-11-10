package com.maxb00.libs;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.maxb00.Main;

public class Respawns implements Listener{
	@SuppressWarnings("unused")
	private Main main;
	public Respawns(Main main){
		this.main = main;
	}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		if (e.getResult() == Result.KICK_FULL
				&& e.getPlayer().hasPermission("joinfull.use")) {
			e.allow();
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerInventory pi = event.getPlayer().getInventory();
		Player p = event.getPlayer();
		pi.setArmorContents(null);
		pi.clear();
		pi.addItem(new ItemStack(Material.COMPASS));
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 2.0F, 2.0F);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		PlayerInventory pi = e.getPlayer().getInventory();
		pi.addItem(new ItemStack(Material.COMPASS));
	}
}
