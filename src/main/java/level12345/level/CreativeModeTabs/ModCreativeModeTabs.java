package level12345.level.CreativeModeTabs;

import level12345.level.BackroomsLevel;
import level12345.level.Block.ModBlocks;
import level12345.level.Item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/***
 * ATTENTION!!!! 这些看起来没用的变量是用来注册的，其实会隐式的被MC调用，不要动！
 */
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BackroomsLevel.MOD_ID);
    public static final RegistryObject<CreativeModeTab>  LEVEL_ITEMS =
            CREATIVE_MODE_TABS.register("level_items",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.LIGHTNING_IN_A_BOTTLE.get()))
                    .title(Component.translatable("level_items"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.FIRESALT.get());
                        pOutput.accept(ModItems.LIGHTNING_IN_A_BOTTLE.get());
                    }).build());
    public static final RegistryObject<CreativeModeTab>  LEVEL_BLOCKS =
            CREATIVE_MODE_TABS.register("level_blocks",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.LEVEL1_PORTAL.get()))
                    .title(Component.translatable("level_blocks"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.LEVEL1_PORTAL.get());
                        pOutput.accept(ModBlocks.LEVEL0_PORTAL.get());
                        pOutput.accept(ModBlocks.LEVEL2_PORTAL.get());
                        pOutput.accept(ModBlocks.LEVEL3_PORTAL.get());
                        pOutput.accept(ModBlocks.LEVEL8_PORTAL.get());
                        pOutput.accept(ModBlocks.concrete_ceiling.get());
                        pOutput.accept(ModBlocks.concrete_floor.get());
                        pOutput.accept(ModBlocks.concrete_wall.get());
                        pOutput.accept(ModBlocks.cracked_concrete_floor.get());
                        pOutput.accept(ModBlocks.light_tube_on.get());
                        pOutput.accept(ModBlocks.light_tube_off.get());
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
