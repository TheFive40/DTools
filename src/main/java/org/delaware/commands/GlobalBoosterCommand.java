package org.delaware.commands;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import java.io.IOException;
import static org.bukkit.Bukkit.getServer;

public class GlobalBoosterCommand extends BaseCommand {

    public static boolean boosterActive = false;
    public static double boosterMultiplier = 1.0;
    public static long boosterEndTime = 0;

    @Command(name = "booster", aliases = "booster", permission = "dtools.booster", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String[] args = command.getArgs();
        if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            if (!boosterActive) {
                command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &cNo hay un booster activo actualmente."));
                return;
            }

            boosterActive = false;
            boosterMultiplier = 1.0;
            boosterEndTime = 0;

            getServer().broadcastMessage(CC.translate("&8&m--------------------------------------"));
            getServer().broadcastMessage(CC.translate("&8[&6DBZ&8] &c&l¡El Booster Global ha terminado!"));
            getServer().broadcastMessage(CC.translate("&8&m--------------------------------------"));
            playGlobalSound(Sound.ANVIL_BREAK, 1f, 1f);
            return;
        }

        if (args.length != 2) {
            command.getSender().sendMessage(CC.translate("&8&m--------------------------------------"));
            command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &c&lUso incorrecto del comando /booster"));
            command.getSender().sendMessage(CC.translate("&7&l➥ &f/booster <multiplicador%> <tiempo>"));
            command.getSender().sendMessage(CC.translate("&7&l➥ &f/booster stop &7(para detener el booster)"));
            command.getSender().sendMessage(CC.translate("&7&l➥ &eEjemplo: &f/booster 100% 5m"));
            command.getSender().sendMessage(CC.translate("&8&m--------------------------------------"));
            return;
        }

        String multiplierArg = args[0];
        if (!multiplierArg.endsWith("%")) {
            command.getSender().sendMessage(CC.translate("&8&m--------------------------------------"));
            command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &c&lFormato inválido para el multiplicador."));
            command.getSender().sendMessage(CC.translate("&7&l➥ &cDebes terminar con % (ej: &f100%&c)"));
            command.getSender().sendMessage(CC.translate("&8&m--------------------------------------"));
            return;
        }

        try {
            String value = multiplierArg.substring(0, multiplierArg.length() - 1);
            double percentage = Double.parseDouble(value);
            boosterMultiplier = 1.0 + (percentage / 100.0);
        } catch (NumberFormatException e) {
            command.getSender().sendMessage(CC.translate("&8&m--------------------------------------"));
            command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &c&lEl multiplicador no es un número válido."));
            command.getSender().sendMessage(CC.translate("&7&l➥ &eEjemplo: &f100%"));
            command.getSender().sendMessage(CC.translate("&8&m--------------------------------------"));
            return;
        }

        String timeArg = args[1];
        long durationMillis = parseTimeToMillis(timeArg);

        if (durationMillis <= 0) {
            command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &cEl tiempo no es válido. Usa formatos como &f1d&c, &f5m&c, &f10s&c."));
            return;
        }

        boosterActive = true;
        boosterEndTime = System.currentTimeMillis() + durationMillis;

        command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &a¡Booster global activado! &7Multiplicador: &f" + multiplierArg + " &7Duración: &f" + timeArg));

        getServer().broadcastMessage(CC.translate("&8&m--------------------------------------"));
        getServer().broadcastMessage(CC.translate("&8[&6DBZ&8] &e&l¡BOOSTER GLOBAL ACTIVADO!"));
        getServer().broadcastMessage(CC.translate("&7&l➥ &eMultiplicador: &f" + multiplierArg));
        getServer().broadcastMessage(CC.translate("&7&l➥ &eTiempo: &f" + timeArg));
        getServer().broadcastMessage(CC.translate("&7&l➥ &eActivado por: &f" + command.getSender().getName()));
        getServer().broadcastMessage(CC.translate("&8&m--------------------------------------"));

        playGlobalSound(Sound.WITHER_SPAWN, 1f, 1f);

    }

    private long parseTimeToMillis(String input) {
        try {
            char unit = input.charAt(input.length() - 1);
            long value = Long.parseLong(input.substring(0, input.length() - 1));

            switch (unit) {
                case 'd':
                    return value * 24 * 60 * 60 * 1000;
                case 'm':
                    return value * 60 * 1000;
                case 's':
                    return value * 1000;
                default:
                    return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }
    public static String getBoosterRemainingTime() {
        if (boosterActive && System.currentTimeMillis() >= boosterEndTime) {
            boosterActive = false;
            boosterMultiplier = 1.0;
            boosterEndTime = 0;
            getServer().broadcastMessage(CC.translate("&8&m--------------------------------------"));
            getServer().broadcastMessage(CC.translate("&8[&6DBZ&8] &c&l¡El Booster Global ha terminado!"));
            getServer().broadcastMessage(CC.translate("&8&m--------------------------------------"));
            playGlobalSound(Sound.ANVIL_BREAK, 1f, 1f);

            return "0s";
        }

        if (!boosterActive) {
            return "0s";
        }

        long remainingMillis = boosterEndTime - System.currentTimeMillis();
        if (remainingMillis <= 0) {
            return "0s";
        }

        long totalSeconds = remainingMillis / 1000;
        long days = totalSeconds / (24 * 60 * 60);
        long remainder = totalSeconds % (24 * 60 * 60);
        long minutes = (remainder / 60) % 60;
        long seconds = remainder % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (minutes > 0 || days > 0) {
            sb.append(minutes).append("m ");
        }
        sb.append(seconds).append("s");

        return sb.toString().trim();
    }


    public static String getBoosterMultiplierPercent() {
        double percent = (boosterMultiplier - 1.0) * 100.0;
        return Math.round(percent) + "%";
    }

    private static void playGlobalSound ( Sound sound, float volume, float pitch ) {
        for (Player player : Main.instance.getServer ().getOnlinePlayers ()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }
}
