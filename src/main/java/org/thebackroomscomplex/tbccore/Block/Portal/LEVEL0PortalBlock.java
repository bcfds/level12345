package org.thebackroomscomplex.tbccore.Block.Portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.thebackroomscomplex.tbccore.WorldGen.portal.ModTeleporter;

public class LEVEL0PortalBlock extends Block {
    public LEVEL0PortalBlock(Properties pProperties){super(pProperties);}
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            if (!pLevel.isClientSide) {
                handleLevel0Portal(pPlayer, pPos);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }
    private void handleLevel0Portal(Player player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            boolean isInCustomDim = minecraftServer.levelKeys().equals(Level.OVERWORLD);

            ServerLevel portalDimension = minecraftServer.getLevel(Level.OVERWORLD);

            if (portalDimension != null && !player.isPassenger()) {
                player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
            }
        }
    }
}
