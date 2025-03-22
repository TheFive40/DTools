package org.delaware.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.RegionTools.PlayerAccessManager;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class RegionCommands extends BaseCommand {
    @Command(name = "regionmanager", permission = "DBFUTURE.REGIONMANAGER", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String[] args = command.getArgs();
        CommandSender player = command.getSender();
        if(args.length == 0) {
            sendUsage(player);
            return;
        }
        switch(args[0].toLowerCase()) {
            case "grantaccess":
                if(args.length < 4) {
                    player.sendMessage(CC.translate("&cUso correcto: /regionmanager grantAccess (nick) (nombre de region) (tiempo) (tiempo de espera)"));
                    player.sendMessage(CC.translate("&cTiempo de espera es OPCIONAL"));
                    return;
                }
                Player playerCommand = Bukkit.getPlayer(args[1]);
                if(playerCommand == null || !playerCommand.isOnline()) {
                    player.sendMessage(CC.translate("&cEl jugador " + args[1] + " no existe o no está conectado"));
                    return;
                }
                if(!General.isConvertibleToInt(args[3].trim())) {
                    player.sendMessage("&cEl tiempo debe ser un numero entero!");
                    return;
                }
                if(Integer.parseInt(args[3].trim()) <= 0) {
                    player.sendMessage("&cEl tiempo debe ser mayor que 0!");
                    return;
                }
                if(args.length >= 5) {
                    if(!General.isConvertibleToInt(args[4].trim())) {
                        player.sendMessage("&cEl tiempo de espera debe ser un numero entero!");
                        return;
                    }
                    if(PlayerAccessManager.allPlayers.containsKey(playerCommand.getUniqueId().toString())) {
                        PlayerAccessManager.allPlayers.get(playerCommand.getUniqueId().toString()).grantAccess(args[2].trim(), Integer.parseInt(args[3].trim()), Integer.parseInt(args[4].trim()));
                    }else {
                        PlayerAccessManager m = new PlayerAccessManager(playerCommand);
                        m.grantAccess(args[2].trim(), Integer.parseInt(args[3].trim()), Integer.parseInt(args[4].trim()));
                    }
                    return;
                }
                if(PlayerAccessManager.allPlayers.containsKey(playerCommand.getUniqueId().toString())) {
                    PlayerAccessManager.allPlayers.get(playerCommand.getUniqueId().toString()).grantAccess(args[2].trim(), Integer.parseInt(args[3].trim()));
                }else {
                    PlayerAccessManager m = new PlayerAccessManager(playerCommand);
                    m.grantAccess(args[2].trim(), Integer.parseInt(args[3].trim()));
                }
                player.sendMessage(CC.translate("&aAcceso agregado."));
                break;
            case "setaccess":
                if(args.length < 4) {
                    player.sendMessage(CC.translate("&cUso correcto: /regionmanager setAccess (nick) (nombre de region) (tiempo) (tiempo de espera)"));
                    player.sendMessage(CC.translate("&cTiempo de espera es OPCIONAL"));
                    return;
                }
                Player playerCommand2 = Bukkit.getPlayer(args[1]);
                if(playerCommand2 == null || !playerCommand2.isOnline()) {
                    player.sendMessage(CC.translate("&cEl jugador " + args[1] + " no existe o no está conectado"));
                    return;
                }
                if(!General.isConvertibleToInt(args[3].trim())) {
                    player.sendMessage("&cEl tiempo debe ser un numero entero!");
                    return;
                }
                if(Integer.parseInt(args[3].trim()) <= 0) {
                    player.sendMessage("&cEl tiempo debe ser mayor que 0!");
                    return;
                }
                if(args.length >= 5) {
                    if(!General.isConvertibleToInt(args[4].trim())) {
                        player.sendMessage("&cEl tiempo de espera debe ser un numero entero!");
                        return;
                    }
                    if(PlayerAccessManager.allPlayers.containsKey(playerCommand2.getUniqueId().toString())) {
                        PlayerAccessManager.allPlayers.get(playerCommand2.getUniqueId().toString()).setAccess(args[2].trim(), Integer.parseInt(args[3].trim()), Integer.parseInt(args[4].trim()));
                    }else {
                        PlayerAccessManager m = new PlayerAccessManager(playerCommand2);
                        m.setAccess(args[2].trim(), Integer.parseInt(args[3].trim()), Integer.parseInt(args[4].trim()));
                    }
                    player.sendMessage(CC.translate("&aAcceso agregado."));
                    return;
                }
                if(PlayerAccessManager.allPlayers.containsKey(playerCommand2.getUniqueId().toString())) {
                    PlayerAccessManager.allPlayers.get(playerCommand2.getUniqueId().toString()).setAccess(args[2].trim(), Integer.parseInt(args[3].trim()));
                }else {
                    PlayerAccessManager m = new PlayerAccessManager(playerCommand2);
                    m.setAccess(args[2].trim(), Integer.parseInt(args[3].trim()));
                }
                player.sendMessage(CC.translate("&aAcceso agregado."));
                break;
            case "addaccess":
                if(args.length < 4) {
                    player.sendMessage(CC.translate("&cUso correcto: /regionmanager addAccess (nick) (nombre de region) (tiempo) (tiempo de espera)"));
                    player.sendMessage(CC.translate("&cTiempo de espera es OPCIONAL"));
                    return;
                }
                Player playerCommand3 = Bukkit.getPlayer(args[1]);
                if(playerCommand3 == null || !playerCommand3.isOnline()) {
                    player.sendMessage(CC.translate("&cEl jugador " + args[1] + " no existe o no está conectado"));
                    return;
                }
                if(!General.isConvertibleToInt(args[3].trim())) {
                    player.sendMessage("&cEl tiempo debe ser un numero entero!");
                    return;
                }
                if(Integer.parseInt(args[3].trim()) <= 0) {
                    player.sendMessage("&cEl tiempo debe ser mayor que 0!");
                    return;
                }
                if(args.length >= 5) {
                    if(!General.isConvertibleToInt(args[4].trim())) {
                        player.sendMessage("&cEl tiempo de espera debe ser un numero entero!");
                        return;
                    }
                    if(PlayerAccessManager.allPlayers.containsKey(playerCommand3.getUniqueId().toString())) {
                        PlayerAccessManager.allPlayers.get(playerCommand3.getUniqueId().toString()).addAccess(args[2].trim(), Integer.parseInt(args[3].trim()), Integer.parseInt(args[4].trim()));
                    }else {
                        PlayerAccessManager m = new PlayerAccessManager(playerCommand3);
                        m.addAccess(args[2].trim(), Integer.parseInt(args[3].trim()), Integer.parseInt(args[4].trim()));
                    }
                    player.sendMessage(CC.translate("&aAcceso agregado."));
                    return;
                }
                if(PlayerAccessManager.allPlayers.containsKey(playerCommand3.getUniqueId().toString())) {
                    PlayerAccessManager.allPlayers.get(playerCommand3.getUniqueId().toString()).addAccess(args[2].trim(), Integer.parseInt(args[3].trim()));
                }else {
                    PlayerAccessManager m = new PlayerAccessManager(playerCommand3);
                    m.addAccess(args[2].trim(), Integer.parseInt(args[3].trim()));
                }
                player.sendMessage(CC.translate("&aAcceso agregado."));
                break;
            case "getremainingtime":
                if(args.length < 3) {
                    player.sendMessage(CC.translate("&cUso correcto: /regionmanager getRemainingTime (nick) (nombre de region)"));
                    return;
                }
                Player playerRemaining = Bukkit.getPlayer(args[1]);
                if(!PlayerAccessManager.allPlayers.containsKey(playerRemaining.getUniqueId().toString())) {
                    playerRemaining.sendMessage(CC.translate("&cNo tienes tiempo restante en esa region"));
                    return;
                }
                PlayerAccessManager manager = PlayerAccessManager.allPlayers.get(playerRemaining.getUniqueId().toString());
                String timeLeft = manager.getRemainingTime(args[2].toUpperCase().trim());
                if(timeLeft != null) {
                    playerRemaining.sendMessage(CC.translate("&aTienes " + timeLeft + " restantes"));
                }else playerRemaining.sendMessage(CC.translate("&cNo tienes tiempo restante en esa region"));
                break;
            case "setpermanentaccess":
                if(args.length < 3) {
                    player.sendMessage(CC.translate("&cUso correcto: /regionmanager setPermanentAccess (nick) (nombre de region)"));
                    return;
                }
                Player playerCommand4 = Bukkit.getPlayer(args[1]);
                if(playerCommand4 == null || !playerCommand4.isOnline()) {
                    player.sendMessage(CC.translate("&cEl jugador " + args[1] + " no existe o no está conectado"));
                    return;
                }
                if(PlayerAccessManager.allPlayers.containsKey(playerCommand4.getUniqueId().toString())) {
                    PlayerAccessManager.allPlayers.get(playerCommand4.getUniqueId().toString()).setPermanentAccess(args[2]);
                }else {
                    PlayerAccessManager m = new PlayerAccessManager(playerCommand4);
                    m.setPermanentAccess(args[2]);
                }
                player.sendMessage(CC.translate("&aAcceso agregado."));
                break;
            default:
                sendUsage(player);
        }
    }
    private void sendUsage(CommandSender player) {
        player.sendMessage(CC.translate("&e---------------------"));
        player.sendMessage(CC.translate("&6Comandos disponibles:"));
        player.sendMessage(CC.translate("&6/regionmanager grantAccess (nick) (nombre de region) (tiempo) (tiempo de espera) &e-> &6Este comando le dará acceso a un jugador a una región por un tiempo especificado, y no hará nada si el jugador ya tiene/tuvo acceso a esta región, básicamente sólo sirve 1 vez."));
        player.sendMessage(CC.translate("&e---------------------"));
        player.sendMessage(CC.translate("&6/regionmanager setAccess (nick) (nombre de region) (tiempo) (tiempo de espera) &e-> &6Modifica el acceso de un jugador a una región, este comando reemplaza los valores que tenía el jugador (si es que tenía)"));
        player.sendMessage(CC.translate("&e---------------------"));
        player.sendMessage(CC.translate("&6/regionmanager addAccess (nick) (nombre de region) (tiempo) (tiempo de espera) &e-> &6Añade tiempo a una región, si el jugador ya tenía tiempo, lo suma"));
        player.sendMessage(CC.translate("&e---------------------"));
        player.sendMessage(CC.translate("&eNOTA: &6el tiempo de espera es un parámetro opcional, sirve para regiones con acceso temporal que se reestablece, por ejemplo, una región donde el jugador pueda ir 1 hora y luego tenga que esperar 6 horas."));
        player.sendMessage(CC.translate("&e---------------------"));
        player.sendMessage(CC.translate("&6/regionmanager getRemainingTime (nick) (nombre de region) &e-> &6Le dice al jugador cuanto tiempo restante tiene en cierta region"));
        player.sendMessage(CC.translate("&e---------------------"));
        player.sendMessage(CC.translate("&6/regionmanager setPermanentAccess (nick) (nombre de region) &e-> &6Proporciona acceso permanente a un jugador para cierta región"));
        player.sendMessage(CC.translate("&e---------------------"));
    }
}
