package net.skits4107.drstonemod.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.item.custom.PetrificationItem;
import net.skits4107.drstonemod.item.custom.RevivalFluid;

public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DrStoneMod.MOD_ID);

    public static RegistryObject<Item> PETRIFICATION_DEVICE_ITEM = ITEMS.register("petrification_device", ()->new PetrificationItem(new Item.Properties().stacksTo(1)));

    public static RegistryObject<Item> REVIVAL_FLUID = ITEMS.register("revival_fluid", ()->new RevivalFluid(new Item.Properties()));

    public static RegistryObject<Item> NITRIC_ACID = ITEMS.register("nitric_acid", ()-> new Item(new Item.Properties()));

    public static RegistryObject<Item> WINE = ITEMS.register("wine", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
