package gregtech.asm.hooks;

import github.kasuminova.lumenized.Lumenized;
import github.kasuminova.lumenized.mixin.LumenizedEarlyMixinLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import team.chisel.ctm.api.model.IModelCTM;
import team.chisel.ctm.client.model.ModelCTM;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class CTMModHooks {

    private static Field layers;

    static {
        try {
            layers = ModelCTM.class.getDeclaredField("layers");
            layers.setAccessible(true);
        } catch (NoSuchFieldException e) {
            LumenizedEarlyMixinLoader.LOG.error("CTMModHooks no such field");
        }
    }

    public static boolean canRenderInLayer(IModelCTM model, IBlockState state, BlockRenderLayer layer) {
        boolean canRenderInLayer = model.canRenderInLayer(state, layer);
        if (model instanceof ModelCTM && layers != null) {
            try {
                return CTMHooks.checkLayerWithOptiFine(canRenderInLayer, layers.getByte(model), layer);
            } catch (Exception ignored) {
                layers = null;
                Lumenized.log.error("CTMModHooks Field error");
            }
        }
        return canRenderInLayer;
    }
}
