package level12345.level;

import level12345.level.capability.ISanity;
import level12345.level.capability.SanityProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModEventBusEvents {
    public static final Capability<ISanity> SANITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    // 2. 监听实体能力附加事件
    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            // 直接添加 Provider，不需要复杂的匿名类
            event.addCapability(
                    ResourceLocation.fromNamespaceAndPath(Level.MOD_ID, "sanity"),
                    new SanityProvider()
            );
        }
    }
}
