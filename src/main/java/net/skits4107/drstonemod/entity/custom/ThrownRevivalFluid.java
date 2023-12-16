package net.skits4107.drstonemod.entity.custom;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
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
                LivingEntity entityToRevive = (LivingEntity)petrifiedEntity.getTrappedEntityType().create(level);
                entityToRevive.load(petrifiedEntity.getTrappedTag());
                entityToRevive.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                entityToRevive.setYBodyRot(entity.getYRot());
                entityToRevive.heal(entityToRevive.getMaxHealth());

                DrStoneMod.LOGGER.info(petrifiedEntity.getTrappedTag().getAllKeys().toString());
                if (!level.isClientSide) {
                    entity.remove(Entity.RemovalReason.KILLED);
                    level.addFreshEntity(entityToRevive);
                    ((ServerLevel) level).sendParticles(ParticleTypes.LANDING_HONEY, entity.getX() + .5, entity.getY() + 1.5, entity.getZ() + .5, 20, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.15);
                }
            }
        }
        //super.onHitEntity(pResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if(!this.level().isClientSide){
            this.remove(RemovalReason.DISCARDED);
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.LANDING_HONEY, this.getX() + .5, this.getY() + 1.5, this.getZ() + .5, 20, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.15);
        }
    }
}
