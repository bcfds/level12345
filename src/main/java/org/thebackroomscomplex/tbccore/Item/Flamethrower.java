package org.thebackroomscomplex.tbccore.Item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.thebackroomscomplex.tbccore.Entity.ModEntities;
import org.thebackroomscomplex.tbccore.Entity.projectile.flame;

import java.util.List;

public class Flamethrower extends Item{
    public static final int MAX_FUEL = 1000;
    private static final float PROJECTILE_SPEED = 1.0F;
    private static final int RELOAD_INTERVAL = 3;
    private int fireTicks = 0;
    public Flamethrower(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }
    @Override
    public int getBarWidth(ItemStack stack) {
        int current = getAmmo(stack);
        return Math.round((float) current / MAX_FUEL * 13.0F);
    }
    @Override
    public int getBarColor(ItemStack stack) {
        return 0xFFAA00;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        int ammo = getAmmo(stack);
        if (ammo <= 0) {
            player.stopUsingItem();
            return;
        }

        fireTicks++;
        if (fireTicks % RELOAD_INTERVAL != 0) return;

        fire(level, player, stack);
    }

    private void fire(Level level, Player player, ItemStack stack) {
        RandomSource random = player.getRandom();
        float maxOffsetDegrees = 15.0F;

        for (int i = 0; i < 8; i++) {
            float yawOffset = (random.nextFloat() - 0.5F) * 2 * maxOffsetDegrees;
            float pitchOffset = (random.nextFloat() - 0.5F) * 2 * maxOffsetDegrees;
            float yaw = player.getYRot() + yawOffset;
            float pitch = player.getXRot() + pitchOffset;
            float radYaw = (float) Math.toRadians(yaw);
            float radPitch = (float) Math.toRadians(pitch);
            Vec3 dir = new Vec3(
                    -Math.sin(radYaw) * Math.cos(radPitch),
                    -Math.sin(radPitch),
                    Math.cos(radYaw) * Math.cos(radPitch)
            );
            flame bulletEntity = new flame(ModEntities.flame.get(), player, level);
            bulletEntity.shoot(dir.x, dir.y, dir.z, PROJECTILE_SPEED, 0.0F);
            level.addFreshEntity(bulletEntity);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.PLAYERS, 0.2F, 0.2F);
        int ammo = getAmmo(stack);
        setAmmo(stack, ammo - 1);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (getAmmo(stack) <= 0) {
            return InteractionResultHolder.fail(stack);
        }
        if (!player.getOffhandItem().isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    public static int getAmmo(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("Ammo")) {
            setAmmo(stack, MAX_FUEL);
            return MAX_FUEL;
        }
        return tag.getInt("Ammo");
    }

    public static void setAmmo(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt("Ammo", Math.max(amount, 0));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.level.flamethrower.desc1"));
    }
}
