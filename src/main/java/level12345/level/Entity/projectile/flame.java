package level12345.level.Entity.projectile;

import level12345.level.Item.Flamethrower;
import level12345.level.Item.ModItems;
import level12345.level.Item.Rifle;
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
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class flame extends AbstractArrow{
    private static final int MAX_LIFE_TICKS = 6;
    private int lifeTicks = 0;
    public flame(EntityType<? extends flame> type, Level level) {
        super(type, level);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    public flame(EntityType<? extends flame> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
        this.setNoGravity(true);
    }

    public flame(EntityType<? extends flame> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
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
        if (target instanceof LivingEntity livingTarget) {
            livingTarget.setSecondsOnFire(5);
        }
        DamageSource damageSource = this.level().damageSources().arrow(this,owner);
        float damage = (float) (this.getBaseDamage());
        if (target.hurt(damageSource, damage)) {
            if (target instanceof LivingEntity livingTarget) {
                this.doPostHurtEffects(livingTarget);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.discard();
    }
    protected void onHitWater(LiquidBlock result){this.discard();}

    @Override
    public double getBaseDamage() {
        double damage = 2.0F;
        return damage;
    }
    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return false;
    }

    @Override
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

        lifeTicks++;
        if (lifeTicks >= MAX_LIFE_TICKS) {
            this.discard();
        }

    }
}
