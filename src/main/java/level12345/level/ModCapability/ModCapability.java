package level12345.level.ModCapability;

import level12345.level.ModCapability.sanity.ISanity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapability {
    // 创建一个能力册器。
    // 第一个参数指定注册类型，第二个参数指定本模组的 MODID。
    public static final Capability<ISanity> PLAYER_SANITY = CapabilityManager.get(new CapabilityToken<>() {});
}
