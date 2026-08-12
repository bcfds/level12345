package org.thebackroomscomplex.tbccore.Entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.thebackroomscomplex.tbccore.Item.ModItems;

public class Compress_firesalt extends AbstractArrow {
    private final float baseDamage = 0.5F;
    public Compress_firesalt(EntityType<? extends bullet> type, Level level) {
        super(type, level);
        this.pickup = Pickup.DISALLOWED;
        this.setInvisible(true);
    }

    public Compress_firesalt(EntityType<? extends bullet> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    public Compress_firesalt(EntityType<? extends bullet> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
        this.pickup = Pickup.DISALLOWED;
        this.setInvisible(true);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float damageAmount = 4.0F;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);
    }

    /**
     * Called when this EntityFireball hits a Block or Entity.
     */
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(),
                    2.0F, true, Level.ExplosionInteraction.BLOCK);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BULLET.get());
    }
    @Override
    public void setCritArrow(boolean crit) {
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.SMOKE,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }
}
