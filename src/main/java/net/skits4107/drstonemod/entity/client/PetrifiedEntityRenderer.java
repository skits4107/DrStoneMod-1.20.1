package net.skits4107.drstonemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
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
        return new ResourceLocation("textures/block/stone.png");
        //return TextureAtlas.LOCATION_BLOCKS;
    }

    protected void preRenderCallback(PetrifiedEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        float scale = entity.getScale() < 0.01F ? 1F : entity.getScale();
        matrixStackIn.scale(scale, scale, scale);
    }

    @Override
    public void render(PetrifiedEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        //default model is pig model
        EntityModel model = new PigModel(context.bakeLayer(ModelLayers.PIG));

        // Get the correct model
        if (modelMap.get(entityIn.getTrappedEntityTypeString()) != null) {
            model = modelMap.get(entityIn.getTrappedEntityTypeString());
        } else {
            EntityRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entityIn.getTrappedEntityType());

            if (renderer instanceof RenderLayerParent) {
                model = ((RenderLayerParent<?, ?>) renderer).getModel();
            }
            modelMap.put(entityIn.getTrappedEntityTypeString(), model);
        }

        if (model == null)
            return;

        Entity fakeEntity = null;
        if (this.hollowEntityMap.get(entityIn.getTrappedEntityTypeString()) == null) {
            Entity build = entityIn.getTrappedEntityType().create(Minecraft.getInstance().level);
            if (build != null) {
                try {
                    build.load(entityIn.getTrappedTag());
                } catch (Exception e) {
                    DrStoneMod.LOGGER.warn("Mob " + entityIn.getTrappedEntityTypeString() + " could not build statue NBT");
                }
                fakeEntity = this.hollowEntityMap.putIfAbsent(entityIn.getTrappedEntityTypeString(), build);
            }
        } else {
            fakeEntity = this.hollowEntityMap.get(entityIn.getTrappedEntityTypeString());
        }

        matrixStackIn.pushPose();
        float yaw = entityIn.yRotO + (entityIn.getYRot() - entityIn.yRotO) * partialTicks;
        boolean shouldSit = entityIn.isPassenger() && (entityIn.getVehicle() != null && entityIn.getVehicle().shouldRiderSit());
        model.young = entityIn.isBaby();
        model.riding = shouldSit;
        model.attackTime = entityIn.getAttackAnim(partialTicks);
        if (fakeEntity != null) {
            model.setupAnim(fakeEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
        }
        preRenderCallback(entityIn, matrixStackIn, partialTicks);
        matrixStackIn.translate(0, 1.5F, 0);
       // matrixStackIn.mulPose(new Quaternionf());
       // matrixStackIn.mulPose(new Quaternionf(Vector3f.YP, yaw, true));
       // model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();

        //super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }


}
