package net.skits4107.drstonemod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrificationSphereEntity;
import net.skits4107.drstonemod.entity.custom.ThrownPetrificationDeviceEntity;

public class PetrificationItem extends Item {

    private double timer = -1;
    private double meters = -1;

    public PetrificationItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            ThrownPetrificationDeviceEntity snowball = new ThrownPetrificationDeviceEntity(pLevel, pPlayer);
            if (this.timer != -1 && this.meters != -1){
                snowball.setMeters(this.meters);
                snowball.setTimer(this.timer);
                this.timer = -1;
                this.meters = -1;
            }
            snowball.setItem(itemstack);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(snowball);
        }

        //pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
    /*
    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (this.timer == -1){
            return;
        }
        if (this.timer <= 0 && this.meters != -1){
            if (!level.isClientSide) {
                this.timer = -1;
                PetrificationSphereEntity sphere = PetrificationSphereEntity.create(this.meters, level);
                sphere.absMoveTo(player.getX(), player.getY(), player.getZ());
                level.addFreshEntity(sphere);
                DrStoneMod.LOGGER.info("Created sphere");
            }
        }
        this.timer -= 1;
        DrStoneMod.LOGGER.info("inventory tick");
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    } */

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int pSlotId, boolean pIsSelected) {
        if (!(entity instanceof Player)){
            return;
        }
        if (this.timer == -1){
            return;
        }
        if (this.timer <= 1 && this.meters != -1){
            Player player = (Player) entity;
            if (!level.isClientSide) {
                this.timer = -1;
                PetrificationSphereEntity sphere = PetrificationSphereEntity.create(this.meters, level);
                sphere.absMoveTo(player.getX(), player.getY(), player.getZ());
                level.addFreshEntity(sphere);
                DrStoneMod.LOGGER.info("Created sphere");
                return;
            }
        }
        this.timer -= 1;
        DrStoneMod.LOGGER.info("inventory tick: "+String.valueOf(this.timer)+ " meters:"+String.valueOf(this.meters));
        super.inventoryTick(stack, level, entity, pSlotId, pIsSelected);
    }

    public void setTimer(double timer){
        this.timer = timer*20;
    }
    public void setMeters(double meters){
        this.meters = meters;
    }

    public double getTimer(){
        return this.timer;
    }
    public double getMeters(){
        return this.meters;
    }

}
