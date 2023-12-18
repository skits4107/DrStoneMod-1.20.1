package net.skits4107.drstonemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.block.ModBlocks;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DrStoneMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WHID_TAB = CREATIVE_MODE_TABS.register("drstone_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.REVIVAL_FLUID.get()))
                    .title(Component.translatable("creativetab.drstone_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PETRIFICATION_DEVICE_ITEM.get());
                        pOutput.accept(ModItems.REVIVAL_FLUID.get());
                        pOutput.accept(ModItems.DISTILLED_ALCOHOL.get());
                        pOutput.accept(ModItems.NITRIC_ACID.get());
                        pOutput.accept(ModItems.WINE.get());


                        pOutput.accept(ModBlocks.FERMENTOR.get());
                        pOutput.accept(ModBlocks.GUANO.get());

                    })
                    .build());

    public static void register(IEventBus EventBus){
        CREATIVE_MODE_TABS.register(EventBus);

    }

}
