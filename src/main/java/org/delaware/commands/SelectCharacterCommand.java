package org.delaware.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.Character;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SelectCharacterCommand extends BaseCommand {
    public static ConcurrentHashMap<String, CopyOnWriteArrayList<Character>> characterHashMap = new ConcurrentHashMap<>();
    private Character selectCharacter = null;

    @Command(name = "selectCharacter", aliases = "selectCharacter")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Player player = command.getPlayer();
        if (command.getArgs().length < 2) {
            command.getPlayer().sendMessage(CC.translate("&cPrueba utilizando &7/selectCharacter <Current name> <name>"));
            return;
        }
        String currentName = command.getArgs(0);
        String name = command.getArgs(1);
        ;
        AtomicBoolean isPrimaryOwner = new AtomicBoolean(false);
        if (characterHashMap.containsKey(player.getName())) {
            CopyOnWriteArrayList<Character> characters = characterHashMap.get(player.getName());
            String finalName = name;
            AtomicBoolean isOwner = new AtomicBoolean(false);
            characterHashMap.forEach((k, v) -> {
                if (k.equalsIgnoreCase(player.getName())) {
                    v.forEach(e -> {
                        if (e.getName().equalsIgnoreCase(name)) {
                            isOwner.set(true);
                        }
                    });
                }
            });
            if (!isOwner.get()) {
                player.sendMessage(CC.translate("&c¡Tu no eres dueño de ese usuario!"));
                return;
            }
            for (Character e : characters) {
                if (currentName.equalsIgnoreCase(e.getName()) && !e.isInUse()) {
                    player.sendMessage(CC.translate("&c¡No estás usando el personaje de " + currentName + "!"));
                    return;
                }
                if (e.getName().equalsIgnoreCase(currentName) && !e.isInUse()) {
                    player.sendMessage(CC.translate("&c¡No estas utilizando el personaje de " + currentName + "!"));
                    return;
                }
                if (currentName.equalsIgnoreCase(e.getName())) {
                    Character characterPlayer = new Character(currentName, player.getUniqueId().toString(),
                            General.getCon(player), General.getDex(player),
                            General.getStr(player), General.getWil(player), General.getMnd(player),
                            General.getSpi(player), General.getPlayerTps(player), General.getPlayerRace(player),
                            General.getPlayerClass(player));
                    characters.remove(e);
                    characterPlayer.setInUse(false);
                    characters.add(characterPlayer);
                    characterHashMap.put(player.getName(), characters);
                    isPrimaryOwner.set(true);
                }
                if (finalName.equalsIgnoreCase(e.getName())) {
                    selectCharacter = e;
                    selectCharacter.setInUse(true);
                }

            }
            if (selectCharacter != null && isOwner.get() && isPrimaryOwner.get()) {
                ConsoleCommandSender consoleCommandSender = player.getServer().getConsoleSender();
                player.setDisplayName(finalName);
                player.setCustomName(finalName);
                player.setCustomNameVisible(true);
                General.setSTR(player, selectCharacter.getStr());
                General.setDEX(player, selectCharacter.getDex());
                General.setCON(player, selectCharacter.getCon());
                General.setWIL(player, selectCharacter.getWil());
                General.setMND(player, selectCharacter.getMnd());
                General.setSPI(player, selectCharacter.getSpi());
                General.setPlayerClass(player, selectCharacter.getDbcClass());
                General.setPlayerRace(player,selectCharacter.getRace());
                General.getAndRemoveTps(player, General.getPlayerTps(player));
                General.setPlayerTps(player, selectCharacter.getTps());
                player.getServer().dispatchCommand(consoleCommandSender,"nick " + player.getName() + " " + selectCharacter.getName());
                player.sendMessage(CC.translate("&c¡Personaje actualizado a " + finalName + " correctamente!"));
            }
            if (!isPrimaryOwner.get() || !isOwner.get()) {
                player.sendMessage(CC.translate("&c¡La cuenta especificada no es de tu propiedad!"));
            }
        } else {
            player.sendMessage(CC.translate("&c¡Por favor registrate y crea una personaje con el comando" +
                    "&7 /createCharacter <name>"));
        }
    }
}
