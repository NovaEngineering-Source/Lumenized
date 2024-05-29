package github.kasuminova.lumenized.client;


import github.kasuminova.lumenized.client.handler.ClientEventHandler;
import github.kasuminova.lumenized.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void construction() {
        super.construction();
    }

    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void postInit() {
        super.postInit();
    }

    @Override
    public void loadComplete() {
        super.loadComplete();
    }

}
