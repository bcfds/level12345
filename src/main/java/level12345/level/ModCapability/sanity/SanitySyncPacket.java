package level12345.level.ModCapability.sanity;
//网络同步
import level12345.level.Level;
import level12345.level.ModCapability.ModCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanitySyncPacket {
    private final int playerId;
    private final int sanity;

    public SanitySyncPacket(int playerId, int sanity) {
        this.playerId = playerId;
        this.sanity = sanity;
    }

    // 编码：将数据写入字节缓冲区
    public static void encode(SanitySyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.playerId);
        buf.writeInt(msg.sanity);
    }

    // 解码：从字节缓冲区读取数据
    public static SanitySyncPacket decode(FriendlyByteBuf buf) {
        return new SanitySyncPacket(buf.readInt(), buf.readInt());
    }

    // 处理器：在客户端执行，更新本地玩家的理智值
    public static void handle(SanitySyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            // 仅在客户端执行
            if (context.getDirection().getReceptionSide().isClient()) {
                Player player = null;
                if (net.minecraft.client.Minecraft.getInstance().level != null) {
                    player = (Player) net.minecraft.client.Minecraft.getInstance().level.getEntity(msg.playerId);
                }
                if (player != null) {
                    player.getCapability(ModCapabilities.PLAYER_SANITY).ifPresent(sanity -> {
                        sanity.setSanity(msg.sanity);
                        Level.LOGGER.debug("Client sanity updated to: {}", msg.sanity);
                    });
                }
            }
        });
        context.setPacketHandled(true);
    }
}