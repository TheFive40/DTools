package org.delaware.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        String action = args[0];
        switch(action.toLowerCase().trim()) {
            case "add":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cYou must be holding an item!"));
                    return;
                }
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID!"));
                    return;
                }
                if(CustomItems.contains(args[1])) {
                    player.sendMessage(CC.translate("&4" + args[1] + "&c already exists"));
                    return;
                }
                CustomItems custom = new CustomItems(player.getItemInHand());
                custom.addCustomItem(args[1]);
                player.sendMessage(CC.translate("&aItem registered correctly"));
                break;
            case "list":
                if(CustomItems.getAllCustomItems().isEmpty()) {
                    player.sendMessage(CC.translate("&cThere are no custom items registered"));
                    return;
                }
                for(String item : CustomItems.getAllCustomItems()) {
                    player.sendMessage(CC.translate("&a" + item));
                }
                break;
            case "get":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID!"));
                    return;
                }
                String key = args[1];
                if(!CustomItems.contains(key.toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem &4" + key + " &cdoes not exist!"));
                    return;
                }
                ItemStack item = CustomItems.getCustomItem(key).toItemStack();
                player.getInventory().addItem(item);
                player.sendMessage(CC.translate("&2Given &a" + key + " &2to " + player.getName()));
                break;
            case "remove":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID"));
                    player.sendMessage(CC.translate("&cUse /dbCustomItems list to see all currently registered items"));
                    return;
                }
                if(!CustomItems.contains(args[1])) {
                    player.sendMessage(CC.translate("&c" + args[1] + " doesn't exist"));
                    return;
                }
                CustomItems.removeItem(args[1]);
                player.sendMessage(CC.translate("&a" + args[1] + " &2Deleted correctly"));
        }
    }
}
