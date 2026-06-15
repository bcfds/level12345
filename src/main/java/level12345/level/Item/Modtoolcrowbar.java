package level12345.level.Item;

import level12345.level.Block.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;

public class Modtoolcrowbar extends PickaxeItem {
    public Modtoolcrowbar(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.getBlock() == ModBlocks.low_level_box.get() ||
                state.getBlock() == ModBlocks.high_level_box.get()) {
            return 30.0F;
        }
        return super.getDestroySpeed(stack, state);
    }
}
