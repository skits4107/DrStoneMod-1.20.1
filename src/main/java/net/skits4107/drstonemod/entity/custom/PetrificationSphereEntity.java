package net.skits4107.drstonemod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.ModEntities;

import java.util.List;

public class PetrificationSphereEntity extends Entity {



    private double scale = 1f;
    private double timer = 20f*50f; //default is 50 meters. 20 ticks a second.


    public PetrificationSphereEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        if (this.timer <= 0){
            this.remove(RemovalReason.DISCARDED);
            DrStoneMod.LOGGER.info("Despawned petrification sphere");
            DrStoneMod.LOGGER.info(String.valueOf(this.scale));
            return;
        }
        super.tick();
        AABB searchArea = this.getBoundingBox().inflate(0.05); //increase radius by one every second (20 ticks a second)
        List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, searchArea);

        for (LivingEntity entity : nearbyEntities) {
            // Check if it's not the same entity
            if ((Entity)entity != this && !(entity instanceof Player) && !(entity instanceof PetrifiedEntity)) {
                PetrifiedEntity petrifiedEntity = PetrifiedEntity.petrifyEntity((LivingEntity) entity);
                Level level = entity.level();
                petrifiedEntity.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                petrifiedEntity.yBodyRot = entity.getYRot();

                if (!level.isClientSide) {
                    entity.remove(Entity.RemovalReason.KILLED);
                    level.addFreshEntity(petrifiedEntity);
                }
            }
        }
        this.setBoundingBox(searchArea); //expand the sphere
        this.scale += 0.05; //increase radius by one every second (20 ticks a second)
        this.timer -= 1;


    }

    public static PetrificationSphereEntity create(double meters, Level level){
        PetrificationSphereEntity sphere = ModEntities.PETRIFICATION_SPHERE.get().create(level);
        sphere.timer = (meters/2)*20;
        return sphere;
    }


    public double getScale(){
        return this.scale;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }


}
