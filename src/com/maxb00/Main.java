package com.maxb00;

import gui.Selector;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.maxb00.command.Command;
import com.maxb00.kits.MageWand;
import com.maxb00.libs.Respawns;
import com.maxb00.perks.ArmorColor;
import com.maxb00.perks.Death;

public class Main extends JavaPlugin implements Listener {

	public Economy economy = null;
	public static Permission perms = null;
	public Main plugin;
	
	public void onEnable() {
		Selector.KitSelector = Bukkit.createInventory(null, 9, "Kit Selector");
		getServer().getPluginManager().registerEvents(this, this);
		setupEconomy();
		if (!setupEconomy()) {
			getLogger()
					.severe(String
							.format("[%s] - Disabled due to no Vault dependency found!",
									getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		registerCmd();
		registerEvents(this, new Selector(plugin), new Death(plugin), new Respawns(plugin), new MageWand(plugin), new ArmorColor(plugin));
	}
	public void registerCmd(){
		getCommand("pvp").setExecutor(new Command(this));
		getCommand("mage").setExecutor(new Command(this));
		getCommand("archer").setExecutor(new Command(this));
		getCommand("undead").setExecutor(new Command(this));
		getCommand("joker").setExecutor(new Command(this));
		getCommand("ci").setExecutor(new Command(this));
		getCommand("c").setExecutor(new Command(this));
		getCommand("rainbow").setExecutor(new Command(this));
		getCommand("cancelarmor").setExecutor(new Command(this));
		getCommand("hat").setExecutor(new Command(this));
		getCommand("kitup").setExecutor(new Command(this));
	}
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
		Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			this.economy = ((Economy) economyProvider.getProvider());
		}
		return this.economy != null;
	}

	public static boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager()
				.getRegistration(Permission.class);
		perms = (Permission) rsp.getProvider();
		return perms != null;
	}

}