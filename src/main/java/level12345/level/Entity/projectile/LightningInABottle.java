package level12345.level.Entity.projectile;

import level12345.level.Entity.ModEntities;
import level12345.level.Item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class LightningInABottle extends ThrowableItemProjectile {
    //TODO 瓶装闪电粒子要大改，尊重原著，而且闪电不止一种，命名空间也许需要调整
    public LightningInABottle(EntityType<? extends LightningInABottle> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public LightningInABottle(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.LIGHTNING_IN_A_BOTTLE.get(), pShooter, pLevel);
    }

    public LightningInABottle(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.LIGHTNING_IN_A_BOTTLE.get(), pX, pY, pZ, pLevel);
    }

    protected @NotNull Item getDefaultItem() {
        return ModItems.LIGHTNING_IN_A_BOTTLE.get();
    }

    /**
     * Handles an Entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte pId) {
    }

    /**
     * Called when the arrow hits an Entity
     */
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float damageAmount = 150.0F;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount );
    }

    /**
     * Called when this EntityFireball hits a Block or Entity.
     */
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            AABB aabb = this.getBoundingBox().inflate(5.0);
            this.level().getEntitiesOfClass(LivingEntity.class, aabb,
                            e -> e != this.getOwner() && e.isAlive())
                    .forEach(entity -> {
                        double distance = entity.distanceTo(this);
                        float damage = (float) (150.0 * (1.0 - distance / 10.0));
                        if (damage < 1.0F) damage = 1.0F;
                        entity.hurt(this.damageSources().magic(), damage);
                    });
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GENERIC_EXPLODE,
                    SoundSource.BLOCKS,
                    4.0F,
                    (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F);
        }
            // 发送客户端事件（但 handleEntityEvent 已为空，所以无任何视觉效果）
        this.level().broadcastEntityEvent(this, (byte)3);
        this.discard();
    }

}
