package pl.mordziaty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.mordziaty.listeners.onPlayerDeath;
import pl.mordziaty.listeners.onPlayerQuit;
import pl.mordziaty.listeners.onPvPStart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cowardless extends JavaPlugin {

    public static HashMap<Player,Long> pvpCD;
    public static Plugin plugin;
    public static BukkitTask antiPVPtask;

    @Override
    public void onEnable(){
        pvpCD = new HashMap<>();
        plugin = this;
        getLogger().info(ChatColor.GOLD +"Cowardless zostal aktywowany!");
        Bukkit.getServer().getPluginManager().registerEvents(new onPvPStart(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerQuit(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerDeath(), this);
        antiPVPtask = new BukkitRunnable(){
            @Override
            public void run() {
                if(pvpCD != null) {
                    Long time = System.currentTimeMillis();
                    Iterator it = pvpCD.entrySet().iterator();
                    while (it.hasNext())
                    {
                        Map.Entry item = (Map.Entry) it.next();
                        Player key = (Player) item.getKey();
                        Long value = (Long) item.getValue();

                        if(key.isFlying()){
                            key.setAllowFlight(false);
                            key.setFlying(false);
                            key.sendMessage(ChatColor.RED+"Nie możesz latać podczas walki!");
                        }

                        if(value<=System.currentTimeMillis()){
                            key.sendMessage(ChatColor.GREEN+"Koniec bitki! Możesz się bezpiecznie wylogować.");
                            key.sendActionBar(ChatColor.GREEN+"Możesz się wylogować.");
                            plugin.getLogger().info(key.getName()+" "+ChatColor.GREEN+" może się bezpiecznie wylogować!");
                            it.remove();
                        }else{
                            key.sendActionBar(ChatColor.RED+"Nie wychodź z serwera przez "+((int) -((time-value)/1000))+" sekund!");
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 10L);
    }




}
