package pl.mordziaty.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.mordziaty.Cowardless;

public class onPlayerQuit implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(Cowardless.pvpCD.containsKey(event.getPlayer())){
            event.getPlayer().setHealth(0);
            Bukkit.getWorld(event.getPlayer().getWorld().getName()).playSound(event.getPlayer().getLocation(), Sound.ENTITY_VEX_CHARGE,1,1);
            Bukkit.broadcastMessage(event.getPlayer().getDisplayName()+""+ ChatColor.GOLD+" wyszedł z serwera podczas walki. Buuuu!");
        }
    }

}
