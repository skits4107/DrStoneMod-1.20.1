package net.skits4107.drstonemod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.block.custom.FermentorBlock;
import net.skits4107.drstonemod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DrStoneMod.MOD_ID);

    public static final RegistryObject<Block> GUANO = registerBlock("guano", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).requiresCorrectToolForDrops().noCollission()));

    public static final RegistryObject<Block> FERMENTOR = registerBlock("fermentor", ()-> new FermentorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> POTASSIAM_SALT_ORE = registerBlock("potassiam_salt_ore", ()-> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops()));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }


}
