package me.xemor.polisheverything;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.EnumMap;

public class ChunkGeneratorListener implements Listener {

    EnumMap<Material, Material> map = new EnumMap<>(Material.class);
    private PolishEverything polishEverything;

    public ChunkGeneratorListener(PolishEverything polishEverything) {
        this.polishEverything = polishEverything;
        map.put(Material.STONE, Material.GRAY_CONCRETE);
        map.put(Material.GOLD_ORE, Material.GOLD_BLOCK);
        map.put(Material.IRON_ORE, Material.IRON_BLOCK);
        map.put(Material.DIAMOND_ORE, Material.DIAMOND_BLOCK);
        map.put(Material.COAL_ORE, Material.COAL_BLOCK);
        map.put(Material.OAK_LOG, Material.BROWN_CONCRETE);
        map.put(Material.OAK_LEAVES, Material.GREEN_CONCRETE);
        map.put(Material.JUNGLE_LOG, Material.BROWN_CONCRETE);
        map.put(Material.JUNGLE_LEAVES, Material.GREEN_CONCRETE);
        map.put(Material.BIRCH_LOG, Material.WHITE_CONCRETE);
        map.put(Material.BIRCH_LEAVES, Material.GREEN_CONCRETE);
        map.put(Material.GRASS_BLOCK, Material.GREEN_TERRACOTTA);
        map.put(Material.DIRT, Material.BROWN_TERRACOTTA);
        map.put(Material.ANDESITE, Material.POLISHED_ANDESITE);
        map.put(Material.DIORITE, Material.POLISHED_DIORITE);
        map.put(Material.GRANITE, Material.POLISHED_GRANITE);
        map.put(Material.GRAVEL, Material.LIGHT_GRAY_WOOL);
        map.put(Material.COARSE_DIRT, Material.BROWN_TERRACOTTA);
        map.put(Material.ACACIA_LOG, Material.LIGHT_GRAY_CONCRETE);
        map.put(Material.ACACIA_LEAVES, Material.GREEN_CONCRETE);
        map.put(Material.SAND, Material.YELLOW_TERRACOTTA);
        map.put(Material.SANDSTONE, Material.YELLOW_CONCRETE);
        map.put(Material.LAPIS_ORE, Material.LAPIS_BLOCK);
        map.put(Material.REDSTONE_ORE, Material.REDSTONE_BLOCK);
        map.put(Material.PODZOL,  Material.BROWN_TERRACOTTA);
        map.put(Material.SPRUCE_LOG, Material.BROWN_CONCRETE);
        map.put(Material.SPRUCE_LEAVES, Material.GREEN_CONCRETE);
        map.put(Material.DARK_OAK_LOG, Material.BROWN_CONCRETE);
        map.put(Material.DARK_OAK_LEAVES, Material.GREEN_CONCRETE);
        map.put(Material.RED_MUSHROOM_BLOCK, Material.RED_CONCRETE);
        map.put(Material.BROWN_MUSHROOM_BLOCK, Material.BROWN_CONCRETE);
        map.put(Material.MUSHROOM_STEM, Material.WHITE_CONCRETE);
        //etc
    }

    @EventHandler
    public void chunkGenerated(ChunkPopulateEvent e) {
        Chunk chunk = e.getChunk();
        if (chunk.isLoaded()) {
            Runnable runnable = new Runnable(0, chunk, map, polishEverything);
            runnable.runTask(polishEverything);
        }
    }

    public class Runnable extends BukkitRunnable {

        private int YColumn;
        private Chunk chunk;
        private EnumMap<Material, Material> map;
        private PolishEverything polishEverything;

        public Runnable(int YColumn, Chunk chunk, EnumMap<Material, Material> map, PolishEverything polishEverything) {
            this.YColumn = YColumn;
            this.chunk = chunk;
            this.map = map;
            this.polishEverything = polishEverything;
        }

        @Override
        public void run() {
            int lowestLightLevel = 15;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Block block = chunk.getBlock(x, YColumn, z);
                    Material currentType = block.getType();
                    if (block.getLightFromSky() < lowestLightLevel) {
                        lowestLightLevel = block.getLightFromSky();
                    }
                    Material newType = map.get(currentType);
                    if (newType != null) {
                        block.setType(newType, false);
                    }
                }
            }
            if (YColumn == 255) {
                return;
            }
            if (lowestLightLevel < 15) {
                Runnable runnable = new Runnable(++YColumn, chunk, map, polishEverything);
                runnable.runTaskLater(polishEverything, 1L);
            }
        }
    }

}
