package me.athish.deathLightning;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RayTrace {

    public static Block getTargetBlock(Player player, int range) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();
        for (int i = 0; i < range; i++) {
            Location checkLocation = eyeLocation.add(direction);
            Block block = checkLocation.getBlock();
            if (block.getType() != Material.AIR) {
                return block;
            }
        }
        return null;
    }
}