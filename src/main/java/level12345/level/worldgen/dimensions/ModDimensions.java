package level12345.level.worldgen.dimensions;

import com.mojang.datafixers.util.Pair;
import level12345.level.Level;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.MultipliedFloats;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraftforge.common.Tags;
import org.openjdk.nashorn.internal.runtime.linker.Bootstrap;

import javax.naming.Context;
import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> KAUPENDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "kaupendim"));
    public static final ResourceKey<net.minecraft.world.level.Level> KAUPENDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "kaupendim"));
    public static final ResourceKey<DimensionType> KAUPENDIM_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "kaupendim_type"));
    public static final ResourceKey<LevelStem> LEVEL3_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level3"));
    public static final ResourceKey<net.minecraft.world.level.Level> LEVEL3_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level3"));
    public static final ResourceKey<DimensionType> LEVEL3_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level3_type"));
    public static final ResourceKey<LevelStem> LEVEL8_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level8"));
    public static final ResourceKey<net.minecraft.world.level.Level> LEVEL8_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level8"));
    public static final ResourceKey<DimensionType> LEVEL8_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level8_type"));
    public static final ResourceKey<LevelStem> LEVEL2_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level2"));
    public static final ResourceKey<net.minecraft.world.level.Level> LEVEL2_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level2"));
    public static final ResourceKey<DimensionType> LEVEL2_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "level2_type"));

    public static void bootstraptype(BootstapContext<DimensionType> context){
        context.register(KAUPENDIM_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                true,
                true,
                false,
                false,
                1.0,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false,false, ConstantInt.of(0),0)
        ));
        context.register(LEVEL3_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                false,
                1.0,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false,false, ConstantInt.of(0),0)
        ));
        context.register(LEVEL8_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                false,
                1.0,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false,false, ConstantInt.of(0),0)
        ));
        context.register(LEVEL2_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                false,
                1.0,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1.0f,
                new DimensionType.MonsterSettings(false,false, ConstantInt.of(0),0)
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context){
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator warppedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.PLAINS)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
        MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F),
                                biomeRegistry.getOrThrow(Biomes.FOREST))
                        ))),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.KAUPENDIM_DIM_TYPE), noiseBasedChunkGenerator);

        Holder.Reference<LevelStem> register = context.register(KAUPENDIM_KEY, stem);
    }
}
