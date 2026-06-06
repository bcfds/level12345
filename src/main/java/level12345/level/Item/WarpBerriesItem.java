package level12345.level.Item;

import level12345.level.DataGen.DimensionVisit.DimensionVisitorData;
import level12345.level.WorldGen.portal.ModTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WarpBerriesItem extends Item {
    private static final FoodProperties FoodPproperties = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.3F)
            .alwaysEat()
            .fast()
            .build();

    public WarpBerriesItem(Item.Properties pProperties) {
        super(pProperties.food(FoodPproperties));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            MinecraftServer server = player.getServer();
            ServerLevel overworld = Objects.requireNonNull(server).overworld(); // SavedData 存在主世界

            DimensionVisitorData dimdata = DimensionVisitorData.get(overworld);
            Set<ResourceKey<Level>> visited = dimdata.getPlayerVisitedDimensions(player.getUUID());

            visited.remove(player.level().dimension());
            if (visited.isEmpty()) {
                // 没有可以去的新维度，发消息并正常消耗物品
                //TODO 调试信息，用完需删除
                player.sendSystemMessage(Component.literal("No Dim avaliable"));
            } else {
                // 随机选择一个
                List<ResourceKey<Level>> list = new ArrayList<>(visited);
                ResourceKey<Level> targetKey = list.get(player.getRandom().nextInt(list.size()));
                ServerLevel targetLevel = server.getLevel(targetKey);
                //传送
                if (targetLevel != null && !player.isPassenger()) {
                    BlockPos targetPos = player.blockPosition();
                    //TODO 需要把传送声音去了
                    player.changeDimension(targetLevel, new ModTeleporter(targetPos, true));
                    //TODO 调试信息，用完需删除
                    player.sendSystemMessage(Component.literal("tp done"));
                } else {
                    //TODO 调试信息，用完需删除
                    player.sendSystemMessage(Component.literal("维度加载失败！"));
                }
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }
}
//TODO 注意到浆果的贴图用的是其他MOD的，无论在风格还是版权上都不合适，需要修改