package org.delaware.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.Kits.KitClaimTracker;
import org.delaware.tools.Kits.KitStorage;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class KitCategoryCommand extends BaseCommand {

    @Command(name = "kitadmin", aliases = {"kitadmin"}, permission = "dtools.kitadmin")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if (!(command.getSender() instanceof Player)) {
            command.getSender().sendMessage(ChatColor.RED + "✘ Este comando solo puede ser usado por jugadores.");
            return;
        }

        Player player = (Player) command.getSender();

        if (command.getArgs().length == 0) {
            player.sendMessage(ChatColor.GRAY + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            player.sendMessage(ChatColor.RED + "✘ Uso incorrecto del comando.");
            player.sendMessage(ChatColor.YELLOW + "✔ Correcto: /kitadmin <categoria>");
            player.sendMessage(ChatColor.YELLOW + "✔ Correcto: /kitadmin eliminar <categoria>");
            player.sendMessage(ChatColor.GRAY + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        String action = command.getArgs(0).toLowerCase();

        if (action.equals("eliminar")) {
            if (command.getArgs().length != 2) {
                player.sendMessage(ChatColor.RED + "✘ Uso: /kitadmin eliminar <categoria>");
                return;
            }

            String category = command.getArgs(1);
            KitStorage.removeCategory(category);
            player.sendMessage(ChatColor.GRAY + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            player.sendMessage(ChatColor.RED + "Kit de categoría " + ChatColor.GOLD + category.toUpperCase() + ChatColor.RED + " eliminado.");
            player.sendMessage(ChatColor.GRAY + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }
        if (action.equals("unclaim")) {
            if (command.getArgs().length != 3) {
                player.sendMessage(ChatColor.RED + "✘ Uso: /kitadmin unclaim <jugador> <categoria>");
                return;
            }

            String targetName = command.getArgs(1);
            String category = command.getArgs(2);

            Player target = org.bukkit.Bukkit.getPlayerExact(targetName);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "✘ No se encontró el jugador '" + targetName + "'.");
                return;
            }

            KitClaimTracker.removeClaim(target.getUniqueId(), category);
            player.sendMessage(ChatColor.GREEN + "✔ Reclamo de kit '" + category + "' eliminado para " + targetName + ".");
            return;
        }

        String category = action;
        ItemStack itemInHand = player.getInventory().getItemInHand();

        if (itemInHand == null) {
            player.sendMessage(ChatColor.RED + "✘ Debes tener un ítem en la mano para registrar en el kit.");
            return;
        }

        KitStorage.addItemToCategory(category, itemInHand.clone());
        player.sendMessage(ChatColor.GRAY + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        player.sendMessage(ChatColor.GREEN + "Ítem agregado al kit de categoría " + ChatColor.GOLD + category.toUpperCase());
        player.sendMessage(ChatColor.GRAY + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
