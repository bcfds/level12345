package level12345.level.Capability.sanity;
//理智值的游戏相关逻辑在此处
import level12345.level.BackroomsLevel;
import level12345.level.Capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = BackroomsLevel.MOD_ID)
public class SanityEventHandler {

    // 为玩家附加能力
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            // 如果你有多个能力，确保 ID 是唯一的！
            event.addCapability(ResourceLocation.fromNamespaceAndPath(BackroomsLevel.MOD_ID, "sanity"), new SanityProvider((Player) event.getObject()));
            BackroomsLevel.LOGGER.debug("Sanity capability attached to player.");
        }
    }

    // 处理重生和维度传送时的数据继承
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        event.getOriginal().revive();
        event.getOriginal().getCapability(ModCapabilities.PLAYER_SANITY).ifPresent(oldSan -> {
            event.getEntity().getCapability(ModCapabilities.PLAYER_SANITY).ifPresent(newSan -> {
                CompoundTag data = oldSan.serializeNBT();
                newSan.deserializeNBT(data);
                BackroomsLevel.LOGGER.debug("Sanity data cloned for player.");
            });
        });
    }

    // 受伤时扣除理智
    @SubscribeEvent
    public static void onPlayerHurt(@NotNull LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getCapability(ModCapabilities.PLAYER_SANITY).ifPresent(sanity -> {
                // 受伤扣除 5 点理智
                sanity.addSanity(-5);
            });
        }
    }

}
