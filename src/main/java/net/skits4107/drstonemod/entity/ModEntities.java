package net.skits4107.drstonemod.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.drstonemod.DrStoneMod;
import net.skits4107.drstonemod.entity.custom.PetrificationSphereEntity;
import net.skits4107.drstonemod.entity.custom.PetrifiedEntity;
import net.skits4107.drstonemod.entity.custom.ThrownPetrificationDeviceEntity;
import net.skits4107.drstonemod.entity.custom.ThrownRevivalFluid;

@Mod.EventBusSubscriber(modid = DrStoneMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DrStoneMod.MOD_ID);

    public static final RegistryObject<EntityType<PetrifiedEntity>> PETRIFIED_ENTITY = registerEntity(EntityType.Builder.of(PetrifiedEntity::new, MobCategory.CREATURE).sized(0.5F, 0.5F), "petrified_entity");


    public static final RegistryObject<EntityType<ThrownPetrificationDeviceEntity>> THROWN_DEVICE = ENTITIES.register("thrown_device", () -> EntityType.Builder.
            <ThrownPetrificationDeviceEntity>of(ThrownPetrificationDeviceEntity::new, MobCategory.MISC).
            sized(0.5F,0.5F).build("thrown_device"));

    public static final RegistryObject<EntityType<ThrownRevivalFluid>> THROWN_REVIVAL_FLUID = ENTITIES.register("thrown_revival_fluid", () -> EntityType.Builder.
                    <ThrownRevivalFluid>of(ThrownRevivalFluid::new, MobCategory.MISC).
            sized(0.5F,0.5F).build("thrown_revival_fluid"));

    public static final RegistryObject<EntityType<PetrificationSphereEntity>> PETRIFICATION_SPHERE = registerEntity(EntityType.Builder.of(PetrificationSphereEntity::new, MobCategory.MISC).sized(0.5F, 0.5F), "petrification_sphere");

    private static final <T extends Entity> RegistryObject<EntityType<T>> registerEntity(EntityType.Builder<T> builder, String entityName) {
        return ENTITIES.register(entityName, () -> builder.build(entityName));
    }


    @SubscribeEvent
    public static void bakeAttributes(EntityAttributeCreationEvent creationEvent) {
        creationEvent.put(PETRIFIED_ENTITY.get(), PetrifiedEntity.bakeAttributes().build());
    }

    public static void register(IEventBus bus){
        ENTITIES.register(bus);
    }
}
