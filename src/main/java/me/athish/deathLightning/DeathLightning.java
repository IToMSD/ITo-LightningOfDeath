package me.athish.deathLightning;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathLightning extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("thor").setExecutor(new ThorCommandExecutor());
        this.getCommand("lightning").setExecutor(new ThorCommandExecutor());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public class ThorCommandExecutor implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("itomsd.lightingofdeath.use")) {
                player.sendMessage("You do not have permission to use this command.");
                return true;
            }

            if (args.length == 0) {
                // Summon lightning at the player's looking location
                Block targetBlock = RayTrace.getTargetBlock(player, 100);
                if (targetBlock != null) {
                    Location targetLocation = targetBlock.getLocation();
                    player.getWorld().strikeLightning(targetLocation);
                    player.sendMessage("Lightning summoned at your target location.");
                } else {
                    player.sendMessage("No target block in sight.");
                }
            } else if (args.length == 1) {
                // Summon lightning at the specified player's location
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer != null) {
                    Location targetLocation = targetPlayer.getLocation();
                    targetPlayer.getWorld().strikeLightning(targetLocation);
                    player.sendMessage("Lightning summoned at " + targetPlayer.getName() + "'s location.");
                } else {
                    player.sendMessage("Player not found.");
                }
            } else {
                player.sendMessage("Usage: /thor [player]");
            }

            return true;
        }
    }

    public class PlayerDeathListener implements Listener {

        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            Player player = event.getEntity();
            Location deathLocation = player.getLocation();
            player.getWorld().strikeLightning(deathLocation);
        }
    }
}