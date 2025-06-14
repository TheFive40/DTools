package org.delaware.commands;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.delaware.tools.BankInventory;
import org.delaware.tools.BankManager;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.text.DecimalFormat;

public class CommandBank extends BaseCommand {

    @Command(name = "banco", aliases = "banco", permission = "dtools.bank", inGameOnly = true)
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        String[] args = command.getArgs ( );
        DecimalFormat df = new DecimalFormat ( "#,##0.00" );

        if (args.length == 0) {
            BankInventory.openTrader(player);
            return;
        }

        switch (args[0].toLowerCase ( )) {
            case "depositar":
                if (args.length != 2) {
                    player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cUso: &f/banco depositar <cantidad>" ) );
                    return;
                }
                try {
                    double amount = Double.parseDouble ( args[1] );
                    if (amount <= 0) {
                        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cLa cantidad debe ser mayor que 0." ) );
                        return;
                    }
                    IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );

                    int tp = dbcPlayer.getTP ( );
                    if (amount <= tp) {
                        BankManager.deposit ( player.getName ( ), amount );
                        dbcPlayer.setTP ( (int) (dbcPlayer.getTP ( ) - amount) );
                        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &aHas depositado &f$" + df.format ( amount ) + "&a en tu banco." ) );
                        player.playSound ( player.getLocation ( ), Sound.LEVEL_UP, 1f, 1f );
                        return;
                    } else {
                        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cNo tienes suficientes TPs para depositar esa cantidad." ) );
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cCantidad inválida." ) );
                }
                break;

            case "retirar":
                if (args.length != 2) {
                    player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cUso: &f/banco retirar <cantidad>" ) );
                    return;
                }
                try {
                    double amount = Double.parseDouble ( args[1] );
                    if (amount <= 0) {
                        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cLa cantidad debe ser mayor que 0." ) );
                        return;
                    }
                    boolean success = BankManager.withdraw ( player.getName ( ), amount );
                    if (success) {
                        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &aHas retirado &f$" + df.format ( amount ) + "&a de tu banco." ) );
                        player.playSound ( player.getLocation ( ), Sound.ORB_PICKUP, 1f, 1f );
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cCantidad inválida." ) );
                }
                break;

            case "balance":
                double balance = BankManager.getBalance ( player.getName ( ) );
                player.sendMessage ( CC.translate ( "&8&m--------------------------------------" ) );
                player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &eTu saldo actual en el banco es: &f$" + df.format ( balance ) ) );
                player.sendMessage ( CC.translate ( "&8&m--------------------------------------" ) );
                break;

            default:
                sendHelp ( player );
                break;
        }
    }

    private void sendHelp ( Player player ) {
        player.sendMessage ( CC.translate ( "&8&m--------------------------------------" ) );
        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &fComandos del banco:" ) );
        player.sendMessage ( CC.translate ( "&7&l➥ &f/banco depositar <cantidad>" ) );
        player.sendMessage ( CC.translate ( "&7&l➥ &f/banco retirar <cantidad>" ) );
        player.sendMessage ( CC.translate ( "&7&l➥ &f/banco balance" ) );
        player.sendMessage ( CC.translate ( "&8&m--------------------------------------" ) );
    }
}