package ibotus.ibshulker.inventory;

import ibotus.ibshulker.configurations.Config;
import ibotus.ibshulker.utils.HexColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickEventHandler implements Listener {

    @EventHandler
    public void onInventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!player.hasPermission("ibshulker.bypass")) {
            if (event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {
                if (event.getCurrentItem() != null && Config.getConfig().getStringList("settings.items-ban-list").contains(event.getCurrentItem().getType().toString())) {
                    if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                        event.setCancelled(true);
                        String soundKey = "settings.sound";
                        Sound sound = Sound.valueOf(Config.getConfig().getString(soundKey + ".name"));
                        float volume = (float) Config.getConfig().getDouble(soundKey + ".volume");
                        float pitch = (float) Config.getConfig().getDouble(soundKey + ".pitch");
                        player.playSound(player.getLocation(), sound, volume, pitch);

                        String message = Config.getConfig().getString("settings.messages");
                        String coloredMessage = HexColor.color(message);
                        assert coloredMessage != null;
                        player.sendMessage(coloredMessage);
                    }
                }
            }
        }
    }
}