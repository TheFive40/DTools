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
            player.sendMessage(CC.translate("&7---------------------------------------------------"));
            player.sendMessage(CC.translate("&6Available Commands:"));
            player.sendMessage(CC.translate("&6dbCustomItems add <ID> -> &eAdds the item the player's holding to the config"));
            player.sendMessage(CC.translate("&6dbCustomItems removeBoost <ID> -> &eRemoves the last applied boost to <ID>"));
            player.sendMessage(CC.translate("&6dbCustomItems removeEffect <ID> -> &eRemoves effects applied to <ID>"));
            player.sendMessage(CC.translate("&6dbCustomItems list -> &eShows all currently registered items"));
            player.sendMessage(CC.translate("&6dbCustomItems get <ID> -> &eGives the player an item registered"));
            player.sendMessage(CC.translate("&6dbCustomItems remove <ID> -> &eRemoves an item"));
            player.sendMessage(CC.translate("&6dbCustomItems makeUnbreakable -> &eAdds the item the player's holding to the config"));
            player.sendMessage(CC.translate("&6dbCustomItems setDamage <Damage> -> &eChanges the item's damage"));
            player.sendMessage(CC.translate("&6dbCustomItems clone <ID> -> &eCopies a custom item's data to your item in hand (DO NOT USE UNLESS YOU KNOW WHAT YOU'RE DOING)"));
            player.sendMessage(CC.translate("&6dbCustomItems info <ID> -> &eShows information about a registered item"));
            player.sendMessage(CC.translate("&6dbCustomItems addBoost -> &eUse for bonus attributes, for help type /dbCustomItems addBoost"));
            player.sendMessage(CC.translate("&6dbCustomItems addEffect -> &eFor adding set effects, for help type /dbCustomItems addEffect"));
            player.sendMessage(CC.translate("&7---------------------------------------------------"));
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
                if(CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&4" + args[1] + "&c already exists"));
                    return;
                }
                CustomItems custom = new CustomItems(player.getItemInHand());
                custom.addCustomItem(args[1].toUpperCase().trim());
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
            //dbcustomitems addBoost <ID> <Stat to boost> <boostID> <Operation> <value>
            case "addboost":
                if(args.length < 6) {
                    player.sendMessage(CC.translate("&7---------------------------------------------------"));
                    player.sendMessage(CC.translate("&6Correct usage: /dbCustomItems addBoost <ID> <Stat> <boostID> <Operation> <Value>"));
                    player.sendMessage(CC.translate("&eID -> &6Custom item's ID, use /dbCustomItems list to see them"));
                    player.sendMessage(CC.translate("&eStat -> &6Stat to boost, these can be: str, dex, con, wil, mnd, spi"));
                    player.sendMessage(CC.translate("&eboostID -> &6Attribute ID for the boost"));
                    player.sendMessage(CC.translate("&eOperation -> &6+, -, *, /"));
                    player.sendMessage(CC.translate("&b&lExample:\n&r&b/dbCustomItems addBoost KITGOKU_CHESTPLATE str KIT_GOKU * 1.15 &e-> &6Provides a 15% STR boost"));
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
                cItem.addBoost(args[1].toUpperCase().trim(), args[2].toLowerCase().trim(), args[3].toUpperCase().trim(), args[4].trim(), Double.parseDouble(args[5].trim()));
                player.sendMessage(CC.translate("&aBoost added to item " + args[1] + " &acorrectly"));
                break;
            //dbcustomitems addSetEffect <ID> <KIT_NAME> <EFFECTID> <LEVEL>
            case "addeffect":
                if(args.length < 5) {
                    player.sendMessage(CC.translate("&7---------------------------------------------------"));
                    player.sendMessage(CC.translate("&6Correct usage: /dbCustomItems addEffect <ID> <KIT_NAME> <EFFECTID> <LEVEL>"));
                    player.sendMessage(CC.translate("&eID -> &6Custom item's ID, use /dbCustomItems list to see them"));
                    player.sendMessage(CC.translate("&eKIT_NAME -> &6Kit's name (all three pieces must have same kit name)"));
                    player.sendMessage(CC.translate("&eEFFECTID -> &6What effect should be given to players wearing the whole set"));
                    player.sendMessage(CC.translate("&6These can be: HEALTHREGEN, KIREGEN, STAMINAREGEN"));
                    player.sendMessage(CC.translate("&eLEVEL -> &6Determines how strong the effect is"));
                    player.sendMessage(CC.translate("&b&lExample:\n&r&b/dbCustomItems addEffect KITGOKU_CHESTPLATE KIT_GOKU HEALTHREGEN 2 &e-> &62% health regeneration while wearing full set"));
                    player.sendMessage(CC.translate("&7---------------------------------------------------"));
                    return;
                }
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem &4" + args[1] + " &cdoes not exist"));
                    return;
                }
                String effectid;
                switch(args[3].toUpperCase().trim()) {
                    case "HEALTHREGEN":
                        effectid = "HEALTHREGEN";
                        break;
                    case "KIREGEN":
                        effectid = "KIREGEN";
                        break;
                    case "STAMINAREGEN":
                        effectid = "STAMINAREGEN";
                        break;
                    default:
                        player.sendMessage(CC.translate("&cInvalid effect ID!"));
                        player.sendMessage(CC.translate("&cValid effect IDs are: healthregen, kiregen, staminaregen"));
                        return;
                }
                try {
                    Integer.parseInt(args[4].trim());
                } catch (NumberFormatException error) {
                    player.sendMessage(CC.translate("&cLevel must be a whole number!"));
                    return;
                }
                if(Integer.parseInt(args[4].trim()) < 1 || Integer.parseInt(args[4].trim()) > 100) {
                    player.sendMessage(CC.translate("&cLevel must be a number above 0 and below 100"));
                    return;
                }
                CustomItems customItem = CustomItems.getCustomItem(args[1].toUpperCase().trim());
                int effectLevel = Integer.parseInt(args[4].trim());
                customItem.addSetEffect(args[1].toUpperCase().trim(), args[2].toUpperCase().trim(), effectid, effectLevel);
                player.sendMessage(CC.translate("&aBonus effect added correctly to item " + args[1]));
                break;
            case "makeunbreakable":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cYou must be holding an item!"));
                    return;
                }
                CustomItems toUnbreakable = new CustomItems(player.getItemInHand());
                toUnbreakable.setUnbreakable(true);
                player.setItemInHand(toUnbreakable.toItemStack());
                player.sendMessage(CC.translate("&aItem made unbreakable correctly"));
                break;
            case "removeboost":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID!"));
                    return;
                }
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem &4" + args[1] + " &cdoes not exist!"));
                    return;
                }
                CustomItems customI = CustomItems.getCustomItem(args[1].toUpperCase().trim());
                customI.deleteLastBoost(args[1].toUpperCase().trim());
                player.sendMessage(CC.translate("&cLast boost applied to " + args[1] + " deleted correctly"));
                break;
            case "removeeffect":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID!"));
                    return;
                }
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem &4" + args[1] + " &cdoes not exist!"));
                    return;
                }
                CustomItems customIt = CustomItems.getCustomItem(args[1].toUpperCase().trim());
                customIt.deleteEffects(args[1].toUpperCase().trim());
                player.sendMessage(CC.translate("&cEffect applied to " + args[1] + " deleted correctly"));
                break;
            case "setdamage":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cCorrect usage: /dbCustomItems setDamage <Damage>"));
                    return;
                }
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cYou must be holding an item!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1]);
                } catch (NumberFormatException error) {
                    player.sendMessage(CC.translate("&cDamage must be a number!"));
                    return;
                }
                CustomItems changeDmg = new CustomItems(player.getItemInHand());
                changeDmg.setDamage(Integer.parseInt(args[1]));
                player.setItemInHand(changeDmg.toItemStack());
                player.sendMessage(CC.translate("&aDamage changed correctly!"));
                break;
            case "clone":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify which ID to clone!"));
                    return;
                }
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cYou're not holding an item!"));
                    return;
                }
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem with ID " + args[1] + " doesn't exist"));
                    return;
                }
                CustomItems clone = new CustomItems(player.getItemInHand());
                clone.clone(args[1].toUpperCase().trim());
                player.setItemInHand(clone.toItemStack());
                player.sendMessage(CC.translate("&aConfig from " + args[1].toUpperCase().trim() + " set to your item in hand!"));
                player.sendMessage(CC.translate("&aNote that this doesn't save a new item in the config, only copies the effects from an already existing item"));
                break;
            case "info":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify an ID!"));
                    return;
                }
                if(!CustomItems.contains(args[1].toUpperCase().trim())) {
                    player.sendMessage(CC.translate("&cItem with ID " + args[1] + " doesn't exist"));
                    return;
                }
                CustomItems info = CustomItems.getCustomItem(args[1].toUpperCase().trim());
                String name = info.getDisplayName() != null ? info.getDisplayName() : "None set";
                player.sendMessage(CC.translate("&8---------------------------------------------------"));
                player.sendMessage(CC.translate("&7Information about item ID &f" + args[1].toUpperCase().trim() + "&7:"));
                player.sendMessage(CC.translate("&7Material: &f" + info.toItemStack().getType().toString()));
                player.sendMessage(CC.translate("&7ID: &f" + info.getItemID()));
                player.sendMessage(CC.translate("&7Name: &f" + name));
                if(info.getLore() != null) {
                    player.sendMessage(CC.translate("&7Lore:"));
                    for(String cLore : info.getLore()) {
                       player.sendMessage(CC.translate("&f" + cLore));
                    }
                }else {
                    player.sendMessage(CC.translate("&7Lore: &fNone set"));
                }
                if(info.hasCustomBoost()) {
                    player.sendMessage(CC.translate("&7Active boosts:"));
                    for(int i = 0; i < info.getBoostIDS().size(); i++) {
                        player.sendMessage(CC.translate("&7" + (i+1) + "º: " + "&7Stat: &f" + info.getStats().get(i) + " &7bonusID: &f" + info.getBoostIDS().get(i)+ " &7Operation: &f" + info.getOperations().get(i) + " &7Value: &f" + info.getValues().get(i)));
                    }
                }else {
                    player.sendMessage(CC.translate("&7Active boosts: &fNone set"));
                }
                if(info.hasSetEffect()) {
                    player.sendMessage(CC.translate("&7Active effects (full set):"));
                    for(int i = 0; i < info.getEffect().size(); i++) {
                        player.sendMessage(CC.translate("&7" + (i+1) + "º: " + "&7Effect: &f" + info.getEffect().get(i) + " &7Level: &f" + info.getLevel().get(i)));
                    }
                    player.sendMessage(CC.translate("&7Part of kit: &f" + info.getKitName()));
                }else {
                    player.sendMessage(CC.translate("&7Active effects (full set): &fNone"));
                }
                player.sendMessage(CC.translate("&8---------------------------------------------------"));
                break;
                //dbcustomitem changematerial <newmaterial>
            case "changematerial":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cYou must specify a new material!"));
                    return;
                }
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cYou're not holding an item!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                } catch (NumberFormatException error) {
                    player.sendMessage(CC.translate("&cNew material must be a number!"));
                    return;
                }
                CustomItems changeItem = new CustomItems(player.getItemInHand());
                changeItem.setItemID(Integer.parseInt(args[1].trim()));
                player.setItemInHand(changeItem.toItemStack());
                break;
        }
    }
}
