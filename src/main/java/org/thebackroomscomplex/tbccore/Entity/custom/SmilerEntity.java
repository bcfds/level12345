package org.thebackroomscomplex.tbccore.Entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.thebackroomscomplex.tbccore.Entity.projectile.LightningInABottle;

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
    }
    public static boolean canSpawn(EntityType<SmilerEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        // 1. 基础环境检查
        if (level.getBrightness(LightLayer.BLOCK, pos) >= 1) return false;
        if (!checkMobSpawnRules(type, level, spawnType, pos, random)) return false;

        // 2. 维度白名单
        // 明确只在 level:level1 维度生成，其他维度直接拒绝
        ResourceLocation dimensionId = level.getLevel().dimension().location();
        if (!dimensionId.equals(new ResourceLocation("tbccore", "level1"))) {
            return false;
        }

        // 3. 统一概率控制
        // 按需调整：nextInt(20)=5% | nextInt(10)=10% | nextInt(50)=2%
        if (random.nextInt(100) != 0) return false;// 1% 生成率

        // 128 格半径密度控制（只在自然生成时执行）
        long nearby = level.getLevel().getEntitiesOfClass(
                SmilerEntity.class,
                new AABB(pos).inflate(128),
                e -> true
        ).size();
        return nearby < 1;
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
        int totalLight = this.level().getBrightness(LightLayer.SKY, this.blockPosition()) + this.level().getBrightness(LightLayer.BLOCK, this.blockPosition());
        if (totalLight < 1 && !this.isOnFire()) {
            if (source.is(DamageTypes.GENERIC_KILL)
                    || (source.getEntity() instanceof ServerPlayer player && player.isCreative()))
                return super.hurt(source, amount);
            if (source.getDirectEntity() instanceof LightningInABottle) {
                return super.hurt(source, amount);
            }
            return false;
        }
        return super.hurt(source, amount);
    }
}
