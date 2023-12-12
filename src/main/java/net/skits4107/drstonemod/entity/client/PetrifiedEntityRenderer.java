package net.skits4107.drstonemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrifiedEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


//significant amounts of code have been reused from the Ice and Fire mod's
// RenderStoneStatue class (created by Raptorfarian and Alexthe666) under the LGPL license.
// there are various differences within the renderer but a lot is the same.
//there are comments in the class that further elaborate on modifications.
public class PetrifiedEntityRenderer extends EntityRenderer<PetrifiedEntity> {

    private final Map<String, EntityModel> modelMap = new HashMap();
    private final Map<String, Entity> hollowEntityMap = new HashMap();


    private final EntityRendererProvider.Context context;

    public PetrifiedEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.context = pContext;
    }
    //i removed the preRenderCallback from the original ice and fire code
    @Override
    public ResourceLocation getTextureLocation(PetrifiedEntity pEntity) {
        return new ResourceLocation("minecraft:textures/block/cobblestone.png");
        //changed what resource location I was returning from the original ice and fire mod code.
    }



    @Override
    public void render(PetrifiedEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {

        //default model is pig. this is used in worse case scenario
        EntityModel model = new PigModel(context.bakeLayer(ModelLayers.PIG));

        //if there is model in cache
        if (modelMap.get(entityIn.getTrappedEntityTypeString()) != null) {
            model = modelMap.get(entityIn.getTrappedEntityTypeString());
        } else {
            //get the renderer of the relevant entity
            EntityRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entityIn.getTrappedEntityType());
            if (renderer != null) {
                if (renderer instanceof LivingEntityRenderer<?, ?>) {
                    //this gets the model if it is a living entity, but it is only a shallow copy, meaning
                    // it might occasionally play unwanted animations but should still work every other way.
                    model = ((LivingEntityRenderer<?, ?>) renderer).getModel();
                    //the code for cloning the model below was not a part of the original ice and fire mod and
                    // was written by the creator of the dr stone mod (me). I do not know how good this code is,
                    // but it works at preventing unwanted animations.
                    EntityModel<?> clonedModel = null;
                    try {
                        Constructor<?> constructor = model.getClass().getDeclaredConstructor(ModelPart.class);
                        constructor.setAccessible(true);
                        ResourceLocation entityTypeName = ForgeRegistries.ENTITY_TYPES.getKey(entityIn.getTrappedEntityType());
                        String entityName = entityTypeName.toString(); //get resource location of entity ex: "minecraft:horse"
                        //location.tostring looks like "minecraft:horse#main" in order to compare we strip away that extra part
                        Optional<ModelLayerLocation> modelLayerOpt = ModelLayers.getKnownLocations()
                                .filter(location -> location.toString().split("#")[0].equals(entityName))
                                .findFirst();
                        //if we found a model layer
                        if (modelLayerOpt.isPresent()) {
                            ModelLayerLocation modelLayer = modelLayerOpt.get(); ///get model layer
                            //create new model for the relevant type of entity
                            clonedModel = (EntityModel<?>) constructor.newInstance(context.bakeLayer(modelLayer));
                        }
                    } catch (Exception e) {
                        // Handle exceptions
                        e.printStackTrace();
                    }
                    if (clonedModel != null){ //if we couldn't get a cloned model then we just use the shallow copy.
                        model = clonedModel;
                    }
                }
                //store model in cache
                modelMap.put(entityIn.getTrappedEntityTypeString(), model);
            }
        }
        if (model == null)
            return;

        //the code for getting the fake entity is from the original ice and fire mod
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

        //From here on I do things fairly differently from the original ice and fire mod as I do not worry about the special cases that existed in their mod
        //I also removed the preRenderCallback and do not have to worry about cracked amount.
        RenderType tex = model.renderType(new ResourceLocation("minecraft:textures/block/cobblestone.png"));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(tex);


        matrixStackIn.pushPose();

        matrixStackIn.scale(entityIn.getScale(), entityIn.getScale(), entityIn.getScale());
        matrixStackIn.translate(0, 1.5F, 0);

        matrixStackIn.mulPose(new Quaternionf().fromAxisAngleDeg(1.0F, 0.0F, 0.0F, 180F));

        float yaw = entityIn.yRotO + (entityIn.getYRot() - entityIn.yRotO) * partialTicks;
        matrixStackIn.mulPose(new Quaternionf().fromAxisAngleDeg(0.0F, 1.0F, 0.0F, yaw));

        model.young = entityIn.isBaby();
        model.riding = entityIn.isPassenger() && (entityIn.getVehicle() != null && entityIn.getVehicle().shouldRiderSit());
        model.attackTime = entityIn.getAttackAnim(partialTicks);
        if (fakeEntity != null){
            model.setupAnim(fakeEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);

        }
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();

    }

    @Override
    public boolean shouldRender(PetrifiedEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
