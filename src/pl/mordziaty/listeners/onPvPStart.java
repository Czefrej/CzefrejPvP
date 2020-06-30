package pl.mordziaty.listeners;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.mordziaty.Cowardless;

public class onPvPStart implements Listener {

    @EventHandler
    public void onPlayerLeavePVP(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player || event.getDamager() instanceof Arrow || event.getDamager() instanceof Trident){
            if(event.getEntity() instanceof Player){
                Player damager = null;
                if(event.getDamager() instanceof Arrow){
                    Arrow arrow = (Arrow) event.getDamager();
                    if(arrow.getShooter() instanceof Player){
                        damager = ((Player) arrow.getShooter()).getPlayer();
                    }else return;
                }else if(event.getDamager() instanceof Trident){
                    Trident trident = (Trident) event.getDamager();
                    if(trident.getShooter() instanceof Player){
                        damager = ((Player) trident.getShooter()).getPlayer();
                    }else return;
                }else damager = ((Player) event.getDamager()).getPlayer();
                Player victim = ((Player) event.getEntity()).getPlayer();
                if(damager!=null && victim!=null) {
                    LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(damager);
                    LocalPlayer localPlayer2 = WorldGuardPlugin.inst().wrapPlayer(victim);
                    com.sk89q.worldedit.util.Location loc = localPlayer.getLocation();
                    com.sk89q.worldedit.util.Location loc2 = localPlayer2.getLocation();
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = container.createQuery();

                    if (!victim.hasPermission("cowardless.bypass") && !damager.hasPermission("cowardless.bypass")) {
                        if (query.testState(loc, localPlayer, Flags.PVP) && query.testState(loc2, localPlayer2, Flags.PVP)) {
                            if (!Cowardless.pvpCD.containsKey(damager)) {
                                damager.sendMessage(ChatColor.RED + "Jesteś w trakcie walki! Nie wychodź z serwera!");
                                Cowardless.plugin.getLogger().info(damager.getName() + " " + ChatColor.RED + " ma AntiLogout!");
                                Cowardless.pvpCD.put(damager, System.currentTimeMillis() + (1000 * 15));
                            } else {
                                Cowardless.pvpCD.remove(damager);
                                Cowardless.pvpCD.put(damager, System.currentTimeMillis() + (1000 * 15));
                            }
                            if (!Cowardless.pvpCD.containsKey(victim)) {
                                victim.sendMessage(ChatColor.RED + "Jesteś w trakcie walki! Nie wychodź z serwera!");
                                Cowardless.plugin.getLogger().info(victim.getName() + " " + ChatColor.RED + " ma AntiLogout!");
                                Cowardless.pvpCD.put(victim, System.currentTimeMillis() + (1000 * 15));
                            } else {
                                Cowardless.pvpCD.remove(victim);
                                Cowardless.pvpCD.put(victim, System.currentTimeMillis() + (1000 * 15));
                            }
                        }
                    }

                }
            }
        }
    }
}
