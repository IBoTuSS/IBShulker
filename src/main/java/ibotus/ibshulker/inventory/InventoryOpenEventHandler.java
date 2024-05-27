package ibotus.ibshulker.inventory;

import ibotus.ibshulker.configurations.Config;
import ibotus.ibshulker.utils.HexColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryOpenEventHandler implements Listener {

    @EventHandler
    public void onInventoryOpenEvent(org.bukkit.event.inventory.InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            List<HumanEntity> viewers = event.getInventory().getViewers();
            if (!viewers.isEmpty()) {
                Player player = (Player) viewers.get(0);
                if (!player.hasPermission("ibshulker.bypass")) {
                    ItemStack[] enderChestItems = event.getInventory().getContents();
                    ArrayList<ItemStack> enderChestArray = new ArrayList<>();

                    ArrayList<ItemStack> toGive = new ArrayList<>();

                    for (ItemStack item : enderChestItems) {
                        if (item != null) {
                            if (Config.getConfig().getStringList("settings.items-ban-list").contains(item.getType().toString())) {
                                toGive.add(item);
                            } else {
                                enderChestArray.add(item);
                            }
                        }
                    }

                    ItemStack[] enderChestItemsNew = enderChestArray.toArray(new ItemStack[0]);
                    event.getInventory().setContents(enderChestItemsNew);

                    for (ItemStack item : toGive) {
                        player.getInventory().addItem(item);
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

                    if (!toGive.isEmpty()) {
                        player.closeInventory();
                        String soundKey = "settings.sound.name";
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
