package ibotus.ibshulker.player;

import ibotus.ibshulker.configurations.Config;
import ibotus.ibshulker.utils.HexColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerSwapItemsEventHandler implements Listener {

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("ibshulker.bypass")) {
            ItemStack mainHandItem = event.getMainHandItem();
            ItemStack offHandItem = event.getOffHandItem();

            assert mainHandItem != null;
            if (Config.getConfig().getStringList("settings.items-ban-list").contains(mainHandItem.getType().toString()) || Config.getConfig().getStringList("settings.items-ban-list").contains(Objects.requireNonNull(offHandItem).getType().toString())) {
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
