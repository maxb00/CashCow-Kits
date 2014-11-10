package com.maxb00.perks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.maxb00.Main;

public class Death implements Listener{
	
	@SuppressWarnings("unused")
	private Main main;
	public Death(Main main) {
		this.main = main;
	}

	public void firework(Player player) {
		Firework fw = (Firework) player.getWorld().spawnEntity(
				player.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwmeta = fw.getFireworkMeta();
		FireworkEffect.Builder builder = FireworkEffect.builder();
		builder.withTrail();
		builder.withFlicker();
		builder.withFade(Color.RED);
		builder.withColor(Color.GRAY);
		builder.withColor(Color.SILVER);
		builder.with(FireworkEffect.Type.BALL_LARGE);
		fwmeta.addEffects(builder.build());
		fwmeta.setPower(2);
		fw.setFireworkMeta(fwmeta);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = (Player) e.getEntity();
		firework(player);
		e.getDrops().clear();
	}

}
