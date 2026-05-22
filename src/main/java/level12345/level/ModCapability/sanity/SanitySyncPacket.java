package level12345.level.ModCapability.sanity;


import level12345.level.Level;
import level12345.level.ModCapability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanitySyncPacket {
    // 玩家ID和当前理智值
    private final int playerId;
    private final int sanity;

    public SanitySyncPacket(int playerId, int sanity) {
        this.playerId = playerId;
        this.sanity = sanity;
    }

    // 编码：将数据写入 buffer
    public static void encode(SanitySyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.playerId);
        buf.writeInt(msg.sanity);
    }

    // 解码：从 buffer 读取数据
    public static SanitySyncPacket decode(FriendlyByteBuf buf) {
        return new SanitySyncPacket(buf.readInt(), buf.readInt());
    }

    // 处理：收到包后更新客户端数据
    public static void handle(SanitySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(msg.playerId);
                if (entity instanceof Player player) {
                    player.getCapability(ModCapabilities.PLAYER_SANITY).ifPresent(sanity -> {
                        sanity.setSanity(msg.sanity);
                        Level.LOGGER.debug("Client sanity synced to: {}", msg.sanity);
                    });
                }
            }
        });
        context.setPacketHandled(true);
    }
}