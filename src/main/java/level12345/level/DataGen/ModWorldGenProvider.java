package level12345.level.DataGen;

import level12345.level.BackroomsLevel;
import level12345.level.WorldGen.dimensions.ModDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final ResourceKey<NoiseGeneratorSettings> OVERWORLD_NOISE = NoiseGeneratorSettings.OVERWORLD;
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstraptype)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries){
        super(output,registries,BUILDER, Set.of(BackroomsLevel.MOD_ID));
    }
    @Override
    public String getName() {
        return "MyMod WorldGen";
    }
}
