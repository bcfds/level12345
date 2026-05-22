//在这里完成各种附加能力注册，例如理智，口渴……
package level12345.level.ModCapability;

import level12345.level.Level;
import level12345.level.ModCapability.sanity.ISanity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Level.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
    // 创建一个能力注册器。
    // 第一个参数指定注册类型，第二个参数指定本模组的 MOD ID。
    public static final Capability<ISanity> PLAYER_SANITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(ISanity.class);
    }
}
