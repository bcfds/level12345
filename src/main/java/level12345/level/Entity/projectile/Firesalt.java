package level12345.level.Entity.projectile;

import level12345.level.Entity.ModEntities;
import level12345.level.Item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class Firesalt extends ThrowableItemProjectile {
    //TODO 火盐粒子要大改，尊重原著
    public Firesalt(EntityType<? extends Firesalt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Firesalt(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.FIRESALT.get(), pShooter, pLevel);
    }

    public Firesalt(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.FIRESALT.get(), pX, pY, pZ, pLevel);
    }

    protected @NotNull Item getDefaultItem() {
        return ModItems.FIRESALT.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    /**
     * Handles an Entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
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
     * Called when the arrow hits an Entity
     */
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float damageAmount = 3.0F;
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
}