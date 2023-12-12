package net.skits4107.drstonemod.entity.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrificationSphereEntity;
import net.skits4107.drstonemod.entity.custom.PetrifiedEntity;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class PetrificationSpherRenderer extends EntityRenderer<PetrificationSphereEntity> {

    private final EntityRendererProvider.Context context;

    public PetrificationSpherRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.context = pContext;

    }

    @Override
    public ResourceLocation getTextureLocation(PetrificationSphereEntity pEntity) {
        return new ResourceLocation(DrStoneMod.MOD_ID, "textures/entity/pst.png");
    }

    @Override
    public void render(PetrificationSphereEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        PetrificationSphere model = new PetrificationSphere<>(context.bakeLayer(ModModelLayers.PETRIFICATION_SPHERE_LAYER));

        matrixStackIn.pushPose();

        //ResourceLocation texture = new ResourceLocation(DrStoneMod.MOD_ID, "textures/entity/pst.png");
        //RenderSystem.setShaderTexture(0, texture);
        // Use the translucent RenderType
        //RenderType tex = RenderType.entityCutoutNoCull(new ResourceLocation(DrStoneMod.MOD_ID, "textures/entity/pst.png"));
        RenderType tex =  RenderType.entityTranslucentEmissive(new ResourceLocation(DrStoneMod.MOD_ID, "textures/entity/pst.png"));//RenderType.translucent();

        VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);

        float scale = entityIn.getScale();
        matrixStackIn.scale(scale*3.2f, scale*3.2f, scale*3.2f);

        // Translate down to keep the base at the same level
        matrixStackIn.translate(0, -1.1, 0);

        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();
    }

    @Override
    public boolean shouldRender(PetrificationSphereEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }


}