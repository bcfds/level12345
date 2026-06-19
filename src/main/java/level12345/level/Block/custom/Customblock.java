package level12345.level.Block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Customblock extends Block {
    private static final VoxelShape SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.5, 1.0); // 根据你的模型调整

    public Customblock(Properties properties) {
        super(properties.noOcclusion()); // 关键：告诉游戏这个方块不是完全遮光的
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE; // 返回实际的非完整形状，否则原版仍可能按完整方块处理
    }
}
