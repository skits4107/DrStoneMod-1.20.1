package net.skits4107.drstonemod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.block.ModBlocks;
import net.skits4107.drstonemod.item.ModItems;
import net.skits4107.drstonemod.item.custom.PetrificationItem;

import java.util.Random;

@Mod.EventBusSubscriber(modid = DrStoneMod.MOD_ID)
public class ModEvents {

    private static Random random = new Random();
    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        String message = event.getMessage().getString();
        Player player = event.getPlayer();
        if (message.contains(",")){
            String[] parts = message.split(",");
            double meters = getNum(parts[0], player); //get first number in the string if it exists, player for outputing error message
            if (meters == -1){
                return;
            }
            double timing = getNum(parts[1], player);
            if (timing == -1){
                return;
            }
            if (player.getInventory().contains(ModItems.PETRIFICATION_DEVICE_ITEM.get().getDefaultInstance())){
                int index = player.getInventory().findSlotMatchingItem(ModItems.PETRIFICATION_DEVICE_ITEM.get().getDefaultInstance());
                ItemStack item = player.getInventory().getItem(index);
                PetrificationItem petrificationItem = (PetrificationItem) item.getItem();
                petrificationItem.setTimer(timing);
                petrificationItem.setMeters(meters);
                player.displayClientMessage(Component.literal("Activating in: "+parts[1]+" seconds with radius: "+parts[0]+" meters"), true);
            }
        }
        else{
            player.displayClientMessage(Component.literal("Invalid syntax: missing ','"), true);
        }
    }

    @SubscribeEvent
    public static void onLivingEntityUpdate(LivingEvent.LivingTickEvent event){
        Entity entity = event.getEntity();
        if (entity.level().isClientSide){
            return; //only place poop on server side
        }
        if (entity instanceof Bat){
            int val = random.nextInt(1000);
            if (val == 0){
                //entity.
                BlockPos ground = findGround(entity.level(), entity.blockPosition());
                //if ground was found
                if (ground != null){
                    //place poop one block above ground
                    entity.level().setBlock(ground.above(), ModBlocks.GUANO.get().defaultBlockState(), 3);
                }

            }
        }

    }

    private static double getNum(String str, Player player){
        if (str.length() == 0){
            player.displayClientMessage(Component.literal("no number input"), true);
            return -1;
        }
        try{
            double num = Double.parseDouble(str);
            return num;
        }
        catch (Exception e){
            player.displayClientMessage(Component.literal("invalid number: "+str), true);
            return -1;

        }

    }

    private static BlockPos findGround(Level world, BlockPos start) {
        //loops downwards until bedrock or non-air block.
        BlockPos pos = start;
        while (pos.getY() > -64 && world.isEmptyBlock(pos)) {
            pos = pos.below();
        }
        return pos.getY() > -64 ? pos : null;
    }


}
