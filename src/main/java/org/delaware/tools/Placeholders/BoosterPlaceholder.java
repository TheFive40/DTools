package org.delaware.tools.Placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.VIPBooster;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class BoosterPlaceholder extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "booster";
    }

    @Override
    public String getAuthor() {
        return "DelawareX";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("timeleft")) {
            return getBoosterTimeLeft(player.getUniqueId());
        }

        return null;
    }

    private String getBoosterTimeLeft(UUID playerUUID) {
        VIPBooster booster = BoosterDataHandler.getBoosterByPlayer(playerUUID);
        if (booster == null || !booster.isActive() || booster.getActivationTime() == null) {
            return "00:00";
        }

        Duration elapsedTime = Duration.between(booster.getActivationTime(), LocalDateTime.now());
        long remainingSeconds = (60 * 60) - elapsedTime.getSeconds();

        if (remainingSeconds <= 0) {
            return "00:00";
        }

        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}


