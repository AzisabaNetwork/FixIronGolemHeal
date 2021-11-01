package net.azisaba.fixIronGolemHeal;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FixIronGolemHeal extends JavaPlugin {
    private static final ItemStack IRON_INGOT = new ItemStack(Material.IRON_INGOT);
    private boolean preventHealWithNormalItem = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TheGreatEventListener(), this);
        getConfig().options().copyDefaults();
        preventHealWithNormalItem = getConfig().getBoolean("preventHealWithNormalItem", false);
    }

    public class TheGreatEventListener implements Listener {
        @EventHandler
        public void onRightClickEntity(PlayerInteractEntityEvent e) {
            if (e.getRightClicked().getType() != EntityType.IRON_GOLEM) {
                return;
            }
            if (preventHealWithNormalItem || !IRON_INGOT.isSimilar(e.getPlayer().getInventory().getItem(e.getHand()))) {
                // iron ingot has some tags or preventHealWithNormalItem is true
                e.setCancelled(true);
            }
        }
    }
}
