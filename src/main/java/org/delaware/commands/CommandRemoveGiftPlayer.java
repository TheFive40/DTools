package org.delaware.commands;

import org.delaware.tools.commands.*;
import org.delaware.events.*;
import org.delaware.tools.*;
import java.io.IOException;

public class CommandRemoveGiftPlayer extends BaseCommand {
    @Command(name = "removeplayergift", permission = "removeplayergift.use", usage = "&cPrueba utilizando &8 /giftpremove <player>"
            , aliases = "giftpremove")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if (command.getArgs().length > 0) {
            interactWithGift.regalosEncontrados.remove(command.getArgs(0));
            interactWithGift.misionCompletada.remove(command.getArgs(0));
            interactWithGift.contadorRegalos.remove(command.getArgs(0));
            command.getPlayer().sendMessage(CC.translate("&cMisión de los regalos reiniciada para el jugador &4" +
                    command.getArgs(0)));
            return;
        }
        command.getPlayer().sendMessage(CC.translate(command.getCommand().getUsage()));
    }
}
