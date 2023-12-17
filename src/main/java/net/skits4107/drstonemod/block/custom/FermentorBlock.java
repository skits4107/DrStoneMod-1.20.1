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
        pBuilder.add(FILLED); //registers properties to block states
        pBuilder.add(CRUSHED);
        pBuilder.add(WINE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //if the player interacts with main hand and on server side
        if (!pLevel.isClientSide && pHand.equals(InteractionHand.MAIN_HAND)){
            //if the player is holding sweet berries
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.SWEET_BERRIES)){
                //then check to see how filled it is. if it isn't filled fully then add berry to the block
                int filled = pState.getValue(FILLED);
                if (filled < MAX_FILL){
                    //set the block state to fill a bit more
                    pLevel.setBlock(pPos, pState.setValue(FILLED, filled+1), 3);
                    //get a reference to the itemstack in the main hand which is a berry in this case
                    ItemStack berries = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                    if (!pPlayer.getAbilities().instabuild) {
                        berries.shrink(1); //removes one berry
                    }
                    //return consume to indicate successful interaction
                    return InteractionResult.CONSUME;
                }

            }
            //if the item in main hand is a glass bottle
            else if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.GLASS_BOTTLE)){
                int filled = pState.getValue(FILLED);
                int crushed = pState.getValue(CRUSHED);
                boolean wine = pState.getValue(WINE);
                //check to see if the barrel has wine and can be collected
                if (filled == MAX_FILL && crushed == MAX_CRUSH && wine){
                    //set the block to default or empty
                    pLevel.setBlock(pPos, pState.setValue(WINE, false).setValue(CRUSHED, 0).setValue(FILLED, 0), 3);
                    //get the glass bottles item stack reference
                    ItemStack bottles = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                    if (!pPlayer.getAbilities().instabuild) {
                        bottles.shrink(1); //remove one glass bottle
                    }
                    //give player the wine bottle
                    pPlayer.getInventory().add((new ItemStack(ModItems.WINE.get())));
                    //indicate successful interaction
                    return InteractionResult.CONSUME;
                }
            }

        }
        //indicate failed interaction
        return InteractionResult.PASS;
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        int crushed = pState.getValue(CRUSHED);
        int filled = pState.getValue(FILLED);
        //if on server and it isnt fully crushed but is filled then increase the amount of crush when an entity falls on it
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
        //if it is fully filled and crushed then there is  chance each random tick that it turns to wine.
        if (!pLevel.isClientSide && crushed == MAX_CRUSH && filled == MAX_FILL && !wine){
            float val = pRandom.nextFloat();
            if (val <= 0.05){
                pLevel.setBlock(pPos, pState.setValue(WINE, true), 3);
            }
        }
    }






}
