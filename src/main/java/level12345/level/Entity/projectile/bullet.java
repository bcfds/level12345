package level12345.level.Entity.projectile;

import level12345.level.Item.ModItems;
import level12345.level.Item.Rifle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class bullet extends AbstractArrow {
    private final float baseDamage = 0.5F;
    public bullet(EntityType<? extends bullet> type, Level level) {
        super(type, level);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    public bullet(EntityType<? extends bullet> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
        this.setNoGravity(true);
    }

    public bullet(EntityType<? extends bullet> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity owner = this.getOwner();

        DamageSource damageSource = this.level().damageSources().arrow(this, owner);

        float damage = (float) (this.getDeltaMovement().length() * this.getBaseDamage());
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
        double damage = 1.25F;
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
