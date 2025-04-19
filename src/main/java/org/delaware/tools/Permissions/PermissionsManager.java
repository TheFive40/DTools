package org.delaware.tools.Permissions;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.delaware.Main;

import java.util.UUID;
import java.util.logging.Level;

public class PermissionsManager {
    private LuckPerms api;
    public PermissionsManager() {
        LuckPerms api = Main.luckPermsAPI;
        if(api == null) {
            Main.instance.getLogger().warning("LuckPerms not found, permissions check won't work");
        }else {
            this.api = Main.luckPermsAPI;
        }
    }
    public static boolean hasPermission(Player player, String permission) {
        LuckPerms api = Main.luckPermsAPI;
        if(api == null) {
            Main.instance.getLogger().warning("LuckPerms not found, permissions check won't work");
            return player.isOp();
        }
        try {
            User user = api.getUserManager().getUser(player.getUniqueId());
            if(user == null) {
                Main.instance.getLogger().warning("User " + player.getName() + " not found in LuckPerms config");
                return player.isOp();
            }
            return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean() || player.isOp();
        }catch(Exception e) {
            Main.instance.getLogger().log(Level.SEVERE, "Error checking permissions for player: " + player.getName(), e);
            return player.isOp();
        }
    }
    public String getPlayerGroup(String uuid) {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        try {
            User user = api.getUserManager().getUser(player.getUniqueId());
            if(user == null) {
                Main.instance.getLogger().warning("User " + player.getName() + " not found in LuckPerms config");
                return null;
            }
            return user.getPrimaryGroup();
        }catch(Exception e) {
            Main.instance.getLogger().log(Level.SEVERE, "Error checking group for player: " + player.getName(), e);
            return null;
        }
    }
}
