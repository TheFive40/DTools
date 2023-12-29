package org.delaware.commands;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.Character;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.delaware.commands.SelectCharacterCommand.characterHashMap;

public class CreateCharacterCommand extends BaseCommand {
    @Command(name = "charactercreate", aliases = "createCharacter", usage = "&cPrueba utilizando &7/createCharacter <Current name> <name>")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if (command.getArgs().length < 2) {
            command.getPlayer().sendMessage(CC.translate("&cPrueba utilizando &7/createCharacter <Current name> <name>"));
            return;
        }
        Player player = command.getPlayer();
        String currentName = command.getArgs(0);
        String name = command.getArgs(1);
        AtomicBoolean isPrimaryOwner = new AtomicBoolean(false);

        if (characterHashMap.containsKey(player.getName())) {
            CopyOnWriteArrayList<Character> characters = characterHashMap.get(player.getName());
            characterHashMap.forEach((k, v) -> {
                CopyOnWriteArrayList<Character> characterss = v;
                if (k.equalsIgnoreCase(player.getName()) && characterss.size() >= 3) {
                    player.sendMessage(CC.translate("&c¡No puedes crear otro personaje!, Haz alcanzado el limite permitido"));
                    return;
                }
                if (k.equalsIgnoreCase(player.getName())) {
                    v.stream().forEach(x -> {
                        if (x.getName().equalsIgnoreCase(currentName) && characterss.size() <= 3) {
                            isPrimaryOwner.set(true);
                        }
                    });
                }
            });
            if (isPrimaryOwner.get()) {
                Character characterPlayer = new Character(currentName, player.getUniqueId().toString(),
                        General.getCon(player), General.getDex(player),
                        General.getStr(player), General.getWil(player), General.getMnd(player),
                        General.getSpi(player), General.getPlayerTps(player));
                characters.forEach(e -> {
                    if (e.getName().equalsIgnoreCase(currentName)) characters.remove(e);
                });
                characterPlayer.setInUse(false);
                characters.add(characterPlayer);
                characterHashMap.put(player.getName(), characters);
                player.getInventory().clear();
                player.setDisplayName(name);
                player.setCustomName(name);
                player.setCustomNameVisible(true);
                General.setSTR(player, 10);
                General.setDEX(player, 10);
                General.setCON(player, 10);
                General.setWIL(player, 10);
                General.setMND(player, 10);
                General.setSPI(player, 10);
                General.getAndRemoveTps(player, General.getPlayerTps(player));
                Character characterTwoPlayer = new Character(name, player.getUniqueId().toString(),
                        General.getCon(player), General.getDex(player),
                        General.getStr(player), General.getWil(player), General.getMnd(player),
                        General.getSpi(player), General.getPlayerTps(player));
                characterTwoPlayer.setInUse(true);
                characters.add(characterTwoPlayer);
                characterHashMap.put(player.getName(), characters);
                player.sendMessage(CC.translate("&c¡Personaje " + name + " creado correctamente!"));
            } else {
                player.sendMessage(CC.translate("&cNo eres dueño del personaje " +  currentName));
            }
        } else {
            if(!currentName.equalsIgnoreCase(player.getName())){
                player.sendMessage(CC.translate("&cNo eres dueño del personaje " +  currentName));
                return;
            }
            CopyOnWriteArrayList<Character> characters = new CopyOnWriteArrayList<>();
            Character characterPlayer = new Character(player.getName(), player.getUniqueId().toString(),
                    General.getCon(player), General.getDex(player),
                    General.getStr(player), General.getWil(player), General.getMnd(player),
                    General.getSpi(player), General.getPlayerTps(player));
            characterPlayer.setInUse(false);
            characters.add(characterPlayer);
            characterHashMap.put(player.getName(), characters);
            player.getInventory().clear();
            player.setDisplayName(name);
            player.setCustomName(name);
            player.setCustomNameVisible(true);
            General.setSTR(player, 10);
            General.setDEX(player, 10);
            General.setCON(player, 10);
            General.setWIL(player, 10);
            General.setMND(player, 10);
            General.setSPI(player, 10);
            General.getAndRemoveTps(player, General.getPlayerTps(player));
            Character characterTwoPlayer = new Character(name, player.getUniqueId().toString(),
                    General.getCon(player), General.getDex(player),
                    General.getStr(player), General.getWil(player), General.getMnd(player),
                    General.getSpi(player), General.getPlayerTps(player));
            characterTwoPlayer.setInUse(true);
            characters.add(characterTwoPlayer);
            characterHashMap.put(player.getName(), characters);
            player.sendMessage(CC.translate("&c¡Personaje " + name + " creado por primera vez!"));
        }
    }
}
