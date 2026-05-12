package level12345.level.worldgen.portal;

import level12345.level.block.ModBlocks;
import level12345.level.block.custom.ModPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.parser.Entity;
import java.util.function.Function;

public class ModTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public ModTeleporter(BlockPos pos,boolean insideDim){
        thisPos = pos;
        insideDimension = insideDim;
    }

    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean,Entity> repositionEntity){
        entity = repositionEntity.apply(false);
        int y = 61;

        if(!insideDimension){
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(),y,thisPos.getZ());
        int tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getBlock() != Blocks.AIR )&&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                (destinationWorld.getBlockState(destinationPos.above()).getBlock() != Blocks.AIR) &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)) {
            destinationPos = destinationPos.above(2);
            tries++;
        }

        if (insideDimension){
            boolean doSetBlock = true;
            for (BlockPos checkPos:BlockPos.betweenClosed(destinationPos.below(10).west(10),
                    destinationPos.above(10).east(10))) {
                if (destinationWorld.getBlockState(checkPos).getBlock() instanceof ModPortalBlock){
                    doSetBlock = false;
                    break;
                }
            }
            if (doSetBlock){
                destinationWorld.setBlock(destinationPos, ModBlocks.MOD_PORTAL.get().defaultBlockState(),3);
            }
        }
        return entity;
    }
}
