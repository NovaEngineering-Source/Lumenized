package github.kasuminova.lumenized.common.config;

import github.kasuminova.lumenized.Lumenized;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Lumenized.MOD_ID)
@Config(modid = Lumenized.MOD_ID, name = Lumenized.MOD_ID)
public class LumenizedConfig {

    @Config.Comment({ "Whether or not to enable Emissive Textures with bloom effect.", "Default: true" })
    public static boolean emissiveTexturesBloom = true;

    @Config.Comment({ "Bloom Algorithm", "0 - Simple Gaussian Blur Bloom (Fast)", "1 - Unity Bloom",
            "2 - Unreal Bloom", "Default: 2" })
    @Config.RangeInt(min = 0, max = 2)
    @Config.SlidingOption
    public static int bloomStyle = 2;

    @Config.Comment({
            "The brightness after bloom should not exceed this value. It can be used to limit the brightness of highlights " +
            "(e.g., daytime).",
            "OUTPUT = BACKGROUND + BLOOM * strength * (base + LT + (1 - BACKGROUND_BRIGHTNESS)*({HT}-LT)))",
            "This value should be greater than lowBrightnessThreshold.", "Default: 0.5" })
    @Config.RangeDouble(min = 0)
    public static double highBrightnessThreshold = 0.5;

    @Config.Comment({
            "The brightness after bloom should not smaller than this value. It can be used to limit the brightness of dusky parts " +
            "(e.g., night/caves).",
            "OUTPUT = BACKGROUND + BLOOM * strength * (base + {LT} + (1 - BACKGROUND_BRIGHTNESS)*(HT-{LT})))",
            "This value should be smaller than highBrightnessThreshold.", "Default: 0.2" })
    @Config.RangeDouble(min = 0)
    public static double lowBrightnessThreshold = 0.2;

    @Config.Comment({ "The base brightness of the bloom.", "It is similar to strength",
            "This value should be smaller than highBrightnessThreshold.",
            "OUTPUT = BACKGROUND + BLOOM * strength * ({base} + LT + (1 - BACKGROUND_BRIGHTNESS)*(HT-LT)))",
            "Default: 0.1" })
    @Config.RangeDouble(min = 0)
    public static double baseBrightness = 0.1;

    @Config.Comment({ "Mipmap Size.", "Higher values increase quality, but are slower to render.",
            "Default: 5" })
    @Config.RangeInt(min = 2, max = 5)
    @Config.SlidingOption
    public static int nMips = 5;

    @Config.Comment({ "Bloom Strength",
            "OUTPUT = BACKGROUND + BLOOM * {strength} * (base + LT + (1 - BACKGROUND_BRIGHTNESS)*(HT-LT)))",
            "Default: 2" })
    @Config.RangeDouble(min = 0)
    public static double strength = 2;

    @Config.Comment({ "Blur Step (bloom range)", "Default: 1" })
    @Config.RangeDouble(min = 0)
    public static double step = 1;

    @Config.Comment({
            "Whether to hook depth texture. Has no effect on performance, but if there is a problem with rendering, try disabling it.",
            "Default: true" })
    public static boolean hookDepthTexture = true;

    static {
        ConfigManager.sync(Lumenized.MOD_ID, Config.Type.INSTANCE);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Lumenized.MOD_ID)) {
            ConfigManager.sync(Lumenized.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
