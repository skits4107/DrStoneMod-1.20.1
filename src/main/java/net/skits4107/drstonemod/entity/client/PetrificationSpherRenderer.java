package net.skits4107.drstonemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrificationSphereEntity;
import net.skits4107.drstonemod.entity.custom.PetrifiedEntity;
import org.jetbrains.annotations.NotNull;

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
        RenderType tex = model.renderType(new ResourceLocation(DrStoneMod.MOD_ID, "textures/entity/pst.png"));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
    }
}