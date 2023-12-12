package net.skits4107.drstonemod.entity.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.item.ModItems;
import net.skits4107.drstonemod.item.custom.PetrificationItem;

@Mod.EventBusSubscriber(modid = DrStoneMod.MOD_ID)
public class ModEvents {

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

    public static double getNum(String str, Player player){
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
}
