package org.delaware.commands;

import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

import static org.delaware.commands.BussyCommand.staff;

public class BussyListCommand extends BaseCommand {
    @Command(name = "ocupados", description = "Mira la lista de staffs que estan ocupados")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        String message = "--------------------------------------------";
        command.getSender ( ).sendMessage ( CC.translate ( "&c&l&m" + message ) );
        staff.forEach ( ( k, v ) -> {
            command.getSender ( ).sendMessage ( CC.translate ( "&4- " + k + ": &c" + v ) );
        } );
        command.getSender ( ).sendMessage ( CC.translate ( "&c&l&m" + message ) );
    }
}
