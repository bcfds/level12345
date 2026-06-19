package level12345.level.Item;

import level12345.level.Entity.ModEntities;
import level12345.level.Entity.projectile.bullet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Rifle extends Item {
    private static final int CHARGE_TIME = 1;
    private static final float PROJECTILE_SPEED = 10.0F;
    private static final int MAX_SHOTS = 10;            // 连续射击上限
    private static final int COOLDOWN_TICKS = 100;

    public Rifle(Properties properties) {
        super(properties);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    // 右键交互
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }
        if (isCharged(stack)) {
            fire(level, player, stack);
            setCharged(stack, false);
            return InteractionResultHolder.success(stack);
        }
        if (hasAmmo(player)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        int usedTicks = this.getUseDuration(stack) - remainingTicks;
        if (usedTicks >= CHARGE_TIME) {
            setCharged(stack, true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.stopUsingItem();
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
    }
    private void fire(Level level, Player player, ItemStack stack) {
        if (level.isClientSide) return;
        if (!hasAmmo(player)) return;

        bullet bulletEntity = new bullet(ModEntities.bullet.get(), player, level);
        Vec3 look = player.getLookAngle();
        bulletEntity.shoot(look.x, look.y, look.z, PROJECTILE_SPEED, 0.0F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.1F, 0.1F);
        level.addFreshEntity(bulletEntity);

        if (!player.isCreative()) {
            consumeAmmo(player);
        }
        stack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(player.getUsedItemHand()));

        // 增加连续射击计数
        int shots = getConsecutiveShots(stack) + 1;
        setConsecutiveShots(stack, shots);

        // 达到最大射击次数
        if (shots >= MAX_SHOTS) {
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            setConsecutiveShots(stack, 0);
        }
    }

    private boolean hasAmmo(Player player) {
        return player.getInventory().contains(new ItemStack(ModItems.BULLET.get()));
    }

    private void consumeAmmo(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == ModItems.BULLET.get()) {
                stack.shrink(1);
                break;
            }
        }
    }

    private boolean isCharged(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("Charged");
    }

    private void setCharged(ItemStack stack, boolean charged) {
        stack.getOrCreateTag().putBoolean("Charged", charged);
    }

    private int getConsecutiveShots(ItemStack stack) {
        return stack.getOrCreateTag().getInt("ConsecutiveShots");
    }

    private void setConsecutiveShots(ItemStack stack, int count) {
        stack.getOrCreateTag().putInt("ConsecutiveShots", count);
    }
}

