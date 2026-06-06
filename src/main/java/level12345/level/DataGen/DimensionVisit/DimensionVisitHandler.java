package level12345.level.DataGen.DimensionVisit;

import level12345.level.BackroomsLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = BackroomsLevel.MOD_ID)
public class DimensionVisitHandler {
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Player player = event.getEntity();
        ServerLevel overworld = Objects.requireNonNull(player.getServer()).overworld(); // 全局数据保存在主世界
        DimensionVisitorData data = DimensionVisitorData.get(overworld);

        // 添加新维度的访问记录
        ResourceKey<Level> newDim = player.level().dimension();
        data.addVisitor(newDim, player.getUUID());

        // 如果要记录“离开旧维度”的情况，旧维度信息可通过 event.getFrom() 获取（该事件未提供，需要自行记录）
    }
}
