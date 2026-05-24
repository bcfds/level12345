package level12345.level.entity.projectile;

import level12345.level.entity.ModEntities;
import level12345.level.item.Moditems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class fire_salt extends ThrowableItemProjectile {
    public fire_salt(EntityType<? extends fire_salt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public fire_salt(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.FIRE_SALT.get(), pShooter, pLevel);
    }

    public fire_salt(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.FIRE_SALT.get(), pX, pY, pZ, pLevel);
    }

    protected Item getDefaultItem() {
        return Moditems.fire_salt.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    /**
     * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float damageAmount = 3.0F;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(),
                    1.0F, true, Level.ExplosionInteraction.BLOCK);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

//        this.level().explode(null, this.getX(), this.getY(), this.getZ(), 1.0F, true, Explosion.BlockInteraction.DESTROY);
//        this.level().broadcastEntityEvent(this, (byte)3);
//        this.discard();
    }
}