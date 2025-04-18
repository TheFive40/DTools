package org.delaware.commands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.PBooster;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class CommandBosterMultiplier extends BaseCommand {
    private static final String VERSION = "1.0.1";
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    @Command(name = "pvboost", aliases = {"pvboost","pvbooster"}, permission = "dtools.pvboost",
    inGameOnly = false)
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        CommandSender player = command.getSender ();
        if (command.length() < 2) {
            player.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------");
            player.sendMessage(ChatColor.GOLD + "✦ PVBooster Plugin " + ChatColor.YELLOW + "v" + VERSION);
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster rank <rank> <multiplier>");
            player.sendMessage(ChatColor.GRAY + "Example: " + ChatColor.GREEN + "/pvbooster VIP 2.0");
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster player <player> <multiplier>");
            player.sendMessage(ChatColor.GRAY + "Example: " + ChatColor.GREEN + "/pvbooster player Steve 1.5");
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster restart <player>");
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster remove <player>");
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster time <player>");
            player.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.LIGHT_PURPLE + "TheFive");
            player.sendMessage(ChatColor.RED + "Invalid usage! Please follow the correct format.");
            player.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------");
            return;
        }

        String subAlias = command.getArgs(0);

        switch (subAlias.toLowerCase()) {
            case "rank": {
                if (command.length() < 3) {
                    player.sendMessage(ChatColor.RED + "Uso inválido: /pvbooster rank <rank> <multiplier>");
                    return;
                }
                String rank = command.getArgs(1).toLowerCase();
                double multiplier = Double.parseDouble(command.getArgs(2));
                boosterHandler.addRankBooster(rank, multiplier);
                player.sendMessage(ChatColor.GREEN + "Booster activado para el rango " + ChatColor.GOLD + rank.toUpperCase() +
                        ChatColor.GREEN + " con multiplicador " + ChatColor.AQUA + multiplier + "x!");
                break;
            }
            case "player": {
                if (command.length() < 3) {
                    player.sendMessage(ChatColor.RED + "Uso inválido: /pvbooster player <player> <multiplier>");
                    return;
                }
                String targetName = command.getArgs(1);
                double multiplier = Double.parseDouble(command.getArgs(2));
                Player target = command.getSender().getServer().getPlayer(targetName);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "El jugador especificado no existe o no está conectado.");
                    return;
                }
                PBooster pBooster = new PBooster();
                pBooster.setMultiplier(multiplier);
                pBooster.setActivationTime(LocalDateTime.now());
                pBooster.setPlayerId(target.getUniqueId());
                boosterHandler.addPlayerBooster(target.getUniqueId(), pBooster);
                player.sendMessage(ChatColor.GREEN + "Booster activado para el jugador " + ChatColor.GOLD + target.getName() +
                        ChatColor.GREEN + " con multiplicador " + ChatColor.AQUA + multiplier + "x!");
                break;
            }
            case "restart": {
                if (command.length() < 2) {
                    player.sendMessage(ChatColor.RED + "Uso inválido: /pvbooster restart <player>");
                    return;
                }
                String targetName = command.getArgs(1);
                Player target = command.getSender().getServer().getPlayer(targetName);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "El jugador especificado no existe o no está conectado.");
                    return;
                }
                if (boosterHandler.hasPlayerBooster(target.getUniqueId())) {
                    boosterHandler.getPlayerBooster(target.getUniqueId()).setActivationTime(LocalDateTime.now());
                    player.sendMessage(ChatColor.GREEN + "El tiempo del booster para " + ChatColor.GOLD + target.getName() +
                            ChatColor.GREEN + " ha sido reiniciado.");
                } else {
                    player.sendMessage(ChatColor.RED + "Este jugador no tiene un booster activo.");
                }
                break;
            }
            case "remove": {
                if (command.length() < 2) {
                    player.sendMessage(ChatColor.RED + "Uso inválido: /pvbooster remove <player>");
                    return;
                }
                String targetName = command.getArgs(1);
                Player target = command.getSender().getServer().getPlayer(targetName);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "El jugador especificado no existe o no está conectado.");
                    return;
                }
                if (boosterHandler.hasPlayerBooster(target.getUniqueId())) {
                    BoosterDataHandler.removeBoosterPlayer (target.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Booster eliminado para el jugador " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + ".");
                } else {
                    player.sendMessage(ChatColor.RED + "Este jugador no tiene un booster activo.");
                }
                break;
            }
            case "time": {
                if (command.length() < 2) {
                    player.sendMessage(ChatColor.RED + "Uso inválido: /pvbooster time <player>");
                    return;
                }
                String targetName = command.getArgs(1);
                Player target = command.getSender().getServer().getPlayer(targetName);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "El jugador especificado no existe o no está conectado.");
                    return;
                }

                String timeLeft = boosterHandler.getRemainingBoosterTime(target.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Booster de " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + ": " + ChatColor.AQUA + timeLeft);
                break;
            }

            default:
                player.sendMessage(ChatColor.RED + "Subcomando desconocido: " + subAlias);
                break;
        }

    }

}
