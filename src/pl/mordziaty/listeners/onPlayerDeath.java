package pl.mordziaty.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.mordziaty.Cowardless;

public class onPlayerDeath implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerDeathEvent event){
        if(Cowardless.pvpCD.containsKey(event.getEntity())){
            Cowardless.pvpCD.remove(event.getEntity());
        }
    }
}
