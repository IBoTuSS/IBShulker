package ibotus.ibshulker;

import ibotus.ibshulker.configurations.Config;
import ibotus.ibshulker.inventory.*;
import ibotus.ibshulker.player.PlayerDeathEventHandler;
import ibotus.ibshulker.player.PlayerSwapItemsEventHandler;
import ibotus.ibshulker.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class IBShulker extends JavaPlugin implements Listener {

    private void msg(String msg) {
        String prefix = HexColor.color("&aIBShulker &7| ");
        Bukkit.getConsoleSender().sendMessage(HexColor.color(prefix + msg));
    }

    @Override
    public void onEnable() {
        Config.loadYaml(this);
        Bukkit.getConsoleSender().sendMessage("");
        this.msg("&fDeveloper: &aIBoTuS");
        this.msg("&fVersion: &dv" + this.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("");
        this.getServer().getPluginManager().registerEvents(new InventoryClickEventHandler(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathEventHandler(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseEventHandler(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryOpenEventHandler(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerSwapItemsEventHandler(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");
        this.msg("&fDisable plugin.");
        Bukkit.getConsoleSender().sendMessage("");
    }

}
