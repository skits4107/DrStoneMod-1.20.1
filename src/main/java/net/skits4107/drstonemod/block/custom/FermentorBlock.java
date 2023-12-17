package net.skits4107.drstonemod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.item.ModItems;

public class FermentorBlock extends Block {

    public static final int MAX_FILL = 7;
    public static final int MAX_CRUSH = 4;
    public static final Property<Integer> FILLED = IntegerProperty.create("filled", 0, 7);
    public static final Property<Integer> CRUSHED = IntegerProperty.create("crushed", 0, 4);

    public static final Property<Boolean> WINE = BooleanProperty.create("wine");
    public FermentorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FILLED, 0).setValue(CRUSHED, 0).setValue(WINE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FILLED); //registers property to block states
        pBuilder.add(CRUSHED);
        pBuilder.add(WINE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        DrStoneMod.LOGGER.info("use is called");
        if (!pLevel.isClientSide && pHand.equals(InteractionHand.MAIN_HAND)){
            DrStoneMod.LOGGER.info("main hand interaction on server");
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.SWEET_BERRIES)){
                DrStoneMod.LOGGER.info("should fill up");
                int filled = pState.getValue(FILLED);
                if (filled < MAX_FILL){
                    pLevel.setBlock(pPos, pState.setValue(FILLED, filled+1), 3);
                    ItemStack itemstack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    return InteractionResult.CONSUME;
                }
                //return InteractionResult.PASS;
            }
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.GLASS_BOTTLE)){
                DrStoneMod.LOGGER.info("should get wine");
                int filled = pState.getValue(FILLED);
                int crushed = pState.getValue(CRUSHED);
                boolean wine = pState.getValue(WINE);
                if (filled == MAX_FILL && crushed == MAX_CRUSH && wine){
                    pLevel.setBlock(pPos, pState.setValue(WINE, false).setValue(CRUSHED, 0).setValue(FILLED, 0), 3);
                    ItemStack itemstack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    pPlayer.getInventory().add((new ItemStack(ModItems.WINE.get())));
                    return InteractionResult.CONSUME;
                }
            }

            //int eaten = pState.getValue(EATEN);

        }
        return InteractionResult.PASS;
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        int crushed = pState.getValue(CRUSHED);
        int filled = pState.getValue(FILLED);
        if (!pLevel.isClientSide && crushed != MAX_CRUSH && filled==MAX_FILL){
            pLevel.setBlock(pPos, pState.setValue(CRUSHED, crushed+1), 3);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int crushed = pState.getValue(CRUSHED);
        int filled = pState.getValue(FILLED);
        boolean wine = pState.getValue(WINE);
        if (!pLevel.isClientSide && crushed == MAX_CRUSH && filled == MAX_FILL && !wine){
            float val = pRandom.nextFloat();
            if (val <= 0.05){
                pLevel.setBlock(pPos, pState.setValue(WINE, true), 3);
            }
        }
    }






}
