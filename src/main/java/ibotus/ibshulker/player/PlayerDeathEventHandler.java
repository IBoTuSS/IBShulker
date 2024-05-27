package ibotus.ibshulker.player;

import ibotus.ibshulker.configurations.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerDeathEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Inventory openInventory = player.getOpenInventory().getTopInventory();

        if (openInventory.getType().equals(InventoryType.ENDER_CHEST)) {
            ArrayList<ItemStack> toDrop = new ArrayList<>();

            for (ItemStack item : openInventory.getContents()) {
                if (item != null && Config.getConfig().getStringList("settings.items-ban-list").contains(item.getType().toString())) {
                    toDrop.add(item);
                }
            }

            for (ItemStack item : toDrop) {
                openInventory.remove(item);
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }
}
