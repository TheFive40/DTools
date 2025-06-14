package org.delaware.commands;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CommandNick extends BaseCommand {
    public static ConcurrentHashMap<Player, String> nicknames = new ConcurrentHashMap<> ( );

    @Command(name = "apodo", aliases = {"apodo","name"}, permission = "dtools.nick")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer();

        if (command.getArgs().length == 0) {
            player.sendMessage(CC.translate("&8[&6✦&8] &cUso correcto: &e/name <nickname>"));
            return;
        }

        General.getStaffs().forEach(e -> {
            e.sendMessage(CC.translate("&8[&e⚠ Advertencia&8] &c" + player.getName() +
                    " &eha solicitado cambiar su nickname a:"));
            e.sendMessage(CC.translate("&8[&e⚠ Advertencia&8]&c " + command.getArgs(0)));
            e.sendMessage(CC.translate("&8[&6✦&8] &7Usa &a/nickAccept &7para aprobar el cambio."));

            e.playSound(e.getLocation(), Sound.CAT_MEOW, 1.0f, 1.0f);
        });

        nicknames.put(player, command.getArgs(0));

        player.sendMessage(CC.translate("&7Has solicitado cambiar tu nickname a: &e" + command.getArgs(0)));
        player.sendMessage(CC.translate("&7Por favor espera a que un miembro del staff lo apruebe."));
    }
}
