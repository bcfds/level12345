package level12345.level.capability;

import level12345.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SanityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<ISanity> PLAYER_SANITY = CapabilityManager.get(new CapabilityToken<>() {});

    private ISanity instance = new ISanity() {
        private int sanity = 100; // 默认满值100
        @Override public int getSanity() { return sanity; }
        @Override public void setSanity(int sanity) { this.sanity = Math.max(0, Math.min(100, sanity)); }
        @Override public void addSanity(int amount) { setSanity(this.sanity + amount); }
        public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
            return SanityProvider.PLAYER_SANITY.orEmpty(cap, optional);
        }
    };
    private final LazyOptional<ISanity> optional = LazyOptional.of(() -> instance);

    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_SANITY.orEmpty(cap, optional);
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("sanity", instance.getSanity());
        return tag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        instance.setSanity(nbt.getInt("sanity"));
    }
    public static final Capability<ISanity> SANITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});


}
