package cn.wanstu.nosurfacemonsters;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(NoSurfaceMonstersNeoForgeMod.MOD_ID)
public final class NoSurfaceMonstersNeoForgeMod {
    public static final String MOD_ID = "nosurfacemonsters";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NoSurfaceMonstersNeoForgeMod() {
        NoSurfaceMonstersGameRules.initialize();
        LOGGER.info("No Surface Monsters loaded on NeoForge");
    }
}
