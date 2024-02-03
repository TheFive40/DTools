package org.delaware.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.delaware.Main;
import org.delaware.commands.CreateCharacterCommand;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.model.Character;
import static org.delaware.commands.CreateCharacterCommand.characters;
import static org.delaware.commands.CreateCharacterCommand.isCreate;
import static org.delaware.commands.SelectCharacterCommand.characterHashMap;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onPlayerChat(org.bukkit.event.player.PlayerChatEvent event){
        if(CreateCharacterCommand.isCreate.containsKey(event.getPlayer().getName()) &&
        event.getMessage().equalsIgnoreCase("listo")){
           Player player = Main.instance.getServer().getPlayer(event.getPlayer().getName());
            setNames(isCreate.get(player.getName()), player);
            isCreate.remove(player.getName());
            event.setCancelled(true);
        }
    }
    public void setNames(String name ,Player player){
        Character characterTwoPlayer = new Character(name, player.getUniqueId().toString(),
                General.getCon(player), General.getDex(player),
                General.getStr(player), General.getWil(player), General.getMnd(player),
                General.getSpi(player), General.getPlayerTps(player), General.getPlayerRace(player),
                General.getPlayerClass(player));
        characterTwoPlayer.setInUse(true);
        characters.add(characterTwoPlayer);
        characterHashMap.put(player.getName(), characters);
        General.readAllNBT(player,name,false);
        player.sendMessage(CC.translate("&c¡Personaje " + name + " creado por primera vez!"));
    }
}
