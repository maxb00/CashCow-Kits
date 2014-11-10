package com.maxb00;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import com.maxb00.HashMaps;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {

	public static Inventory KitSelector;
	public Economy economy = null;
	public static Permission perms = null;
	public Main plugin;
	

	public void onEnable() {
		KitSelector = Bukkit.createInventory(null, 9, "Kit Selector");
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
	}

	private ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
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

	public void armorColor() {
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this, new Runnable() {
					private Random r = new Random();

					@SuppressWarnings("deprecation")
					public void run() {
						Color c = Color.fromRGB(r.nextInt(255), r.nextInt(255),
								r.nextInt(255));

						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							if (p.hasPermission(new Permissions().rainbow)) {
								if (p.getInventory().getHelmet() != null
										&& p.getInventory().getHelmet()
												.getType() == Material.LEATHER_HELMET) {
									p.getInventory()
											.setHelmet(
													getColorArmor(
															Material.LEATHER_HELMET,
															c));
								}

								if (p.getInventory().getChestplate() != null
										&& p.getInventory().getChestplate()
												.getType() == Material.LEATHER_CHESTPLATE) {
									p.getInventory()
											.setChestplate(
													getColorArmor(
															Material.LEATHER_CHESTPLATE,
															c));
								}

								if (p.getInventory().getLeggings() != null
										&& p.getInventory().getLeggings()
												.getType() == Material.LEATHER_LEGGINGS) {
									p.getInventory().setLeggings(
											getColorArmor(
													Material.LEATHER_LEGGINGS,
													c));
								}

								if (p.getInventory().getBoots() != null
										&& p.getInventory().getBoots()
												.getType() == Material.LEATHER_BOOTS) {
									p.getInventory().setBoots(
											getColorArmor(
													Material.LEATHER_BOOTS, c));
								}
							}
						}
					}
				}, 1, 20);
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

	private void openGUI(Player player) {
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

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
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
				armorColor();
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