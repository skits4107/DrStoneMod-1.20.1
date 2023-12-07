package net.skits4107.drstonemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrifiedEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;



public class PetrifiedEntityRenderer extends EntityRenderer<PetrifiedEntity> {

    private final Map<String, EntityModel> modelMap = new HashMap();
    private final Map<String, Entity> hollowEntityMap = new HashMap();
    private final EntityRendererProvider.Context context;

    public PetrifiedEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.context = pContext;
    }

    @Override
    public ResourceLocation getTextureLocation(PetrifiedEntity pEntity) {
        return new ResourceLocation("minecraft:textures/block/stone.png");
        //return TextureAtlas.LOCATION_BLOCKS;
    }

    protected void preRenderCallback(PetrifiedEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        float scale = entity.getScale() < 0.01F ? 1F : entity.getScale();
        matrixStackIn.scale(scale, scale, scale);
    }
    //default model is pig model
    //EntityModel model = new PigModel(context.bakeLayer(ModelLayers.PIG));
    @Override
    public void render(PetrifiedEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {

        EntityModel model = new PigModel(context.bakeLayer(ModelLayers.PIG));

        if (modelMap.get(entityIn.getTrappedEntityTypeString()) != null) {
            model = modelMap.get(entityIn.getTrappedEntityTypeString());
        } else {
            EntityRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entityIn.getTrappedEntityType());
            if (renderer != null) {
                if (renderer instanceof LivingEntityRenderer<?, ?>) {
                    model = ((LivingEntityRenderer<?, ?>) renderer).getModel();
                }
                modelMap.put(entityIn.getTrappedEntityTypeString(), model);
            }
        }
        if (model == null)
            return;


        RenderType tex = model.renderType(new ResourceLocation("minecraft:textures/block/stone.png"));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);


        matrixStackIn.pushPose();
        preRenderCallback(entityIn, matrixStackIn, partialTicks);
        matrixStackIn.translate(0, 1.5F, 0);
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();



        //super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }


}
