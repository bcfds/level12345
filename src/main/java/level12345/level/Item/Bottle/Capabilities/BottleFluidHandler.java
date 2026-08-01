package level12345.level.Item.Bottle.Capabilities;

import level12345.level.Item.Bottle.BottleItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BottleFluidHandler implements IFluidHandlerItem, ICapabilityProvider {
    private final ItemStack container;
    private FluidStack fluid = FluidStack.EMPTY;
    private final int capacity;
    private final LazyOptional<BottleFluidHandler> holder = LazyOptional.of(() -> this);

    public BottleFluidHandler(ItemStack container, int capacity) {
        this.container = container;
        this.capacity = capacity;
        deserializeNBT();
    }

    // ---------- IFluidHandler ----------
    @Override
    public int getTanks() { return 1; }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) { return fluid; }

    @Override
    public int getTankCapacity(int tank) { return capacity; }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) { return true; }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) return 0;
        if (!fluid.isEmpty() && !fluid.isFluidEqual(resource)) return 0;

        int fillAmount = Math.min(capacity - fluid.getAmount(), resource.getAmount());
        if (action.execute() && fillAmount > 0) {
            if (fluid.isEmpty()) {
                fluid = new FluidStack(resource.getFluid(), fillAmount);
            } else {
                fluid.grow(fillAmount);
            }
            serializeNBT();
        }
        return fillAmount;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (fluid.isEmpty() || !fluid.isFluidEqual(resource)) return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (fluid.isEmpty()) return FluidStack.EMPTY;
        int drainAmount = Math.min(fluid.getAmount(), maxDrain);
        FluidStack drained = new FluidStack(fluid.getFluid(), drainAmount);
        if (action.execute() && drainAmount > 0) {
            fluid.shrink(drainAmount);
            serializeNBT();
        }
        return drained;
    }

    // ---------- IFluidHandlerItem ----------
    @Override
    public @NotNull ItemStack getContainer() { return container; }

    // ---------- NBT 持久化（命名空间隔离）----------
    private void serializeNBT() {
        CompoundTag tag = container.getOrCreateTag();
        if (fluid.isEmpty()) {
            tag.remove(BottleItem.FLUID_TAG);
            if (tag.isEmpty()) container.setTag(null);
        } else {
            tag.put(BottleItem.FLUID_TAG, fluid.writeToNBT(new CompoundTag()));
        }
    }

    private void deserializeNBT() {
        CompoundTag tag = container.getTag();
        if (tag != null && tag.contains(BottleItem.FLUID_TAG)) {
            fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound(BottleItem.FLUID_TAG));
        }
    }

    // ---------- ICapabilityProvider ----------
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.FLUID_HANDLER_ITEM ? holder.cast() : LazyOptional.empty();
    }
}