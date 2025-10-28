package org.delaware.commands;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.PBooster;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.delaware.commands.GlobalBoosterCommand.boosterActive;
import static org.delaware.commands.GlobalBoosterCommand.boosterMultiplier;
import static org.delaware.events.PersonalBoosterEvent.boosterPlayer;

public class CommandGiveTPSByLvL extends BaseCommand {
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    @Command(name = "dartpslvl", inGameOnly = false, permission = "dtools.dartps")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        try {
            if (command.getArgs ( ).length < 2) {
                command.getSender ( ).sendMessage ( CC.translate ( "&cUso correcto: &e/dartps <jugador> <cantidad>" ) );
                return;
            }

            String username = command.getArgs ( 0 );
            Player player = Bukkit.getPlayerExact ( username );

            if (player == null || !player.isOnline ( )) {
                command.getSender ( ).sendMessage ( CC.translate ( "&cEl jugador &e" + username + " &cno está en línea o no existe." ) );
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt ( command.getArgs ( 1 ) );
                if (amount <= 0) {
                    command.getSender ( ).sendMessage ( CC.translate ( "&cLa cantidad debe ser un número positivo." ) );
                    return;
                }
            } catch (NumberFormatException ex) {
                command.getSender ( ).sendMessage ( CC.translate ( "&cLa cantidad debe ser un número válido." ) );
                return;
            }

            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( username ).getDBCPlayer ( );
            boolean hasBooster = false;
            DecimalFormat formatter = new DecimalFormat ( "#,###" );
            amount = General.getLVL ( player ) * amount;
            // Booster global
            if (boosterActive) {
                int boosterGain = (int) (amount * (boosterMultiplier - 1.0));
                int reward = boosterGain + amount;
                idbcPlayer.setTP ( reward + idbcPlayer.getTP ( ) );
                player.sendMessage ( CC.translate ( "&e+ &6" + formatter.format ( boosterGain ) + " &a(Booster Global x" + (boosterMultiplier - 1.0) + ")" ) );
                hasBooster = true;
            }

            // Booster VIP
            VIPBooster booster = boosterHandler.findBooster ( player.getUniqueId ( ) );
            if (booster != null && booster.isActive ( )) {
                int bonus = (int) (amount * booster.getMultiplier ( )) + amount;
                idbcPlayer.setTP ( idbcPlayer.getTP ( ) + bonus );
                idbcPlayer.sendMessage ( CC.translate ( "&e+ &6" + formatter.format ( (int) (amount * booster.getMultiplier ( )) ) + " (Booster VIP)" ) );
                hasBooster = true;
            }
            if (boosterPlayer.containsKey ( player.getUniqueId ( ) )) {
                double multiplier = boosterPlayer.get ( player.getUniqueId ( ) );
                int bonus = (int) (amount * multiplier);
                int reward = bonus + amount;
                idbcPlayer.setTP ( idbcPlayer.getTP ( ) + reward );
                idbcPlayer.sendMessage ( CC.translate ( "&e+ &6" + formatter.format ( bonus ) + " (Booster Personalizado)" ) );
                hasBooster = true;
            }
            // Booster personalizado (PBooster)
            ConcurrentHashMap<UUID, PBooster> pMultiplier = BoosterDataHandler.getBoosterPMultiplier ( );
            if (pMultiplier.containsKey ( player.getUniqueId ( ) )) {
                double multiplier = pMultiplier.get ( player.getUniqueId ( ) ).getMultiplier ( );
                int bonus = (int) (amount * multiplier);
                int reward = bonus + amount;
                idbcPlayer.setTP ( idbcPlayer.getTP ( ) + reward );
                idbcPlayer.sendMessage ( CC.translate ( "&e+ &6" + formatter.format ( bonus ) + " (Booster Personalizado)" ) );
                hasBooster = true;
            }
            if (!hasBooster) {
                idbcPlayer.setTP ( idbcPlayer.getTP ( ) + amount );
            }
            idbcPlayer.sendMessage ( CC.translate ( "&e+ &6" + formatter.format ( amount ) + " TP recibido." ) );
            player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
            command.getSender ( ).sendMessage ( CC.translate ( "&aLe diste &e" + formatter.format ( amount ) + " TP &aal jugador &e" + username + "&a." ) );
        } catch (Exception ex) {
            command.getSender ( ).sendMessage ( CC.translate ( "&cHa ocurrido un error al ejecutar el comando. Revisa la consola para más detalles." ) );
            ex.printStackTrace ( );
        }
    }
}
