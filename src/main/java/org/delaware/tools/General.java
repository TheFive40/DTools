package org.delaware.tools;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.entity.Player;
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

    public static final String CLASS = "jrmcClass";

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

    public static void startNew(Player player){
        NBTCompound playerPersisted = PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted");
        playerPersisted.clear();
        playerPersisted.put(STR, 10);
        NBTManager.getInstance().writeForgeData(player,playerPersisted);
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
    public static Race getPlayerRace(Player player) {
        int raceNumber = PowerNBT.getApi().readForgeData(player).getCompound("PlayerPersisted").getInt("jrmcRace");
        Race playerRace = null;

        if (raceNumber == 0) {
            playerRace = Race.HUMANO;
        } else if (raceNumber == 1) {
            playerRace = Race.SAIYAJIN;
        } else if (raceNumber == 2) {
            playerRace = Race.SEMI_SAIYAN;
        } else if (raceNumber == 3) {
            playerRace = Race.NAMEKIANO;
        } else if (raceNumber == 4) {
            playerRace = Race.ARCOSIANO;
        } else if (raceNumber == 5) {
            playerRace = Race.MAJIN;
        }

        return playerRace;
    }
    public static void setPlayerRace(Player player, Race race){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound playerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        switch(race){
            case HUMANO:
                playerPersisted.put("jrmcRace",0);
                forgeData.put("jrmcRace",0);
                NBTManager.getInstance().writeForgeData(player,forgeData);
                break;
            case SAIYAJIN:
                playerPersisted.put("jrmcRace",1);
                forgeData.put("jrmcRace",1);
                NBTManager.getInstance().writeForgeData(player,forgeData);
                break;
            case SEMI_SAIYAN:
                playerPersisted.put("jrmcRace",2);
                forgeData.put("jrmcRace",2);
                NBTManager.getInstance().writeForgeData(player,forgeData);
                break;
            case NAMEKIANO:
                playerPersisted.put("jrmcRace",3);
                forgeData.put("jrmcRace",3);
                NBTManager.getInstance().writeForgeData(player,forgeData);
                break;
            case ARCOSIANO:
                playerPersisted.put("jrmcRace",4);
                forgeData.put("jrmcRace",4);
                NBTManager.getInstance().writeForgeData(player,forgeData);
                break;
            case MAJIN:
                playerPersisted.put("jrmcRace",5);
                forgeData.put("jrmcRace",5);
                NBTManager.getInstance().writeForgeData(player,forgeData);
                break;

        }
    }
    public static void setPlayerClass(Player player, DBCClass dbcClass){
        NBTCompound forgeData = NBTManager.getInstance().readForgeData(player);
        NBTCompound PlayerPersisted = (NBTCompound) forgeData.get("PlayerPersisted");
        if(dbcClass == DBCClass.WARRIOR){
            forgeData.put(CLASS, 2);
            PlayerPersisted.put(CLASS,2);

        }else if (dbcClass == DBCClass.MARTIAL_ARTIST){
            forgeData.put(CLASS, 0);
            PlayerPersisted.put(CLASS,0);

        }else{
            forgeData.put(CLASS, 1);
            PlayerPersisted.put(CLASS,1);
        }
        NBTManager.getInstance().writeForgeData(player, forgeData);
    }
    public static DBCClass getPlayerClass(Player player){
        DBCClass dbcClass = null;
        int classPlayer = NBTManager.getInstance().readForgeData(player).getInt(CLASS);
        if(classPlayer == 2){
            dbcClass = DBCClass.WARRIOR;
        }else if (classPlayer == 0){
            dbcClass = DBCClass.MARTIAL_ARTIST;
        }else{
            dbcClass = DBCClass.SPIRITUALISTIC;
        }
        return dbcClass;
    }
}