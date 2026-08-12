package org.thebackroomscomplex.tbccore.Capability.sanity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.thebackroomscomplex.tbccore.Capability.ModCapabilities;
import org.thebackroomscomplex.tbccore.TBCcore;

public class SanityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ISanity sanity;
    private final LazyOptional<ISanity> optional;

    // 构造函数接收 Player 参数，用于网络同步回调
    public SanityProvider(Player player) {
        SanityDataManager dataManager = new SanityDataManager();
        // 设置回调：当理智值变化时，向客户端发送同步包
        dataManager.setOnChanged(() -> {
            if (!player.level().isClientSide) {
                TBCcore.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new SanitySyncPacket(player.getId(), dataManager.getSanity()));
            }
        });
        this.sanity = dataManager;
        this.optional = LazyOptional.of(() -> sanity);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        return ModCapabilities.PLAYER_SANITY.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return sanity.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        sanity.deserializeNBT(nbt);
    }
}