package org.delaware.commands;

import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.CustomItems.Inventories.MainMenu;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ItemCreationMenu extends BaseCommand {
    @Command(name = "itemCreator", aliases = "crearitem", permission = "SPACEY.ITEMCREATOR")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Player player = command.getPlayer();
        Set<String> cItems = CustomItems.getAllCustomItems();
        ArrayList<CustomItems> custom = new ArrayList<>();
        for(String item : cItems) {
            custom.add(CustomItems.getCustomItem(item));
        }
        final SmartInventory INV = SmartInventory.builder().title(CC.translate("&bItems registrador")).id("ItemCreator").provider(new MainMenu(custom)).build();
        INV.open(player);
    }
}
