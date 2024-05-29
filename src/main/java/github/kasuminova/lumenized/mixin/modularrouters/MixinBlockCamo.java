package github.kasuminova.lumenized.mixin.modularrouters;

import gregtech.client.utils.BloomEffectUtil;
import me.desht.modularrouters.block.BlockCamo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockCamo.class)
@SuppressWarnings("MethodMayBeStatic")
public class MixinBlockCamo {

    @Inject(method = "canRenderInLayer", at = @At("HEAD"), cancellable = true, remap = false)
    private void injectCanRenderInLayer(final IBlockState state, final BlockRenderLayer layer, final CallbackInfoReturnable<Boolean> cir) {
        if (layer == BloomEffectUtil.getBloomLayer()) {
            cir.setReturnValue(false);
        }
    }

}
