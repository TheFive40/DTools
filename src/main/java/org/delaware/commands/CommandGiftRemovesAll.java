package org.delaware.commands;

import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

import static org.delaware.events.InteractWithGiftsEvent.*;

public class CommandGiftRemovesAll extends BaseCommand {
    @Command(name = "removegiftAll", description = "Remueve todos los regalos registrados", permission = "dtools.giftremoveall", aliases = "removegiftAll")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        regalosEncontrados.clear ();
        contadorRegalos.clear ();
        misionCompletada.clear ( );
        CommandAddGift.itemStackHashMap.clear ();
        CommandAddGift.itemStacks.clear ();
        command.getPlayer ( ).sendMessage ( "&aTodos los regalos han sido removidos exitosamente." );
    }
}
