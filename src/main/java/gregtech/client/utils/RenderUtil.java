package gregtech.client.utils;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

@SideOnly(Side.CLIENT)
public class RenderUtil {
    public static boolean updateFBOSize(Framebuffer fbo, int width, int height) {
        if (fbo.framebufferWidth != width || fbo.framebufferHeight != height) {
            fbo.createBindFramebuffer(width, height);
            return true;
        }
        return false;
    }

    public static void hookDepthBuffer(Framebuffer fbo, int depthBuffer) {
        // Hook DepthBuffer
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, fbo.framebufferObject);
        if (fbo.isStencilEnabled()) {
            OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER,
                    org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, OpenGlHelper.GL_RENDERBUFFER,
                    depthBuffer);
            OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER,
                    org.lwjgl.opengl.EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, OpenGlHelper.GL_RENDERBUFFER,
                    depthBuffer);
        } else {
            OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT,
                    OpenGlHelper.GL_RENDERBUFFER, depthBuffer);
        }
    }

    public static void hookDepthTexture(Framebuffer fbo, int depthTexture) {
        // Hook DepthTexture
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, fbo.framebufferObject);
        if (fbo.isStencilEnabled()) {
            OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT,
                    GL11.GL_TEXTURE_2D, depthTexture, 0);
        } else {
            OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT,
                    GL11.GL_TEXTURE_2D, depthTexture, 0);
        }
    }

    /**
     * Makes given BakedQuad emissive; specifically, it makes a new UnpackedBakedQuad with
     * constant lightmap coordination and {@code applyDiffuseLighting} set to {@code false}.
     * The other properties, such as textures, tint color and other vertex data will be copied from
     * the template.
     * <p>
     * Note that this method just returns {@code quad} if Optifine is installed.
     *
     * @param quad Template BakedQuad.
     * @return New UnpackedBakedQuad with emissive property
     */
    public static BakedQuad makeEmissive(BakedQuad quad) {
        if (FMLClientHandler.instance().hasOptifine()) return quad;
        VertexFormat format = quad.getFormat();
        if (!format.getElements().contains(DefaultVertexFormats.TEX_2S)) {
            format = new VertexFormat(quad.getFormat());
            format.addElement(DefaultVertexFormats.TEX_2S);
        }
        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format) {

            @Override
            public void put(int element, float @NotNull ... data) {
                if (this.getVertexFormat().getElement(element) == DefaultVertexFormats.TEX_2S)
                    super.put(element, 480.0f / 0xFFFF, 480.0f / 0xFFFF);
                else super.put(element, data);
            }
        };
        quad.pipe(builder);
        builder.setApplyDiffuseLighting(false);
        return builder.build();
    }
}
