package level12345.level.Block.custom;

import level12345.level.WorldGen.dimensions.ModDimensions;
import level12345.level.WorldGen.portal.ModTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class LEVEL8PortalBlock extends Block {
    public LEVEL8PortalBlock(BlockBehaviour.Properties pProperties){super(pProperties);}
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            if (!pLevel.isClientSide) {
                handleLevel8Portal(pPlayer, pPos);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }
    private void handleLevel8Portal(Player player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            boolean isInCustomDim = minecraftServer.levelKeys().equals(ModDimensions.LEVEL8_LEVEL_KEY);
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.LEVEL8_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.LEVEL8_LEVEL_KEY;

            ServerLevel portalDimension = minecraftServer.getLevel(ModDimensions.LEVEL8_LEVEL_KEY);

            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == ModDimensions.LEVEL8_LEVEL_KEY){
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else{
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                }
            }
        }
    }
}
