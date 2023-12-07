package net.skits4107.drstonemod.entity.custom;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;


//significant amounts of code have been reused from the Ice and Fire mod's StoneStatue class (created by Raptorfarian and Alexthe666) under the LGPL license..
//I feel the need to give credit to the creators of Ice and Fire for it
// as I really only changed some names and removed the cracked Synched entity data as I deemed it unneeded for this entity.
//I also do not implement the IBlacklistedFromStatues Interface that exists in ice and fire mod.
public class PetrifiedEntity extends LivingEntity {

    //these are used to store data for the entity, specifcally about the petrfied entity
    private static final EntityDataAccessor<String> TRAPPED_ENTITY_TYPE = SynchedEntityData.defineId(PetrifiedEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<CompoundTag> TRAPPED_ENTITY_DATA = SynchedEntityData.defineId(PetrifiedEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Float> TRAPPED_ENTITY_WIDTH = SynchedEntityData.defineId(PetrifiedEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TRAPPED_ENTITY_HEIGHT = SynchedEntityData.defineId(PetrifiedEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> TRAPPED_ENTITY_SCALE = SynchedEntityData.defineId(PetrifiedEntity.class, EntityDataSerializers.FLOAT);


    public PetrifiedEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    //attributes of entity
    public static AttributeSupplier.Builder bakeAttributes() {
        return Mob.createMobAttributes()
                //HEALTH
                .add(Attributes.MAX_HEALTH, 20)
                //SPEED
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                //ATTACK
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    //basically sets default entity data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TRAPPED_ENTITY_TYPE, "minecraft:pig");
        this.entityData.define(TRAPPED_ENTITY_DATA, new CompoundTag());
        this.entityData.define(TRAPPED_ENTITY_WIDTH, 0.5F);
        this.entityData.define(TRAPPED_ENTITY_HEIGHT, 0.5F);
        this.entityData.define(TRAPPED_ENTITY_SCALE, 1F);

    }

    public static PetrifiedEntity petrifyEntity(LivingEntity parent) {
        PetrifiedEntity petrifiedEntity = ModEntities.PETRIFIED_ENTITY.get().create(parent.level());
        CompoundTag entityTag = new CompoundTag();
        try {
            if (!(parent instanceof Player)) {
                parent.saveWithoutId(entityTag);
            }
        } catch (Exception e) {
            DrStoneMod.LOGGER.debug("Encountered issue creating stone statue from {}", parent);
        }
        petrifiedEntity.setTrappedTag(entityTag);
        petrifiedEntity.setTrappedEntityTypeString(ForgeRegistries.ENTITY_TYPES.getKey(parent.getType()).toString());
        petrifiedEntity.setTrappedEntityWidth(parent.getBbWidth());
        petrifiedEntity.setTrappedHeight(parent.getBbHeight());
        petrifiedEntity.setTrappedScale(parent.getScale());
        return petrifiedEntity;
    }

    //getters and setters for setting the entity data and readying it.
    public EntityType getTrappedEntityType() {
        String str = getTrappedEntityTypeString();
        return EntityType.byString(str).orElse(EntityType.PIG);
    }

    public String getTrappedEntityTypeString() {
        return this.entityData.get(TRAPPED_ENTITY_TYPE);
    }

    public void setTrappedEntityTypeString(String string) {
        this.entityData.set(TRAPPED_ENTITY_TYPE, string);
    }

    public CompoundTag getTrappedTag() {
        return this.entityData.get(TRAPPED_ENTITY_DATA);
    }

    public void setTrappedTag(CompoundTag tag) {
        this.entityData.set(TRAPPED_ENTITY_DATA, tag);
    }

    public float getTrappedWidth() {
        return this.entityData.get(TRAPPED_ENTITY_WIDTH);
    }

    public void setTrappedEntityWidth(float size) {
        this.entityData.set(TRAPPED_ENTITY_WIDTH, size);
    }

    public float getTrappedHeight() {
        return this.entityData.get(TRAPPED_ENTITY_HEIGHT);
    }

    public void setTrappedHeight(float size) {
        this.entityData.set(TRAPPED_ENTITY_HEIGHT, size);
    }

    public float getTrappedScale() {
        return this.entityData.get(TRAPPED_ENTITY_SCALE);
    }

    public void setTrappedScale(float size) {
        this.entityData.set(TRAPPED_ENTITY_SCALE, size);
    }



    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("PetrifiedWidth", this.getTrappedWidth());
        tag.putFloat("PetrifiedHeight", this.getTrappedHeight());
        tag.putFloat("PetrifiedScale", this.getTrappedScale());
        tag.putString("PetrifiedEntityType", this.getTrappedEntityTypeString());
        tag.put("PetrifiedEntityTag", this.getTrappedTag());

    }



    @Override
    public float getScale() {
        return this.getTrappedScale();
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setTrappedEntityWidth(tag.getFloat("PetrifiedWidth"));
        this.setTrappedHeight(tag.getFloat("PetrifiedHeight"));
        this.setTrappedScale(tag.getFloat("PetrifiedScale"));

        this.setTrappedEntityTypeString(tag.getString("PetrifiedEntityType"));
        if (tag.contains("PetrifiedEntityTag")) {
            this.setTrappedTag(tag.getCompound("PetrifiedEntityTag"));
        }
    }


    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }


}
