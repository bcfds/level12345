package level12345.level.Item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ModTools {
    public static final Tier BSTEEL = new ModToolTiers(
            1,
            128,
            2,
            3,
            1,
            ()-> Ingredient.of(ModItems.BSTEEL.get())
    );
}
