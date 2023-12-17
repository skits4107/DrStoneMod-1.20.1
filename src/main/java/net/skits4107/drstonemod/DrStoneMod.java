package net.skits4107.drstonemod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.skits4107.drstonemod.block.ModBlocks;
import net.skits4107.drstonemod.entity.ModEntities;
import net.skits4107.drstonemod.entity.client.ModModelLayers;
import net.skits4107.drstonemod.entity.client.PetrificationSpherRenderer;
import net.skits4107.drstonemod.entity.client.PetrificationSphere;
import net.skits4107.drstonemod.entity.client.PetrifiedEntityRenderer;
import net.skits4107.drstonemod.item.ModItems;
import net.skits4107.drstonemod.loot.ModLootModifiers;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DrStoneMod.MOD_ID)
public class DrStoneMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "drstonemod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace


    public DrStoneMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModBlocks.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);



    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.PETRIFICATION_DEVICE_ITEM);
            event.accept(ModItems.REVIVAL_FLUID);
            event.accept(ModBlocks.FERMENTOR);
        }
        else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.GUANO);
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.NITRIC_ACID);
            event.accept(ModItems.DISTILLED_ALCOHOL);
        }
        else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(ModItems.WINE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
       ;
    }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            EntityRenderers.register(ModEntities.PETRIFICATION_SPHERE.get(), PetrificationSpherRenderer::new);
            EntityRenderers.register(ModEntities.THROWN_DEVICE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.THROWN_REVIVAL_FLUID.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.PETRIFIED_ENTITY.get(), PetrifiedEntityRenderer::new);

        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ModModelLayers.PETRIFICATION_SPHERE_LAYER, PetrificationSphere::createBodyLayer);
        }
    }
}
