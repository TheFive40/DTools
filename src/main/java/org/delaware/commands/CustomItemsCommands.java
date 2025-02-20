package org.delaware.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

public class CustomItemsCommands extends BaseCommand {
    @Command(name = "dbCustomItems", aliases = {"itemscustom", "itemcustom"}, permission = "DBFUTURE.CUSTOMITEMS", usage = "&cCorrect usage:" +
            " /dbCustomItems <action> | actions can be add, update, remove, list")
    @Override
    public void onCommand(CommandArgs command) {
        String[] args = command.getArgs();
        Player player = command.getPlayer();
        if(args.length < 1) {
            player.sendMessage(command.getCommand().getUsage());
            return;
        }
        Bukkit.getConsoleSender().sendMessage(args[0]);
        String action = args[0];
        switch(action.toLowerCase().trim()) {
            case "add":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cYou must be holding an item!"));
                    return;
                }
                CustomItems custom = new CustomItems(player.getItemInHand());
                custom.addCustomItem("test");
                break;
            case "list":
                if(CustomItems.getAllCustomItems().isEmpty()) return;
                for(String item : CustomItems.getAllCustomItems()) {
                    player.sendMessage(CC.translate("&a" + item));
                }
                break;
        }
    }
}
