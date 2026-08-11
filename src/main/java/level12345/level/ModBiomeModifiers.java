package level12345.level;

import com.mojang.serialization.Codec;
import level12345.level.Entity.ModEntities;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BackroomsLevel.MOD_ID)
public class ModBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BackroomsLevel.MOD_ID);

    public static final RegistryObject<Codec<SmilerSpawnModifier>> SMILER_SPAWN_MODIFIER =
            BIOME_MODIFIER_SERIALIZERS.register("smiler_spawn_modifier", () -> Codec.unit(new SmilerSpawnModifier()));

    private static class SmilerSpawnModifier implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) {
                if (biome.is(new ResourceLocation("level:l1_biome"))) {
                    builder.getMobSpawnSettings().addSpawn(
                            MobCategory.MONSTER,
                            new MobSpawnSettings.SpawnerData(
                                    ModEntities.smiler.get(),
                                    1,
                                    1,
                                    1
                            )
                    );
                }
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return SMILER_SPAWN_MODIFIER.get();
        }
    }
}
