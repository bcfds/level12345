//在这里完成各种附加能力注册，例如理智，口渴……
package org.thebackroomscomplex.tbccore.Capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.thebackroomscomplex.tbccore.Capability.sanity.ISanity;

public class ModCapabilities {
    public static final Capability<ISanity> PLAYER_SANITY = CapabilityManager.get(new CapabilityToken<>() {});


    public static void register(@NotNull IEventBus modEventBus) {
        modEventBus.addListener(ModCapabilities::registerCapabilities);
    }

    @SubscribeEvent
    public static void registerCapabilities(@NotNull RegisterCapabilitiesEvent event) {
        event.register(ISanity.class);
    }
}
