package com.maxb00.kits;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.maxb00.Main;

public class MageWand implements Listener{
	@SuppressWarnings("unused")
	private Main main;
	public MageWand(Main main){
		this.main = main;
	}
	@EventHandler
	public void mageSnowball(final PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_AIR))
			return;
		if (!(e.getItem().getType() == Material.BLAZE_ROD))
			return;
		Snowball s = e.getPlayer().launchProjectile(Snowball.class);
		s.getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 10);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		Projectile p = e.getEntity();
		if (!(p instanceof Snowball)) {
			return;
		}
		Snowball s = (Snowball) p;
		for (Entity en : s.getNearbyEntities(5, 30, 5)) {
			if (en instanceof Player) {
				Player pl = (Player) en;
				if (!(pl == e.getEntity().getShooter())) {
					pl.addPotionEffect(new PotionEffect(
							PotionEffectType.BLINDNESS, 25, 0));
					pl.addPotionEffect(new PotionEffect(
							PotionEffectType.POISON, 25, 1));

				}
			}
		}
	}
}
