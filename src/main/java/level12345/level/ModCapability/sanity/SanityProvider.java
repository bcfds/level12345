package level12345.level.ModCapability.sanity;

import level12345.level.ModCapability.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SanityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ISanity sanityData = new SanityDataManager();
    private final LazyOptional<ISanity> optional = LazyOptional.of(() -> sanityData);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        // 当Forge询问是否有Sanity能力时，返回数据实例
        return ModCapabilities.PLAYER_SANITY.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        // 存档时调用，将数据转为NBT
        return sanityData.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        // 读档时调用，从NBT恢复数据
        sanityData.deserializeNBT(nbt);
    }
}
