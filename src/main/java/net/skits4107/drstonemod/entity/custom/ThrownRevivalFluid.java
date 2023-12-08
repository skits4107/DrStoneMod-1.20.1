package net.skits4107.drstonemod.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.ModEntities;
import net.skits4107.drstonemod.item.ModItems;

public class ThrownRevivalFluid extends ThrowableItemProjectile {

    public ThrownRevivalFluid(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownRevivalFluid(Level pLevel) {

        super(ModEntities.THROWN_REVIVAL_FLUID.get(), pLevel);
    }

    public ThrownRevivalFluid(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.THROWN_REVIVAL_FLUID.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.REVIVAL_FLUID.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if (entity instanceof PetrifiedEntity){
            if (entity != null){
                Level level = entity.level();
                PetrifiedEntity petrifiedEntity = (PetrifiedEntity)entity;
                Entity entityToRevive = petrifiedEntity.getTrappedEntityType().create(level);

                entityToRevive.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                entityToRevive.setYBodyRot(entity.getYRot());

                DrStoneMod.LOGGER.info(petrifiedEntity.getTrappedTag().getAllKeys().toString());

                if (!level.isClientSide) {
                    entity.remove(Entity.RemovalReason.KILLED);
                    level.addFreshEntity(entityToRevive);
                }
            }
        }
        //super.onHitEntity(pResult);
    }
}
