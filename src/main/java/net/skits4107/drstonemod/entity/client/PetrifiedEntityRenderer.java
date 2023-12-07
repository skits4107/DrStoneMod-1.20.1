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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrifiedEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.HashMap;
import java.util.Map;


//significant amounts of code have been reused from the Ice and Fire mod's RenderStoneStatue class.
// there are various differences within the renderer but a lot is the same.
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
        return new ResourceLocation("minecraft:textures/block/cobblestone.png");
    }



    @Override
    public void render(PetrifiedEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {

        //default model is pig
        EntityModel model = new PigModel(context.bakeLayer(ModelLayers.PIG));

        //if there is model in cache
        if (modelMap.get(entityIn.getTrappedEntityTypeString()) != null) {
            model = modelMap.get(entityIn.getTrappedEntityTypeString());
        } else {
            //get the renderer of the relevant entity
            EntityRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entityIn.getTrappedEntityType());
            if (renderer != null) {
                if (renderer instanceof LivingEntityRenderer<?, ?>) {
                    //get model if it is a living entity
                    model = ((LivingEntityRenderer<?, ?>) renderer).getModel();
                }
                //store model in cache
                modelMap.put(entityIn.getTrappedEntityTypeString(), model);
            }
        }
        if (model == null)
            return;

        //fake entity used for setting animation
        Entity fakeEntity = null;
        if (this.hollowEntityMap.get(entityIn.getTrappedEntityTypeString()) == null) {
            //creates entity from stored entity type
            Entity build = entityIn.getTrappedEntityType().create(Minecraft.getInstance().level);
            if (build != null) {
                try {
                    //load stored data on entity
                    build.load(entityIn.getTrappedTag());
                } catch (Exception e) {
                    DrStoneMod.LOGGER.warn("Mob " + entityIn.getTrappedEntityTypeString() + " could not build statue NBT");
                }
                //sets fake entitiy and caches it
                fakeEntity = this.hollowEntityMap.putIfAbsent(entityIn.getTrappedEntityTypeString(), build);
            }
        } else {
            //gets from cache
            fakeEntity = this.hollowEntityMap.get(entityIn.getTrappedEntityTypeString());
        }

        RenderType tex = model.renderType(new ResourceLocation("minecraft:textures/block/cobblestone.png"));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);


        matrixStackIn.pushPose();

        matrixStackIn.scale(entityIn.getScale(), entityIn.getScale(), entityIn.getScale());
        matrixStackIn.translate(0, 1.5F, 0);
        matrixStackIn.mulPose(new Quaternionf().fromAxisAngleDeg(1.0F, 0.0F, 0.0F, 180F));
        float yaw = entityIn.yRotO + (entityIn.getYRot() - entityIn.yRotO) * partialTicks;
        matrixStackIn.mulPose(new Quaternionf().fromAxisAngleDeg(0.0F, 1.0F, 0.0F, yaw));
        boolean shouldSit = entityIn.isPassenger() && (entityIn.getVehicle() != null && entityIn.getVehicle().shouldRiderSit());
        model.young = entityIn.isBaby();
        model.riding = shouldSit;
        model.attackTime = entityIn.getAttackAnim(partialTicks);
        if (fakeEntity != null){


            model.setupAnim(fakeEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);

        }
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();



        //super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }


}
