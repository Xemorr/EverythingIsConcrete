package me.xemor.polisheverything;

import org.bukkit.plugin.java.JavaPlugin;

public final class PolishEverything extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ChunkGeneratorListener chunkGeneratorListener = new ChunkGeneratorListener(this);
        this.getServer().getPluginManager().registerEvents(chunkGeneratorListener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
