package net.skits4107.drstonemod.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.skits4107.drstonemod.entity.ModEntities;
import net.skits4107.drstonemod.item.ModItems;

public class ThrownPetrificationDeviceEntity extends ThrowableItemProjectile {


    public ThrownPetrificationDeviceEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownPetrificationDeviceEntity(Level pLevel) {

        super(ModEntities.THROWN_DEVICE.get(), pLevel);
    }

    public ThrownPetrificationDeviceEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.THROWN_DEVICE.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PETRIFICATION_DEVICE_ITEM.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if (entity instanceof LivingEntity){
            if (entity != null && !(entity instanceof Player)){
                PetrifiedEntity petrifiedEntity = PetrifiedEntity.petrifyEntity((LivingEntity) entity);
                Level level = entity.level();
                petrifiedEntity.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                petrifiedEntity.yBodyRot = entity.getYRot();

                if (!level.isClientSide) {
                    entity.remove(Entity.RemovalReason.KILLED);
                    level.addFreshEntity(petrifiedEntity);
                }
                this.discard();
            }
        }
        //super.onHitEntity(pResult);
    }


}
