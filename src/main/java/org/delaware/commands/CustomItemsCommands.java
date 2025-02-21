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
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&c" + args[1] + " doesn't exist"));
                    return;
                }
                CustomItems.removeItem(args[1].toUpperCase().trim());
                player.sendMessage(CC.translate("&a" + args[1] + " &2Deleted correctly"));
                break;
            //dbcustomitems addEffect <ID> <Stat to boost> <boostID> <Operation> <value>
            case "addeffect":
                if(args.length < 6) {
                    player.sendMessage(CC.translate("&7---------------------------------------------------"));
                    player.sendMessage(CC.translate("&6Correct usage: /dbCustomItems addEffect <ID> <Stat> <boostID> <Operation> <Value>"));
                    player.sendMessage(CC.translate("&eID -> &6Custom item's ID, use /dbCustomItems list to see them"));
                    player.sendMessage(CC.translate("&eStat -> &6Stat to boost, these can be: str, dex, con, wil, mnd, spi"));
                    player.sendMessage(CC.translate("&eboostID -> &6Attribute ID for the boost"));
                    player.sendMessage(CC.translate("&eOperation -> &6+, -, *, /"));
                    player.sendMessage(CC.translate("&b&lExample:\n&r&b/dbCustomItems addEffect KITGOKU str KIT_GOKU * 1.15 &e-> &6Provides a 15% STR boost"));
                    player.sendMessage(CC.translate("&7---------------------------------------------------"));
                    return;
                }
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem &4" + args[1] + " &cdoes not exist!"));
                    return;
                }
                String[] validStats = {"str", "dex", "con", "wil", "mnd", "spi"};
                boolean hasValidStat = false;
                for(String stat : validStats) {
                    if (stat.equals(args[2].toLowerCase().trim())) {
                        hasValidStat = true;
                        break;
                    }
                }
                if(!hasValidStat) {
                    player.sendMessage(CC.translate("&cInvalid stat!"));
                    player.sendMessage(CC.translate("&cValid stats are str, dex, con, wil, mnd, spi"));
                    return;
                }
                String[] validOperations = {"+", "-", "*", "/"};
                boolean hasValidOperation = false;
                for(String operation : validOperations) {
                    if(operation.equals(args[4].trim())) {
                        hasValidOperation = true;
                        break;
                    }
                }
                if(!hasValidOperation) {
                    player.sendMessage(CC.translate("&cInvalid operation!"));
                    player.sendMessage(CC.translate("&cValid operations are +, -, *, /"));
                    return;
                }
                try {
                    Double.parseDouble(args[5].trim());
                } catch (NumberFormatException error) {
                    player.sendMessage(CC.translate("&cValue must be a number!"));
                    return;
                }
                CustomItems cItem = CustomItems.getCustomItem(args[1].toUpperCase().trim());
                cItem.addBoost(args[1].toUpperCase().trim(), args[2].toLowerCase().trim(), args[3].toUpperCase().trim(), args[4].trim(), args[5]);
                player.sendMessage(CC.translate("&aBoost added to item " + args[1] + " &acorrectly"));
        }
    }
}
