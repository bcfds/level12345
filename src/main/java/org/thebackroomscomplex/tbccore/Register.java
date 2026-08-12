package org.thebackroomscomplex.tbccore;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Register {
    //Register Item and BlockItem
    public static DeferredRegister<Item> ModItems=DeferredRegister.create(ForgeRegistries.ITEMS, TBCcore.MOD_ID);
    public static DeferredRegister<Block> Blocks=DeferredRegister.create(ForgeRegistries.BLOCKS, TBCcore.MOD_ID);
    public static DeferredRegister<CreativeModeTab> CMTs=DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TBCcore.MOD_ID);
    public static DeferredRegister<EntityType<?>> AllEntities=DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TBCcore.MOD_ID);
    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntities(String name, EntityType.Builder<T> builder) {
        return AllEntities.register(name,() -> builder.build(name));
    }

}