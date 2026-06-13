package level12345.level.Block.custom;

import net.minecraft.core.BlockPos;
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
import level12345.level.WorldGen.portal.ModTeleporter;
import level12345.level.WorldGen.dimensions.ModDimensions;
import net.minecraft.resources.ResourceKey;

public class LEVLE1PortalBlock extends Block {
    public LEVLE1PortalBlock(BlockBehaviour.Properties pProperties){
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            if (!pLevel.isClientSide) {
                handleKaupenPortal(pPlayer, pPos);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handleKaupenPortal(Player player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            boolean isInCustomDim = minecraftServer.levelKeys().equals(ModDimensions.LEVEL1_LEVEL_KEY);
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.LEVEL1_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.LEVEL1_LEVEL_KEY;

            ServerLevel portalDimension = minecraftServer.getLevel(ModDimensions.LEVEL1_LEVEL_KEY);

            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == ModDimensions.LEVEL1_LEVEL_KEY){
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else{
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                }
            }
        }
    }
}
