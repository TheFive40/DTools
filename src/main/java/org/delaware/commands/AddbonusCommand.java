package org.delaware.commands;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class AddbonusCommand extends BaseCommand {
    @Command ( name = "bonustest",aliases = "bonustest")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (command.getArgs (0).equalsIgnoreCase ( "addbonus" )){
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( command.getPlayer ().getName ( ) ).getDBCPlayer ( );
            idbcPlayer.addBonusAttribute ( "str", "KIT_BLACK","*",1.15 );
        }else{
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( command.getPlayer ().getName ( ) ).getDBCPlayer ( );
            idbcPlayer.clearBonusAttribute ( "str" );
        }

    }
}
