package level12345.level.entity.projectile;

import level12345.level.entity.ModEntities;
import level12345.level.item.Moditems;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;

public class bottle_light extends ThrowableItemProjectile {
    public bottle_light(EntityType<? extends bottle_light> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public bottle_light(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.BOTTLE_LIGHT.get(), pShooter, pLevel);
    }

    public bottle_light(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.BOTTLE_LIGHT.get(), pX, pY, pZ, pLevel);
    }

    protected Item getDefaultItem() {
        return Moditems.bottle_light.get();
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    /**
     * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte pId) {
//        if (pId == 3) {
//            ParticleOptions particleoptions = this.getParticle();

//            for(int i = 0; i < 8; ++i) {
//                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
//            }
//        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float damageAmount = 50.0F;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), damageAmount );
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            AABB aabb = this.getBoundingBox().inflate(5.0);
            this.level().getEntitiesOfClass(LivingEntity.class, aabb,
                            e -> e != this.getOwner() && e.isAlive())
                    .forEach(entity -> {
                        double distance = entity.distanceTo(this);
                        float damage = (float) (50.0 * (1.0 - distance / 5.0));
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
//            this.level().explode(null, this.getX(), this.getY(), this.getZ(), 5.0F, Level.ExplosionInteraction.NONE);
//            this.level().broadcastEntityEvent(this, (byte)3);
//            this.discard();
    }

}
