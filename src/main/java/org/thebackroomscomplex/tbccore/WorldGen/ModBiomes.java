package org.thebackroomscomplex.tbccore.WorldGen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "data/level");
    public static final ResourceKey<Biome> L1_BIOME_KEY = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath("tbccore", "l1_biome"));

    public static final RegistryObject<Biome> L1_BIOME = BIOMES.register("l1_biome",
            () -> null);
}
