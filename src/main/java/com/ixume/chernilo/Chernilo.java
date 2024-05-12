package com.ixume.chernilo;

import com.ixume.chernilo.dataloading.FontLoader;
import com.ixume.chernilo.dataloading.ResourceLoader;
import com.ixume.chernilo.text.Font;
import org.bukkit.plugin.java.JavaPlugin;

public final class Chernilo extends JavaPlugin {
    private static Chernilo plugin;
    public static Chernilo getInstance() {return plugin;}

    @Override
    public void onEnable() {
        plugin = this;
        ResourceLoader.init();
        FontLoader.init();
    }
}
