package org.thebackroomscomplex.tbccore.Entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.thebackroomscomplex.tbccore.Entity.custom.SmilerEntity;
import org.thebackroomscomplex.tbccore.Item.ModItems;
import org.thebackroomscomplex.tbccore.Item.Rifle;

public class bullet extends AbstractArrow {
    public bullet(EntityType<? extends bullet> type, Level level) {
        super(type, level);
        this.pickup = Pickup.DISALLOWED;
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    public bullet(EntityType<? extends bullet> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
        this.setNoGravity(true);
    }

    public bullet(EntityType<? extends bullet> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
        this.pickup = Pickup.DISALLOWED;
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (target instanceof Player player && player.isBlocking()) {
            ItemStack activeItem = player.getUseItem();
            if (activeItem.getItem() instanceof ShieldItem) {
                player.getCooldowns().addCooldown(Items.SHIELD, 1200);
                player.stopUsingItem();
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1.0, player.getZ(), 1, 0.5, 0.5, 0.5, 0.0);
                }
                this.discard();
                return;
            }
        }
        Entity owner = this.getOwner();

        DamageSource damageSource = this.level().damageSources().arrow(this, owner);

        float damage = (float) (this.getDeltaMovement().length() * this.getBaseDamage());
        if (target instanceof SmilerEntity) {
            damage *= 5.0F;
        }
        if (target instanceof Player) {
            damage *= 1.25F;
        }
        if (target.hurt(damageSource, damage)) {
            if (target instanceof LivingEntity livingTarget) {
                this.doPostHurtEffects(livingTarget);
            }
        }
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BULLET.get());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.discard();
    }

    @Override
    public double getBaseDamage() {
        double damage = 0.75F;
        if (this.getOwner() instanceof LivingEntity owner) {
            ItemStack held = owner.getMainHandItem();
            if (held.getItem() instanceof Rifle) {
                damage *= 2.0;
            }
        }
        return damage;
    }

    @Override
    public void setCritArrow(boolean crit) {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.FLAME,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0.0D, 0.0D, 0.0D
            );

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
