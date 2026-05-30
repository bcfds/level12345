package level12345.level.Block;

import level12345.level.BackroomsLevel;
import level12345.level.Block.custom.*;
import level12345.level.Item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BackroomsLevel.MOD_ID);

    private static void registerBlockitems(String name, Supplier<? extends Block> block){
        ModItems.ITEMS.register(name, ()->new BlockItem(block.get(),new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        registerBlockitems(name, blocks);
        return blocks;
    }

    /***
     * ATTENTION!!!! 这些看起来没用的变量是用来注册的，其实会隐式的被MC调用，不要动！
     */
    public static final RegistryObject<Block> LEVEL0_PORTAL = registerBlock("level0_portal",
            ()->new LEVEL0PortalBlock(BlockBehaviour.Properties.of().strength(-1).noLootTable().noOcclusion().noCollission().explosionResistance(3600000F)));
    public static final RegistryObject<Block> LEVEL1_PORTAL = registerBlock("level1_portal",
            ()->new LEVLE1PortalBlock(BlockBehaviour.Properties.of().strength(-1).noLootTable().noOcclusion().noCollission().explosionResistance(3600000F)));
    public static final RegistryObject<Block> LEVEL2_PORTAL = registerBlock("level2_portal",
            ()->new LEVEL2PortalBlock(BlockBehaviour.Properties.of().strength(-1).noLootTable().noOcclusion().noCollission().explosionResistance(3600000F)));
    public static final RegistryObject<Block> LEVEL3_PORTAL = registerBlock("level3_portal",
            ()->new LEVEL3PortalBlock(BlockBehaviour.Properties.of().strength(-1).noLootTable().noOcclusion().noCollission().explosionResistance(3600000F)));
    public static final RegistryObject<Block> LEVEL8_PORTAL = registerBlock("level8_portal",
            ()->new LEVEL8PortalBlock(BlockBehaviour.Properties.of().strength(-1).noLootTable().noOcclusion().noCollission().explosionResistance(3600000F)));
    public static final RegistryObject<Block> concrete_ceiling = registerBlock("concrete_ceiling",
            () -> new Block(BlockBehaviour.Properties.of().strength(-1).explosionResistance(3600000F)));
    public static final RegistryObject<Block> concrete_floor = registerBlock("concrete_floor",
            ()->new Block(BlockBehaviour.Properties.of().strength(-1).explosionResistance(3600000F)));
    public static final RegistryObject<Block> concrete_wall = registerBlock("concrete_wall",
            ()->new Block(BlockBehaviour.Properties.of().strength(-1).explosionResistance(3600000F)));
    public static final RegistryObject<Block> cracked_concrete_floor = registerBlock("cracked_concrete_floor",
            ()->new Block(BlockBehaviour.Properties.of().strength(-1).explosionResistance(3600000F)));
    public static final RegistryObject<Block> light_tube_off = registerBlock("light_tube_off",
            ()->new Block(BlockBehaviour.Properties.of().strength(-1).explosionResistance(3600000F)));
    public static final RegistryObject<Block> light_tube_on = registerBlock("light_tube_on",
            ()->new Block(BlockBehaviour.Properties.of().strength(-1).lightLevel(state -> 15).explosionResistance(3600000F)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
