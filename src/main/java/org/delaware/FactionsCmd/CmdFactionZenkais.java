package org.delaware.FactionsCmd;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FactionsCommand;


public class CmdFactionZenkais extends FactionsCommand {
    public CmdFactionZenkais () {
        this.addAliases ( "zenkais" );
        this.desc = "Obten los zenkais de tu faction";
    }

    public void perform () {
        this.msg ( "Tienes 5000 Zenkais" );
    }
}
