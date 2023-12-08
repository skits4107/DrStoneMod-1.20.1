package net.skits4107.drstonemod.entity.client;// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class PetrificationSphere<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "petrificationsphere"), "main");
	private final ModelPart bone;

	public PetrificationSphere(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -17.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, 1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 2.3562F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.1088F, 7.0607F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.1088F, -8.9393F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1088F, -8.0607F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r5 = bone2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1088F, 8.0607F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r6 = bone2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, 1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.1088F, -3.9393F, 0.0F, 0.0F, 0.0F, 2.3562F));

		PartDefinition cube_r7 = bone2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8912F, 8.0607F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1088F, 8.0607F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.1088F, 7.0607F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.1088F, -8.9393F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1088F, -8.0607F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r9 = bone3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1088F, 8.0607F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r10 = bone3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, 1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.1088F, -3.9393F, 0.0F, 0.0F, 0.0F, 2.3562F));

		PartDefinition cube_r11 = bone3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8912F, 8.0607F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = bone3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1088F, 8.0607F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.1088F, 7.0607F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.1088F, -8.9393F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1088F, -8.0607F, 0.0F, 0.0F, -2.3562F, 0.0F));

		PartDefinition cube_r13 = bone4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1088F, 8.0607F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r14 = bone4.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, 1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.1088F, -3.9393F, 0.0F, 0.0F, 0.0F, 2.3562F));

		PartDefinition cube_r15 = bone4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8912F, 8.0607F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r16 = bone4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1088F, 8.0607F, 0.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}