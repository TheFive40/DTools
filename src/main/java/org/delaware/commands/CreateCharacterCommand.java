package org.delaware.commands;

import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.Race;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.Character;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.delaware.commands.SelectCharacterCommand.characterHashMap;

public class CreateCharacterCommand extends BaseCommand implements Serializable {
    public static  CopyOnWriteArrayList<Character> characters;
    public static ConcurrentHashMap<String, ConcurrentHashMap<String,Object>> accounts = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>> playerData = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> isCreate = new ConcurrentHashMap<>();
    @Command(name = "charactercreate", aliases = "createCharacter", usage = "&cPrueba utilizando &7/createCharacter <Current name> <name>"
    , permission = "characterscreate")
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
            characters = characterHashMap.get(player.getName());
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
                        General.getSpi(player), General.getPlayerTps(player), General.getPlayerRace(player),
                        General.getPlayerClass(player));
                characters.forEach(e -> {
                    if (e.getName().equalsIgnoreCase(currentName)) characters.remove(e);
                });
                General.readAllNBT(player,currentName,false);
                characterPlayer.setInUse(false);
                characters.add(characterPlayer);
                characterHashMap.put(player.getName(), characters);
                player.getInventory().clear();
                player.setDisplayName(name);
                player.setCustomName(name);
                player.setCustomNameVisible(true);
                /*General.setSTR(player, 10);
                General.setDEX(player, 10);
                General.setCON(player, 10);
                General.setWIL(player, 10);
                General.setMND(player, 10);
                General.setSPI(player, 10);

                Character.setPlayerClass(command.getArgs(3), player);
                General.setPlayerRace(player, getRaceInGame(command.getArgs(2)));
                General.getAndRemoveTps(player, General.getPlayerTps(player));
                Character characterTwoPlayer = new Character(name, player.getUniqueId().toString(),
                        General.getCon(player), General.getDex(player),
                        General.getStr(player), General.getWil(player), General.getMnd(player),
                        General.getSpi(player), General.getPlayerTps(player), General.getPlayerRace(player)
                        , General.getPlayerClass(player));
                characterTwoPlayer.setInUse(true);
                characters.add(characterTwoPlayer);
                characterHashMap.put(player.getName(), characters);
                player.sendMessage(CC.translate("&c¡Personaje " + name + " creado correctamente!"));*/
                General.startNew(player);
                isCreate.put(player.getName(), name);
                player.sendMessage(CC.translate("&aEscribe listo una vez hayas terminado de crear tu personaje"));

            } else {
                player.sendMessage(CC.translate("&cNo eres dueño del personaje " + currentName));
            }
        } else {
            if (!currentName.equalsIgnoreCase(player.getName())) {
                player.sendMessage(CC.translate("&cNo eres dueño del personaje " + currentName));
                return;
            }
            characters = new CopyOnWriteArrayList<>();
            Character characterPlayer = new Character(player.getName(), player.getUniqueId().toString(),
                    General.getCon(player), General.getDex(player),
                    General.getStr(player), General.getWil(player), General.getMnd(player),
                    General.getSpi(player), General.getPlayerTps(player), General.getPlayerRace(player)
                    , General.getPlayerClass(player));
            characterPlayer.setInUse(false);
            characters.add(characterPlayer);
            characterHashMap.put(player.getName(), characters);
            player.getInventory().clear();
            player.setDisplayName(name);
            player.setCustomName(name);
            player.setCustomNameVisible(true);
            General.readAllNBT(player,player.getName(),false);
            /*General.setSTR(player, 10);
            General.setDEX(player, 10);
            General.setCON(player, 10);
            General.setWIL(player, 10);
            General.setMND(player, 10);
            General.setSPI(player, 10);
            Character.setPlayerClass(command.getArgs(3), player);
            General.setPlayerRace(player, getRaceInGame(command.getArgs(2)));
            General.getAndRemoveTps(player, General.getPlayerTps(player));
            Character characterTwoPlayer = new Character(name, player.getUniqueId().toString(),
                    General.getCon(player), General.getDex(player),
                    General.getStr(player), General.getWil(player), General.getMnd(player),
                    General.getSpi(player), General.getPlayerTps(player), General.getPlayerRace(player),
                    General.getPlayerClass(player));
            characterTwoPlayer.setInUse(true);
            characters.add(characterTwoPlayer);
            characterHashMap.put(player.getName(), characters);
            player.sendMessage(CC.translate("&c¡Personaje " + name + " creado por primera vez!"));*/
            General.startNew(player);
            isCreate.put(player.getName(), name);
            player.sendMessage(CC.translate("&aEscribe listo una vez hayas terminado de crear tu personaje"));
        }
    }

    public Race getRaceInGame(String race) {
        Race race1;
        switch (race.toUpperCase()) {
            case "HUMANO":
                race1 = Race.HUMANO;
                break;
            case "SAIYAN":
                race1 = Race.SAIYAJIN;
                break;
            case "NAMEKIANO":
                race1 = Race.NAMEKIANO;
                break;
            case "MAJIN":
                race1 = Race.MAJIN;
                break;
            case "SEMI-SAIYAN":
                race1 = Race.SEMI_SAIYAN;
                break;
            case "ARCOSIANO":
                race1 = Race.ARCOSIANO;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + race.toUpperCase());
        }
        return race1;
    }
}
