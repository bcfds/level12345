package level12345.level.Entity.custom;

import level12345.level.Entity.projectile.LightningInABottle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class SmilerEntity extends Monster {
    public SmilerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 16;
    }
    @Override
    //孩子们这是笑魇的ai，但是咋调都太笨了，没招了
    public void registerGoals(){
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.setAggressive(true);
    }
    //孩子们这是笑魇的生成机制，但是原版的机制太几把坑了，咋写都改不了，气笑了
    public static boolean canSpawn(EntityType<SmilerEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, pos) >= 1) return false;
        if (!checkMobSpawnRules(type, level, spawnType, pos, random)) return false;
        if (random.nextInt(10) != 0) return false;
        Holder<Biome> biome = level.getBiome(pos);
        ResourceLocation biomeName = biome.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);
        if (biomeName != null && biomeName.equals(new ResourceLocation("level", "l1_biome"))) {
            int chunkX = pos.getX() >> 4;
            int chunkZ = pos.getZ() >> 4;
            AABB chunkBounds = new AABB(chunkX << 4, level.getMinBuildHeight(), chunkZ << 4, (chunkX << 4) + 16, level.getMaxBuildHeight(), (chunkZ << 4) + 16);
            int existingSmilers = level.getEntitiesOfClass(SmilerEntity.class, chunkBounds, e -> true).size();
            return existingSmilers < 1;
        }
        return true;
    }
    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 9.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }
    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        int blockLight = this.level().getBrightness(LightLayer.BLOCK, this.blockPosition());
        if (blockLight < 1 && !this.isOnFire()) {
            if (source.getDirectEntity() instanceof LightningInABottle) {
                return super.hurt(source, amount);
            }
            return false;
        }
        return super.hurt(source, amount);
    }
    public AgeableMob getBreedOffspring(ServerLevel pLevel,AgeableMob pOtherParent){
        return null;
    }
}
