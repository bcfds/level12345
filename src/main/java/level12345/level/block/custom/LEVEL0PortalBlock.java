package level12345.level.block.custom;

import level12345.level.worldgen.dimensions.ModDimensions;
import level12345.level.worldgen.portal.ModTeleporter;
import net.minecraft.client.gui.font.providers.UnihexProvider;
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

public class LEVEL0PortalBlock extends Block {
    public LEVEL0PortalBlock(BlockBehaviour.Properties pProperties){super(pProperties);}
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            if (!pLevel.isClientSide) {
                handleLevel2Portal(pPlayer, pPos);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }
    private void handleLevel2Portal(Player player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            boolean isInCustomDim = minecraftServer.levelKeys().equals(Level.OVERWORLD);
            ResourceKey<Level> resourcekey = player.level().dimension() == Level.OVERWORLD ?
                    Level.OVERWORLD : Level.OVERWORLD;

            ServerLevel portalDimension = minecraftServer.getLevel(Level.OVERWORLD);

            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == Level.OVERWORLD){
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else{
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                }
            }
        }
    }
}
