package org.delaware.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.delaware.events.interactWithGift;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.ArrayList;

public class CommandSetRewardGift extends BaseCommand {


    @Command(name = "setReward", aliases = "setReward", permission = "addgift.setReward",
            usage = "&7Prueba utilizando &b/setReward <MULTIPLIER>")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        int reward = Integer.parseInt ( command.getArgs ( 0 ) );
        interactWithGift.MULTIPLIER = reward;
        command.getPlayer ().sendMessage ( CC.translate ( "&aRecompensa actualizada correctamente" ) );
    }


}
