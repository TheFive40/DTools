package org.delaware.tools;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.PoweredMinecart;

public class General {
    public static final int MAX_TPS = 2000000000;
    public static int bonusSTAT;
    private static NBTCompound PlayerPersisted;
    private static int stat;
    public static final String STR = "jrmcStrI";
    public static final String DEX = "jrmcDexI";
    private static NBTCompound nbtCompound;
    public static final String CON = "jrmcCnsI";
    public static final String WIL = "jrmcWilI";

    public static final String MND = "jrmcIntI";

    public static final String SPI = "jrmcCncI";

    public static int getPlayerTps(Player player) {
        return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt("jrmcTpint");
    }
    public static int getDex(Player player){
       return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt(DEX);
    }
    public static int getCon(Player player){
        return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt(CON);
    }
    public static int getSpi(Player player){
        return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt(SPI);
    }
    public static int getStr(Player player){
        return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt(STR);
    }
    public static int getMnd(Player player){
        return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt(MND);
    }
    public static int getWil(Player player){
        return PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt(WIL);
    }
    public static void getAndRemoveTps(Player player, int tps) {
        if(tps<0) return;
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        if (tps > playerPersisted.getInt("jrmcTpint")){
            player.sendMessage(CC.translate("&c¡No tienes la cantidad suficiente de Tps!"));
            return;
        }
        playerPersisted.put("jrmcTpint", getPlayerTps(player) - tps);
        forgeData.put("jrmcTpint", getPlayerTps(player)- tps);
        NBTManager.getInstance().writeForgeData(player, forgeData);
        player.sendMessage(CC.translate("&c- " + tps));
    }

    public static void setPlayerTps(Player player, int amount) {
        NBTCompound Forgedata = NBTManager.getInstance().readForgeData(player);
        if (Forgedata == null) {
            return;
        }

        NBTCompound PlayerPersisted = (NBTCompound) Forgedata.get("PlayerPersisted");
        if (PlayerPersisted == null) {
            return;
        }

        if (getPlayerTps(player) >= MAX_TPS) {
            return;
        }

        PlayerPersisted.put("jrmcTpint", getPlayerTps(player) + amount);
        Forgedata.put("jrmcTpint", getPlayerTps(player) + amount);
        NBTManager.getInstance().writeForgeData(player, Forgedata);
    }

    public static void setSTR(Player player, int str){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        playerPersisted.put(STR,str);
        forgeData.put(STR, str);
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
    public static void setDEX(Player player, int dex){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        playerPersisted.put(DEX,dex);
        forgeData.put(DEX, dex);
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
    public static void setCON(Player player, int con){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        playerPersisted.put(CON,con);
        forgeData.put(CON, con);
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
    public static void setWIL(Player player, int wil){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        playerPersisted.put(WIL,wil);
        forgeData.put(WIL, wil);
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
    public static void setMND(Player player, int mnd){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        playerPersisted.put(MND,mnd);
        forgeData.put(MND, mnd);
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
    public static void setSPI(Player player, int spi){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        playerPersisted.put(SPI,spi);
        forgeData.put(SPI, spi);
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
}