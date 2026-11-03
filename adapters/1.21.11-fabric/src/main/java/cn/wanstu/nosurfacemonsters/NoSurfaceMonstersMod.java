package cn.wanstu.nosurfacemonsters;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NoSurfaceMonstersMod implements ModInitializer {
    public static final String MOD_ID = "nosurfacemonsters";
    public static final Logger LOGGER = LoggerFactory.getLogger("No Surface Monsters");

    @Override
    public void onInitialize() {
        NoSurfaceMonstersGameRules.initialize();
        LOGGER.info("No Surface Monsters loaded");
    }
}
