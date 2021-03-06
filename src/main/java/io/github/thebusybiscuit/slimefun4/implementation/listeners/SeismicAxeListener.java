package io.github.thebusybiscuit.slimefun4.implementation.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import io.github.thebusybiscuit.slimefun4.implementation.items.weapons.SeismicAxe;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;

/**
 * This {@link Listener} is responsible for removing every {@link FallingBlock} that was
 * created using a {@link SeismicAxe}.
 * 
 * @author TheBusyBiscuit
 * 
 * @see SeismicAxe
 *
 */
public class SeismicAxeListener implements Listener {

    public SeismicAxeListener(SlimefunPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent e) {
        if (e.getEntity().getType() == EntityType.FALLING_BLOCK && e.getEntity().hasMetadata("seismic_axe")) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }
}
