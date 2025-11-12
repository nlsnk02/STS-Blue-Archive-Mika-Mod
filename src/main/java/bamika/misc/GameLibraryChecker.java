package bamika.misc;

import bamika.utils.ModHelper;
import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamApps;

public class GameLibraryChecker {

    public static void check() {
        if (SteamAPI.isSteamRunning()) {
            SteamApps steamApps = new SteamApps();

            int targetGameAppID = 646570;

            // 查询库中是否拥有某个游戏
            boolean isOwned = steamApps.isSubscribedApp(targetGameAppID);

            if (isOwned) {
                ModHelper.logger.info("====塔p===");
            } else {
                ModHelper.logger.info("===柚子厨===");
            }
        }
    }
}