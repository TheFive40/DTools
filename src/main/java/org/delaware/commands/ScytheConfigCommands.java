package org.delaware.commands;

import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.Scythe.ScytheRunnable;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class ScytheConfigCommands extends BaseCommand {
    @Command(name = "scytheconfig", permission = "DBFUTURE.SCYTHECONFIG", usage = "&cCorrect usage:" +
            " /dbCustomItems <action> | actions can be add, update, remove, list")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String[] args = command.getArgs();
        Player player = command.getPlayer();
        if(args.length < 1) {
            player.sendMessage(CC.translate("&8---------------------------------------------------"));
            player.sendMessage(CC.translate("&7Available commands:"));
            player.sendMessage(CC.translate("&7/ScytheConfig getID &f-> &7Gets current scythe's id"));
            player.sendMessage(CC.translate("&7/ScytheConfig setID <ID> &f-> &7Sets scythe's id for updating"));
            player.sendMessage(CC.translate("&7/ScytheConfig getMultiplier &f-> &7Gets current scythe's damage multiplier"));
            player.sendMessage(CC.translate("&7/ScytheConfig setMultiplier <NewMultiplier> &f-> &7Changes the current scythe's dmg multiplier"));
            player.sendMessage(CC.translate("&8---------------------------------------------------"));
            return;
        }
        switch(args[0].toLowerCase().trim()) {
            case "getid":
                player.sendMessage(CC.translate("&7Current Scythe's id is: &f" + Main.scytheConfig.get("ID")));
                break;
            case "getmultiplier":
                player.sendMessage(CC.translate("&7Current Scythe's multiplier is: &f" + Main.scytheConfig.get("dmgMultiplier")));
                break;
            case "setid":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                } catch (NumberFormatException error) {
                    player.sendMessage(CC.translate("&cValue must be a number!"));
                    return;
                }
                Main.scytheConfig.put("ID", Integer.valueOf(args[1].trim()));
                ScytheRunnable.saveToConfig();
                player.sendMessage(CC.translate("&aID set correctly!"));
                break;
            case "setmultiplier":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify a new multiplier!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                } catch (NumberFormatException error) {
                    player.sendMessage(CC.translate("&cValue must be a number!"));
                    return;
                }
                Main.scytheConfig.put("dmgMultiplier", Integer.valueOf(args[1].trim()));
                ScytheRunnable.saveToConfig();
                player.sendMessage(CC.translate("&aMultiplier set correctly!"));
                break;
        }
    }
}
