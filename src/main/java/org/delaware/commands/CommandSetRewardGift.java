package org.delaware.commands;

import org.delaware.events.InteractWithGiftsEvent;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class CommandSetRewardGift extends BaseCommand {


    @Command(name = "setReward", aliases = "setReward", permission = "addgift.setReward",
            usage = "&7Prueba utilizando &b/setReward <MULTIPLIER>")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        int reward = Integer.parseInt ( command.getArgs ( 0 ) );
        InteractWithGiftsEvent.MULTIPLIER = reward;
        command.getPlayer ().sendMessage ( CC.translate ( "&aRecompensa actualizada correctamente" ) );
    }


}
