package level12345.level.Item.Bottle;

import level12345.level.Item.Bottle.Capabilities.BottleFluidHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BottleItem extends Item {
    public static final int CAPACITY = 250;
    public static final String FLUID_TAG = "BottleFluid";

    public BottleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BottleFluidHandler(stack, CAPACITY);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return getFluid(stack).isEmpty() ? 0 : 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return getFluid(stack).isEmpty() ? UseAnim.NONE : UseAnim.DRINK;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        FluidStack fluid = getFluid(stack);
        if (!fluid.isEmpty()) {
            tooltip.add(fluid.getDisplayName().copy().withStyle(ChatFormatting.GRAY));
        }
        else {
            tooltip.add(Component.translatable("tooltip.level.bottle.empty").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (level.isClientSide) return stack;
        FluidStack fluid = getFluid(stack);
        if (!fluid.isEmpty()) {
            if (entity instanceof Player player && player.isCreative()) {
                return stack;
            }
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
            return new ItemStack(this);
        }
        return stack;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.success(held);
        }

        FluidStack fluid = getFluid(held);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hit.getBlockPos();
            Direction face = hit.getDirection();
            BlockState state = level.getBlockState(pos);

            if (fluid.isEmpty()) {
                Optional<ItemStack> filled = tryFill(level, pos, state, face);
                if (filled.isPresent()) {
                    return InteractionResultHolder.success(filled.get());
                }
            } else {
                Optional<ItemStack> emptied = tryEmpty(level, pos, state, face, fluid);
                if (emptied.isPresent()) {
                    return InteractionResultHolder.success(emptied.get());
                }
            }
        }

        if (!fluid.isEmpty()) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(held);
        }

        return InteractionResultHolder.pass(held);
    }

    // ==================== 装填逻辑（通用流体）====================

    private Optional<ItemStack> tryFill(Level level, BlockPos pos, BlockState state, Direction face) {
        // ① 优先尝试方块实体（MOD储罐、机器等）— 已支持任意流体
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null) {
            IFluidHandler handler = be.getCapability(ForgeCapabilities.FLUID_HANDLER, face).orElse(null);
            if (handler != null) {
                FluidStack simulated = handler.drain(CAPACITY, IFluidHandler.FluidAction.SIMULATE);
                if (!simulated.isEmpty() && simulated.getAmount() >= CAPACITY) {
                    FluidStack actual = handler.drain(CAPACITY, IFluidHandler.FluidAction.EXECUTE);
                    level.playSound(null, pos, SoundEvents.BOTTLE_FILL,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                    return Optional.of(createFilledStack(actual));
                }
            }
        }

        // ② 从流体方块装填（水源、岩浆源、流动岩浆、自定义流体等）— 不消耗方块
        if (!state.getFluidState().isEmpty()) {
            Fluid fluid = state.getFluidState().getType();
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
            return Optional.of(createFilledStack(new FluidStack(fluid, CAPACITY)));
        }

        // ③ 炼药锅（仍只支持水，这是原版炼药锅的限制）
        if (state.is(Blocks.WATER_CAULDRON)) {
            int cauldronLevel = state.getValue(LayeredCauldronBlock.LEVEL);
            if (cauldronLevel > 0) {
                BlockState newState = (cauldronLevel == 1)
                        ? Blocks.CAULDRON.defaultBlockState()
                        : state.setValue(LayeredCauldronBlock.LEVEL, cauldronLevel - 1);
                level.setBlock(pos, newState, 3);
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
                return Optional.of(createFilledStack(new FluidStack(Fluids.WATER, CAPACITY)));
            }
        }

        return Optional.empty();
    }

    // ==================== 倒出逻辑（通用流体）====================

    private Optional<ItemStack> tryEmpty(Level level, BlockPos pos, BlockState state, Direction face, FluidStack fluid) {
        // ① 优先尝试方块实体（MOD储罐等）— 已支持任意流体倒入
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null) {
            IFluidHandler handler = be.getCapability(ForgeCapabilities.FLUID_HANDLER, face).orElse(null);
            if (handler != null) {
                int filled = handler.fill(fluid, IFluidHandler.FluidAction.SIMULATE);
                if (filled == fluid.getAmount()) {
                    handler.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                    return Optional.of(new ItemStack(this));
                }
            }
        }

        // ② 炼药锅（仍只支持水，原版限制）
        if (fluid.getFluid() == Fluids.WATER) {
            if (state.is(Blocks.CAULDRON)) {
                level.setBlock(pos, Blocks.WATER_CAULDRON.defaultBlockState()
                        .setValue(LayeredCauldronBlock.LEVEL, 1), 3);
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
                return Optional.of(new ItemStack(this));
            } else if (state.is(Blocks.WATER_CAULDRON)) {
                int cauldronLevel = state.getValue(LayeredCauldronBlock.LEVEL);
                if (cauldronLevel < 3) {
                    level.setBlock(pos, state.setValue(LayeredCauldronBlock.LEVEL, cauldronLevel + 1), 3);
                    level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                    return Optional.of(new ItemStack(this));
                }
            }
        }

        return Optional.empty();
    }

    // ==================== 工具方法 ====================

    private ItemStack createFilledStack(FluidStack fluid) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().put(FLUID_TAG, fluid.writeToNBT(new CompoundTag()));
        return stack;
    }

    public static FluidStack getFluid(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(FLUID_TAG)) {
            return FluidStack.loadFluidStackFromNBT(tag.getCompound(FLUID_TAG));
        }
        return FluidStack.EMPTY;
    }
}