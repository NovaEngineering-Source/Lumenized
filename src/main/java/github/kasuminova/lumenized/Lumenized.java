package github.kasuminova.lumenized;

import github.kasuminova.lumenized.common.CommonProxy;
import gregtech.client.utils.BloomEffectUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(modid = Lumenized.MOD_ID, name = Lumenized.MOD_NAME, version = Lumenized.VERSION,
        dependencies = "required-after:forge@[14.23.5.2847,);" +
                "required-after:ctm@[MC1.12.2-1.0.2.31,);" + 
                "required-after:mixinbooter@[8.0,);",
        acceptedMinecraftVersions = "[1.12, 1.13)"
)
@SuppressWarnings("MethodMayBeStatic")
public class Lumenized {
    public static final String MOD_ID = "lumenized";
    public static final String MOD_NAME = "Lumenized";

    public static final String VERSION = Tags.VERSION;

    public static final String CLIENT_PROXY = "github.kasuminova.lumenized.client.ClientProxy";
    public static final String COMMON_PROXY = "github.kasuminova.lumenized.common.CommonProxy";

    @Mod.Instance(MOD_ID)
    public static Lumenized instance = null;
    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy = null;
    public static Logger log = null;

    public Lumenized() {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            BloomEffectUtil.init();
        }
    }

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        proxy.construction();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = VERSION;
        log = event.getModLog();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete();
    }
}
