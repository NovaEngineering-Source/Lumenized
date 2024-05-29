package github.kasuminova.lumenized.client;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.function.BooleanSupplier;

@SideOnly(Side.CLIENT)
public class ShaderManager {

    private static final BooleanSupplier optifine$shaderPackLoaded;

    static {
        Field shaderPackLoadedField = null;
        try {
            Class<?> shadersClass = Class.forName("net.optifine.shaders.Shaders");
            shaderPackLoadedField = shadersClass.getDeclaredField("shaderPackLoaded");
        } catch (Exception ignored) {
        }
        if (shaderPackLoadedField == null) {
            optifine$shaderPackLoaded = () -> false;
        } else {
            Field finalShaderPackLoadedField = shaderPackLoadedField;
            optifine$shaderPackLoaded = () -> {
                try {
                    return finalShaderPackLoadedField.getBoolean(null);
                } catch (IllegalAccessException ignored) {
                }
                return false;
            };
        }
    }

    public static boolean isOptifineShaderPackLoaded() {
        return OpenGlHelper.areShadersSupported() && optifine$shaderPackLoaded.getAsBoolean();
    }

}
