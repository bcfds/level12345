package org.thebackroomscomplex.tbccore.Item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.thebackroomscomplex.tbccore.Entity.ModEntities;
import org.thebackroomscomplex.tbccore.Entity.projectile.bullet;

import java.util.List;

public class Rifle extends Item {
    private static final int MAGAZINE_SIZE = 30;
    private static final int RELOAD_TIME = 30;          // 20:1
    private static final float PROJECTILE_SPEED = 20.0F;

    public Rifle(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.getOffhandItem().isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        int ammo = getAmmo(stack);

        if (ammo > 0) {
            if (!level.isClientSide) {
                fire(level, player, stack);
                player.getCooldowns().addCooldown(this, 3);
            }
            return InteractionResultHolder.success(stack);
        }

        if (countBullets(player) >= MAGAZINE_SIZE) {
            player.startUsingItem(hand);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);

    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        int usedTicks = getUseDuration(stack) - remainingTicks;
        if (usedTicks >= RELOAD_TIME) {
            int needed = MAGAZINE_SIZE - getAmmo(stack);
            consumeBullets(player, needed);
            setAmmo(stack, MAGAZINE_SIZE);

            setJustReloaded(stack, true);

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 0.1F, 0.1F);
            player.stopUsingItem();
            player.getCooldowns().addCooldown(this, 20);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
    }

    private void fire(Level level, Player player, ItemStack stack) {
        bullet bulletEntity = new bullet(ModEntities.bullet.get(), player, level);
        Vec3 look = player.getLookAngle();
        bulletEntity.shoot(look.x, look.y, look.z, PROJECTILE_SPEED, 0.0F);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.9F, 0.9F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
        level.addFreshEntity(bulletEntity);

        int ammo = getAmmo(stack);
        setAmmo(stack, ammo - 1);

        stack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(player.getUsedItemHand()));
        player.getCooldowns().addCooldown(this, 2);
    }

    private int countBullets(Player player) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == ModItems.BULLET.get()) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private void consumeBullets(Player player, int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().getContainerSize() && remaining > 0; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == ModItems.BULLET.get()) {
                int toRemove = Math.min(stack.getCount(), remaining);
                stack.shrink(toRemove);
                remaining -= toRemove;
            }
        }
    }

    private int getAmmo(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("Ammo");
    }

    private void setAmmo(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt("Ammo", amount);
    }

    private boolean isJustReloaded(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("JustReloaded");
    }

    private void setJustReloaded(ItemStack stack, boolean value) {
        stack.getOrCreateTag().putBoolean("JustReloaded", value);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.level.Rifle.desc1"));
    }
}

