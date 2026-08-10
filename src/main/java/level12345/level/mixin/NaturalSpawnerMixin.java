package level12345.level.mixin;

import level12345.level.Entity.ModEntities;
import level12345.level.Entity.custom.SmilerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.world.level.NaturalSpawner;

import java.util.Map;

@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {
    @Unique
    private static final double SPAWN_CHANCE = 0.2;
    @Unique
    private static final int MAX_SMILER = 1;
    @Unique
    private static final Map<ResourceKey<Level>, Integer> DIMENSION_RADIUS = Map.of(
            ResourceKey.create(Registries.DIMENSION, new net.minecraft.resources.ResourceLocation("level", "level1")), 128
            // 有新维度直接复制黏贴孩子们
    );
    @Unique
    private static final int DEFAULT_RADIUS = 128;

    @Redirect(
            method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private static void redirectAddFreshEntity(ServerLevel level, Entity entity) {
        if (entity.getType() != ModEntities.smiler.get()) {
            level.addFreshEntityWithPassengers(entity);
            return;
        }
        if (level.random.nextDouble() > SPAWN_CHANCE) {
            return;
        }
        ResourceKey<Level> dimension = level.dimension();
        int radius = DIMENSION_RADIUS.getOrDefault(dimension, DEFAULT_RADIUS);
        BlockPos pos = entity.blockPosition();
        AABB area = new AABB(pos).inflate(radius);
        long existing = level.getEntitiesOfClass(SmilerEntity.class, area)
                .stream().filter(e -> e != entity).count();
        if (existing >= MAX_SMILER) {
            return;
        }
        level.addFreshEntityWithPassengers(entity);
    }
}
